package com.pramod.reactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxTest {

    @Test
    public void fluxTest() {
        Flux<String> stringFlux = Flux.just("Spring", "Spring boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Exception in Flux occurred")))
                .concatWith(Flux.just("After Exception"))
                .log();
        stringFlux
                .subscribe(System.out::println,
                        (e) -> System.err.println(e),
                        () -> System.out.println("Completed"));
    }

    @Test
    public void fluxTest_WithError() {
        Flux<String> stringFlux = Flux.just("Spring", "Spring boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Exception in Flux occurred")))
                .concatWith(Flux.just("After Exception"))
                .log();
        stringFlux
                .subscribe(System.out::println,
                        (e) -> System.err.println(e));
    }

    @Test
    public void fluxTest_WithError_And_Completion_Process_At_End() {
        Flux<String> stringFlux = Flux.just("Spring", "Spring boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Exception in Flux occurred")))
                .concatWith(Flux.just("After Exception"))
                .log();
        stringFlux
                .subscribe(System.out::println,
                        (e) -> System.err.println(e),
                        () -> System.out.println("Completed"));
    }

    @Test
    public void fluxTestElements_Without_Error() {
        Flux<String> stringFlux = Flux.just("Spring", "Spring boot", "Reactive Spring")
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring boot")
                .expectNext("Reactive Spring")
                .verifyComplete();

    }

    @Test
    public void fluxTestElements_With_Error() {
        Flux<String> stringFlux = Flux.just("Spring", "Spring boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Exception in Flux occurred")))
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring boot")
                .expectNext("Reactive Spring")
                .expectError(RuntimeException.class)
                .verify();

    }

    @Test
    public void fluxTestElements_With_Error_Using_One_expectNext() {
        Flux<String> stringFlux = Flux.just("Spring", "Spring boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Exception in Flux occurred")))
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("Spring", "Spring boot", "Reactive Spring")
                .expectError(RuntimeException.class)
                .verify();

    }

    @Test
    public void fluxTestElements_With_ErrorMessage() {
        Flux<String> stringFlux = Flux.just("Spring", "Spring boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Exception in Flux occurred")))
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring boot")
                .expectNext("Reactive Spring")
                .expectErrorMessage("Exception in Flux occurred")
                .verify();

    }

    @Test
    public void fluxTestElementsCount_WithError() {
        Flux<String> stringFlux = Flux.just("Spring", "Spring boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Exception in Flux occurred")))
                .log();

        StepVerifier.create(stringFlux)
                .expectNextCount(3)
                .expectErrorMessage("Exception in Flux occurred")
                .verify();

    }
}
