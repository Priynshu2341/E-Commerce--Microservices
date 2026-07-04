package com.venom.ecommerce;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MongoDBContainer;

import static org.assertj.core.api.Assertions.assertThat;

public class DockerOnlyTest {

    @Test
    void shouldStartMongo(){
        try (MongoDBContainer mongo = new MongoDBContainer("mongo:8.0")) {
            mongo.start();

            assertThat(mongo.isRunning()).isTrue();
        }
    }
}
