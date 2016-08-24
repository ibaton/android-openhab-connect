Android Openhab Connect
========

Library for communicating with openhab server.

Supports both openhab 1.x and 2.x.

Download
--------

[![](https://jitpack.io/v/ibaton/android-openhab-connect.svg)](https://jitpack.io/#ibaton/android-openhab-connect)

```
allprojects {
	repositories {
		...
	  maven { url "https://jitpack.io" }
  }
}
```

```
dependencies {
  compile 'com.github.ibaton:android-openhab-connect:1.1.18'
}
```

Example
--------

Requesting sitemap data as RX observable.

```java
OHServer server = new OHServer();
server.setLocalurl("http://192.168.1.5:8080");

Connector.ServerHandler serverHandler = new Connector.ServerHandler(server, getContext());
Observable<List<OHSitemap>> listObservable = serverHandler.requestSitemapObservable();
```
