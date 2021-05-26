package com.pramod.reactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

public class FluxAndMonoFilterTest {


    List<String> names = Arrays.asList("Adam", "Jack", "Anna", "John", "Jenny", "Antony");

    @Test
    public void filterTest_To_Filter_Name_Starting_With_A() {
        Flux<String> stringFlux = Flux.fromIterable(names)
                .filter(name -> name.startsWith("A"))
                .log();

        StepVerifier.create(stringFlux)
                .expectNextCount(3)
                .verifyComplete();

        StepVerifier.create(stringFlux)
                .expectNext("Adam", "Anna", "Antony")
                .verifyComplete();
    }
}
