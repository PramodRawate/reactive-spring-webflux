package com.pramod.reactivespring.fluxandmonoplayground;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class MonoTest {

    @Test
    public void monoTest() {
        Mono<String> stringMono = Mono.just("Spring Boot")
                .log();

        stringMono
                .subscribe(System.out::println);
    }

    @Test
    @Disabled
    public void monoTest_WithError_And_StepVerifier() {
        StepVerifier.create(Mono.error(new RuntimeException("Mono exception")).log())
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    public void monoTest_With_StepVerifier() {
        Mono<String> stringMono = Mono.just("Spring Boot")
                .log();

        StepVerifier.create(stringMono.log())
                .expectNext("Spring Boot")
                .verifyComplete();
    }
}
