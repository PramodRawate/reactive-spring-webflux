package com.pramod.reactivespring.backPressure;

import org.junit.jupiter.api.Test;
import org.springframework.boot.web.server.Ssl;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.concurrent.Flow;

public class FluxAndMonoBackPressureTest {

    /*BackPressure - Subscriber takes control of data flow from Publisher
    * 1. thenRequest(no. of elements) -> Requests no. of elements from publisher.
    * 2. BaseSubscriber().hookOnNext() -> Customized subscription to flux elements based on certain conditions*/


    @Test           // This test is using StepVerifier which is for verification but doesn't actually subscribes the flux.
    void backPressureFlux() {
        Flux<Integer> rangeFlux = Flux.range(1, 10)
                .log();

        StepVerifier.create(rangeFlux)
                .expectSubscription()
                .thenRequest(1)
                .expectNext(1)
                .thenRequest(1)
                .expectNext(2)
                .thenRequest(2)
                .expectNext(3, 4)
                .thenCancel()
                .verify();

    }

    @Test
    void backPressureWithActualSubscription() {
        Flux<Integer> rangeFlux = Flux.range(1, 10)
                .log();

        rangeFlux.subscribe(e -> System.out.println("Value is:" + e),
                (error) -> System.out.println("Exception is:"+error),
                () -> System.out.println("Done"),
                subscription -> subscription.request(2));
    }

    @Test
    void backPressure_Cancel() {
        Flux<Integer> rangeFlux = Flux.range(1, 10)
                .log();

        rangeFlux.subscribe(e -> System.out.println("Value is:" + e),
                (error) -> System.out.println("Exception is:"+error),
                () -> System.out.println("Done"),
                subscription -> subscription.cancel());
    }

    @Test
    void backPressure_CustomizedSubscription() {
        Flux<Integer> rangeFlux = Flux.range(1, 10)
                .log();

        rangeFlux.subscribe(new BaseSubscriber<Integer>() {
            @Override
            protected void hookOnNext(Integer value) {
                System.out.println("Value is: " + value);
                if (value == 4)
                    cancel();
            }
        });
    }
}
