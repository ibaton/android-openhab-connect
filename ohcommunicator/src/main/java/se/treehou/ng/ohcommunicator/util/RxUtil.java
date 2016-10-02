package se.treehou.ng.ohcommunicator.util;


import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func1;

public class RxUtil {
    private RxUtil(){}

    public static Func1<Observable<? extends Throwable>, Observable<?>> RetryOnTimeout = new Func1<Observable<? extends Throwable>, Observable<?>>() {
        @Override
        public Observable<?> call(Observable<? extends Throwable> errors) {
            return errors.flatMap(new Func1<Throwable, Observable<?>>() {
                @Override
                public Observable<?> call(Throwable error) {
                    if (error instanceof SocketTimeoutException) {
                        return Observable.just(null);
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
    public static final <T> Observable.Transformer<T, T> repeatWithDelay(final int delay, final TimeUnit unit){
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.repeatWhen(new Func1<Observable<? extends Void>, Observable<?>>() {
                    @Override
                    public Observable<?> call(Observable<? extends Void> observable) {
                        return observable.delay(delay, unit);
                    }
                });
            }
        };
    }

}
