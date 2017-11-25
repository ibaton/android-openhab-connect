package se.treehou.ng.ohcommunicator.util;


import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

public class RxUtil {
    private RxUtil(){}

    public static Function<Observable<? extends Throwable>, Observable<?>> RetryOnTimeout = new Function<Observable<? extends Throwable>, Observable<?>>() {

        @Override
        public Observable<?> apply(Observable<? extends Throwable> errors) throws Exception {
            return errors.flatMap(new Function<Throwable, ObservableSource<?>>() {
                @Override
                public ObservableSource<?> apply(Throwable error) throws Exception {
                    if (error instanceof SocketTimeoutException) {
                        return Observable.just(new Object());
                    }
                    return Observable.error(error);
                }
            });
        }
    };

    /**
     * Resubscribes to observable after a certain time.
     *
     * @param delay the time to wait
     * @param unit unit that time is provided in.
     * @param <T>
     * @return observable stream which automatically resubscribes on finish
     */
    public static final <T> ObservableTransformer<T, T> repeatWithDelay(final int delay, final TimeUnit unit){
        return new ObservableTransformer<T, T>() {

            @Override
            public ObservableSource<T> apply(Observable<T> observable) {
                return observable.repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Observable<Object> observable) throws Exception {
                        return observable.delay(delay, unit);
                    }
                });
            }
        };
    }

}
