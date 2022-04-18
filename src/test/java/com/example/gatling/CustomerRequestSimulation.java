package com.example.gatling;

import java.time.Duration;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.constantUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class CustomerRequestSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:9000")
            .acceptHeader("application/json")
            .userAgentHeader("Gatling Performance Test");

    ScenarioBuilder scn = scenario("Load Test Getting List")
            .exec(http("get-customer-request")
                          .get("/")
                          .check(status().is(200))
                 );

    public CustomerRequestSimulation() {
        this.setUp(scn.injectOpen(constantUsersPerSec(50).during(Duration.ofSeconds(10))))
                .protocols(httpProtocol);
    }
}
