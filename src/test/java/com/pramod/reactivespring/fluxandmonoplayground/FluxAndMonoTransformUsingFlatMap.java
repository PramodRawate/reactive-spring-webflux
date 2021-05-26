package com.pramod.reactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static reactor.core.scheduler.Schedulers.parallel;

public class FluxAndMonoTransformUsingFlatMap {

    List<String> names = Arrays.asList("Adam", "Jack", "Jay", "Jenny");

    @Test
    public void transformUsingFlatMap() {
        Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E"))
                .flatMap(s -> Flux.fromIterable(convertToList(s)))
                .log();

        StepVerifier.create(stringFlux)
                .expectNextCount(10)
                .verifyComplete();
    }

    @Test
    public void transformUsingFlatMap_UsingParallel() {
        Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E"))
                .window(2)
                .flatMap((s) ->
                    s.map(this::convertToList).subscribeOn(parallel()))
                .flatMap(s -> Flux.fromIterable(s))
                .log();

        StepVerifier.create(stringFlux)
                .expectNextCount(10)
                .verifyComplete();
    }

    @Test
    public void transformUsingFlatMap_Using_concatMap() {
        Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E"))
                .window(2)
                .concatMap((s) ->       // concatMap() to maintain order but takes 5 seconds for given 5 elements as normal to execute despite of using parallel()
                        s.map(this::convertToList).subscribeOn(parallel()))
                .flatMap(s -> Flux.fromIterable(s))
                .log();

        StepVerifier.create(stringFlux)
                .expectNextCount(10)
                .verifyComplete();
    }

    @Test
    public void transformUsingFlatMap_Using_flatMapSequential() {
        Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E"))
                .window(2)
                .flatMapSequential((s) ->       // flatMapWithSequential() to maintain order but runs in parallel
                        s.map(this::convertToList).subscribeOn(parallel()))
                .flatMap(s -> Flux.fromIterable(s))
                .log();

        StepVerifier.create(stringFlux)
                .expectNextCount(10)
                .verifyComplete();
    }

    private List<String> convertToList(String s) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Arrays.asList(s, "new Value");
    }
}
