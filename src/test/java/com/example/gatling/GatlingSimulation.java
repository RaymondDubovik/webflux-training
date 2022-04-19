package com.example.gatling;

import java.time.Duration;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.constantUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.rampUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.core.CoreDsl.stressPeakUsers;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class GatlingSimulation extends Simulation {

    HttpProtocolBuilder protocol = http
            .baseUrl("http://localhost:9000")
            .acceptHeader("application/json")
            .userAgentHeader("Gatling Load Test");

    ScenarioBuilder scenario = scenario("Load Test Getting List")
            .exec(http("get-customer-request")
                          .get("/")
                          .check(status().is(200))
                 );

    public GatlingSimulation() {
        setUp(scenario.injectOpen(
                rampUsersPerSec(100).to(1000).during(Duration.ofSeconds(10)),
                constantUsersPerSec(1000).during(Duration.ofSeconds(10))
             ))
             .protocols(protocol);
    }
}
