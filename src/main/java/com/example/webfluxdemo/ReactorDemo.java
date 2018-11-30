package com.example.webfluxdemo;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang tong
 * date: 2018/11/30 10:49
 * description:
 */
public class ReactorDemo {
    public static void main(String[] args) {
        String[] strArr = {"1", "2", "3", "4", "5"};

        Subscriber<Integer> subscriber = new Subscriber<>() {
            Subscription subscription;

            @Override
            public void onSubscribe(Subscription subscription) {
                this.subscription = subscription;
                this.subscription.request(1L);
            }

            @Override
            public void onNext(Integer item) {
                System.out.println("消费 = " + item);
                this.subscription.request(1L);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
                this.subscription.cancel();
            }

            @Override
            public void onComplete() {
                System.out.println("完成了");
            }
        };
        Flux.fromArray(strArr).map(Integer::valueOf).subscribe(subscriber);
    }
}
