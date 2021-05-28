package com.pramod.reactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class FluxAndMonoTransform {

    List<String> names = Arrays.asList("Adam", "Jack", "Jay", "Jenny");

    @Test
    public void transformUsingMap_Concatenate() {
        Flux<String> namesFlux = Flux.fromIterable(names)
                .map(name -> name.concat(" Denial"))
                .log();

        StepVerifier.create(namesFlux)
                .expectNext("Adam Denial", "Jack Denial", "Jay Denial", "Jenny Denial")
                .verifyComplete();
    }

    @Test
    public void transformUsingMap_Uppercase() {
        Flux<String> namesFlux = Flux.fromIterable(names)
                .map(name -> name.toUpperCase(Locale.ROOT))
                .log();

        StepVerifier.create(namesFlux)
                .expectNext("ADAM", "JACK", "JAY", "JENNY")
                .verifyComplete();
    }

    @Test
    public void transformUsingMap_Length() {
        Flux<Integer> namesFlux = Flux.fromIterable(names)
                .map(name -> name.length())
                .log();

        StepVerifier.create(namesFlux)
                .expectNext(4,4,3,5)
                .verifyComplete();
    }

    @Test
    public void transformUsingMap_Length_Repeat_Flux() {
        Flux<Integer> namesFlux = Flux.fromIterable(names)
                .map(name -> name.length())
                .repeat(1)          // repeats the flux 1 times.
                .log();

        StepVerifier.create(namesFlux)
                .expectNext(4,4,3,5,4,4,3,5)
                .verifyComplete();
    }

    @Test
    public void transformUsingMap_Filter_And_Map_Together() {
        Flux<String> namesFlux = Flux.fromIterable(names)
                .filter(name -> name.startsWith("Ja"))
                .map(name -> name.concat(" Starts with 'Ja'"))
                .log();

        StepVerifier.create(namesFlux)
                .expectNext("Jack Starts with 'Ja'", "Jay Starts with 'Ja'")
                .verifyComplete();
    }
}
