package se.treehou.ng.ohcommunicator.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceListener;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Cancellable;
import se.treehou.ng.ohcommunicator.connector.models.OHServer;
import se.treehou.ng.ohcommunicator.services.callbacks.OHCallback;
import se.treehou.ng.ohcommunicator.services.callbacks.OHResponse;
import se.treehou.ng.ohcommunicator.util.ThreadPool;

public class Scanner implements IScanner {

    private static final String SERVICE_TYPE = "_openhab-server._tcp.local.";
    private static final String SERVICE_TYPE_SSL = "_openhab-server-ssl._tcp.local.";

    private Context context;
    private JmDNS dnsService;
    private WifiManager.MulticastLock lock;
    private WifiManager wifi;

    private Set<OHCallback<List<OHServer>>> discoveryListeners = new HashSet<>();
    private ServiceListener serviceListener;

    private BroadcastReceiver broadcastReceiver;

    private Set<OHServer> servers = new HashSet<>();

    public Scanner(Context context) {
        this.context = context;

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateZeroconfListener();
            }
        };

        serviceListener = new ServiceListener() {
            @Override
            public void serviceAdded(ServiceEvent event) {
                dnsService.requestServiceInfo(event.getType(), event.getName());
            }

            @Override
            public void serviceRemoved(ServiceEvent event) {
            }

            @Override
            public void serviceResolved(ServiceEvent event) {

                String[] serviceUrls = event.getInfo().getURLs();
                String url = serviceUrls[0];
                if (url == null) {
                    return;
                }

                String proto = event.getType().contains("ssl") ? "https" : "http";
                Uri serverUri = Uri.parse(serviceUrls[0]).buildUpon().scheme(proto).build();

                final OHServer server = new OHServer();
                server.setName(event.getName());
                server.setLocalurl(serverUri.toString());

                servers.add(server);
                serverFound(servers);
            }
        };
    }

    /**
     * start scanning for new servers.
     */
    private void startScan() {
        context.registerReceiver(broadcastReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        ThreadPool.instance().submit(new Runnable() {
            @Override
            public void run() {
                wifi = (android.net.wifi.WifiManager) context.getSystemService(android.content.Context.WIFI_SERVICE);
                updateZeroconfListener();
            }
        });
    }

    private void updateZeroconfListener() {
        ThreadPool.instance().submit(() -> {

            if (lock == null) {
                lock = wifi.createMulticastLock("JmdnsLock");
                lock.setReferenceCounted(true);
                lock.acquire();
            }

            if (dnsService != null) {
                dnsService.unregisterAllServices();
                try {
                    dnsService.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                WifiInfo wifiinfo = wifi.getConnectionInfo();
                int intaddr = wifiinfo.getIpAddress();
                byte[] byteaddr = BigInteger.valueOf(intaddr).toByteArray();
                InetAddress addr = InetAddress.getByAddress(byteaddr);

                dnsService = JmDNS.create(addr);
                dnsService.addServiceListener(SERVICE_TYPE, serviceListener);
                dnsService.addServiceListener(SERVICE_TYPE_SSL, serviceListener);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Stop scanning for new servers.
     */
    private void stopScan() {
        try {
            context.unregisterReceiver(broadcastReceiver);
        } catch (Exception ignore) {}
        servers.clear();
        ThreadPool.instance().submit(() -> {
            try {
                dnsService.unregisterAllServices();
                dnsService.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (lock != null) lock.release();
        });
    }

    /**
     * Get rx listener for subscriber.
     *
     * @return rx zeroconf listener
     */
    @Override
    public Observable<OHServer> registerRx() {
        return Observable.create(new ObservableOnSubscribe<OHServer>() {
            @Override
            public void subscribe(final ObservableEmitter<OHServer> emitter) throws Exception {
                if (emitter.isDisposed()) {
                    return;
                }

                final OHCallback<List<OHServer>> scannerCallback = new OHCallback<List<OHServer>>() {
                    @Override
                    public void onUpdate(OHResponse<List<OHServer>> items) {
                        for (OHServer server : items.body()) {
                            emitter.onNext(server);
                        }
                    }

                    @Override
                    public void onError() {
                    }
                };

                registerServerDiscoveryListener(scannerCallback);
                emitter.setCancellable(new Cancellable() {
                    @Override
                    public void cancel() throws Exception {
                        deregisterServerDiscoveryListener(scannerCallback);
                    }
                });
            }
        });
    }

    public void registerServerDiscoveryListener(OHCallback<List<OHServer>> listener) {
        if (listener == null) {
            return;
        }

        serverFound(servers, listener);
        discoveryListeners.add(listener);
        if (discoveryListeners.size() >= 1) {
            startScan();
        }
    }

    public void deregisterServerDiscoveryListener(OHCallback<List<OHServer>> listener) {
        discoveryListeners.remove(listener);
        if (discoveryListeners.size() <= 0) {
            stopScan();
        }
    }

    /**
     * Update listeners that servers have been found
     *
     * @param servers
     */
    public void serverFound(Set<OHServer> servers) {
        List<OHServer> serverList = new ArrayList<>(servers);
        for (OHCallback<List<OHServer>> listener : discoveryListeners) {
            serverFound(serverList, listener);
        }
    }

    /**
     * Update listeners that servers have been found
     *
     * @param servers
     */
    public void serverFound(List<OHServer> servers, OHCallback<List<OHServer>> listener) {
        listener.onUpdate(new OHResponse.Builder<List<OHServer>>(new ArrayList<>(servers)).fromCache(false).build());
    }

    /**
     * Update listeners that servers have been found
     *
     * @param servers
     */
    public void serverFound(Set<OHServer> servers, OHCallback<List<OHServer>> listener) {
        listener.onUpdate(new OHResponse.Builder<List<OHServer>>(new ArrayList<>(servers)).fromCache(false).build());
    }
}
