package se.treehou.ng.ohcommunicator.util;


import java.net.SocketTimeoutException;

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
}
