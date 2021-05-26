package com.pramod.reactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class FluxAndMonoErrorTest {

    /* Using onErrorResume() and onErrorReturn()
    * 1. onErrorResume() -  do something such as returning a flux with default values when Exception occurs
    * 2. onErrorReturn() -  return a default value when exception occurs
    * 2. onErrorMap() - map the occurred exception to some other exception.*/

    @Test
    public void fluxErrorHandling() {
        Flux<String> flux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("Error Occurred")))
                .concatWith(Flux.just("D"))
                .onErrorResume(e -> {
                        System.out.println("Exception is:" + e);
                        return Flux.just("default", "default");
                        })
                .log();

        StepVerifier.create(flux)
                .expectSubscription()
                .expectNext("A", "B", "C")
                /*.expectError(RuntimeException.class)
                .verify();*/
                .expectNext("default", "default")
                .verifyComplete();

    }

    @Test
    public void fluxErrorHandling_onErrorReturn() {
        Flux<String> flux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("Error Occurred")))
                .concatWith(Flux.just("D"))
                .onErrorReturn("default")
                .log();

        StepVerifier.create(flux)
                .expectSubscription()
                .expectNext("A", "B", "C")
                /*.expectError(RuntimeException.class)
                .verify();*/
                .expectNext("default")
                .verifyComplete();

    }

    @Test
    public void fluxErrorHandling_onErrorMap() {
        Flux<String> flux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("Error Occurred")))
                .concatWith(Flux.just("D"))
                .onErrorMap(e -> new CustomException(e))
                .log();

        StepVerifier.create(flux)
                .expectSubscription()
                .expectNext("A", "B", "C")
                .expectError(CustomException.class)
                .verify();

    }

    @Test
    public void fluxErrorHandling_Retry_When_Exception_Occurs() {
        Flux<String> flux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("Error Occurred")))
                .concatWith(Flux.just("D"))
                .onErrorMap(e -> new CustomException(e))
                .retry(2)
                .log();

        StepVerifier.create(flux)
                .expectSubscription()
                .expectNext("A", "B", "C")
                /*.expectError(CustomException.class)
                .verify();*/
                .expectNext("A", "B", "C")      /*1st retry*/
                .expectNext("A", "B", "C")      /*2nd retry*/
                .expectError(CustomException.class)
                .verify();

    }

    @Test
    @Disabled
    public void fluxErrorHandling_Retry_WithBackOff_When_Exception_Occurs() {
        Flux<String> flux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("Error Occurred")))
                .concatWith(Flux.just("D"))
                .onErrorMap(e -> new CustomException(e))
//                .retryBackOff(2, Duration.ofSeconds(5))
                .log();

        StepVerifier.create(flux)
                .expectSubscription()
                .expectNext("A", "B", "C")
                /*.expectError(CustomException.class)
                .verify();*/
                .expectNext("A", "B", "C")      /*1st retry*/
                .expectNext("A", "B", "C")      /*2nd retry*/
                .expectError(CustomException.class)
                .verify();

    }
}
