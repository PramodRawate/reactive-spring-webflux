package com.pramod.reactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class FluxAndMonoTimeTest {

    @Test
    void infinteSequence() throws InterruptedException {
        Flux<Long> intervalFlux = Flux.interval(Duration.ofMillis(200))
                .log();

        intervalFlux.subscribe(e -> System.out.println("Value is: "+ e));

        Thread.sleep(3000);
    }

    @Test
    void infinteSequence_WithDelay(){
        Flux<Integer> intervalFlux = Flux.interval(Duration.ofMillis(200))
                .delayElements(Duration.ofSeconds(1))
                .map(l -> new Integer(l.intValue()))
                .take(3)
                .log();

        StepVerifier.create(intervalFlux)
                .expectSubscription()
                .expectNext(0, 1, 2)
                .verifyComplete();
    }
}
