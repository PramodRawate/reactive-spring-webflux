package com.pramod.reactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class FluxAndMonoFactoryTest {

    List<String> names = Arrays.asList("Adam", "Jack", "John", "Jenny");

    @Test
    public void fluxUsingIterable() {
        Flux<String> namesFlux = Flux.fromIterable(names)
                .log();

        StepVerifier.create(namesFlux)
                .expectNext("Adam", "Jack", "John", "Jenny")
                .verifyComplete();
    }

    @Test
    public void fluxUsingArray() {
        String[] names = new String[] {"Adam", "Jack", "John", "Jenny"};

        Flux<String> namesFlux = Flux.fromArray(names);

        StepVerifier.create(namesFlux)
                .expectNext("Adam", "Jack", "John", "Jenny")
                .verifyComplete();
    }

    @Test
    public void fluxUsingStreams() {
        Flux<String> namesFlux = Flux.fromStream(names.stream()) ;

        StepVerifier.create(namesFlux)
                .expectNext("Adam", "Jack", "John", "Jenny")
                .verifyComplete();
    }

    @Test
    public void fluxUsingRange() {
        Flux<Integer> integerFlux = Flux.range(1, 5);

        StepVerifier.create(integerFlux.log())
                .expectNext(1, 2, 3,4, 5)
                .verifyComplete();
    }

    @Test
    public void monoUsingJustOrEmpty() {
        Mono<Object> mono = Mono.justOrEmpty(null);

        StepVerifier.create(mono.log())
                .verifyComplete();
    }

    @Test
    public void monoUsingSupplier() {
        Supplier<String> stringSupplier = () -> "Pramod";
        Mono<String> stringMono = Mono.fromSupplier(stringSupplier);

        System.out.println(stringSupplier.get());

        StepVerifier.create(stringMono.log())
                .expectNext("Pramod")
                .verifyComplete();
    }
}
