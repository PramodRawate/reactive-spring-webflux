package com.pramod.reactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class FluxAndMonoCombineTest {

    /*Using operations like Merge(), concat() and zip().
    1. merge(flux1, flux2) - merges 2 flux but order cannot be guaranteed. When used with delays 2nd flux doesn't wait for 1st flux. Both run in parallel.
    2. concat(flux1, flux2) - concat 2 flux but order is guaranteed. When used with delay 2nd flux waits for 1st flux. hence for 3 elements in 1st flux 2nd flux waits 3 seconds.
    3. zip(flux1, flux2 (t1, t2)) - t1, t2 are 1st element from 1st and 2nd flux respectively. Zip() is used when we want to combine elements from 1st and 2nd flux such as having pairs in the given example*/
    @Test
    public void combine_Using_Merge() {
        Flux<String> flux1 = Flux.just("A", "B", "C");
        Flux<String> flux2 = Flux.just("D", "E", "F");

        Flux<String> mergedFlux = Flux.merge(flux1, flux2);

        StepVerifier.create(mergedFlux.log())
                .expectSubscription()
                .expectNext("A",  "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    public void combine_Using_Merge_With_Delay() {
        Flux<String> flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofSeconds(1));
        Flux<String> flux2 = Flux.just("D", "E", "F").delayElements(Duration.ofSeconds(1));

        Flux<String> mergedFlux = Flux.merge(flux1, flux2);

        StepVerifier.create(mergedFlux.log())
                .expectSubscription()
                .expectNextCount(6)
                .verifyComplete();
    }

    @Test
    public void combine_Using_Concat() {
        Flux<String> flux1 = Flux.just("A", "B", "C");
        Flux<String> flux2 = Flux.just("D", "E", "F");

        Flux<String> concatenatedFlux = Flux.concat(flux1, flux2);

        StepVerifier.create(concatenatedFlux.log())
                .expectSubscription()
                .expectNext("A",  "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    public void combine_Using_Concat_With_Delay() {
        Flux<String> flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofSeconds(1));
        Flux<String> flux2 = Flux.just("D", "E", "F").delayElements(Duration.ofSeconds(1));

        Flux<String> concatenatedFlux = Flux.concat(flux1, flux2);

        StepVerifier.create(concatenatedFlux.log())
                .expectSubscription()
                .expectNext("A",  "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    public void combine_Using_zip() {
        Flux<String> flux1 = Flux.just("A", "B", "C");
        Flux<String> flux2 = Flux.just("D", "E", "F");

        Flux<String> zippedFlux = Flux.zip(flux1, flux2, (t1, t2) -> t1.concat(t2));

        StepVerifier.create(zippedFlux.log())
                .expectSubscription()
                .expectNext("AD","BE", "CF")
                .verifyComplete();
    }
}
