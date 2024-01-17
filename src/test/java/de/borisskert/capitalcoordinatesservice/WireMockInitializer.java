package de.borisskert.capitalcoordinatesservice;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;

import java.util.Map;

public class WireMockInitializer
        implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        WireMockServer wireMockServer = new WireMockServer(new WireMockConfiguration().dynamicPort());
        wireMockServer.start();

        configurableApplicationContext
                .getBeanFactory()
                .registerSingleton("wireMockServer", wireMockServer);

        configurableApplicationContext.addApplicationListener(applicationEvent -> {
            if (applicationEvent instanceof ContextClosedEvent) {
                wireMockServer.stop();
            }
        });

        final String cityLocationClientUrl = "http://localhost:" + wireMockServer.port() + "/?addressdetails=1&q={city}&format=json&limit=1&accept-language=de";

        TestPropertyValues
                .of(Map.of("capital-coordinates-service.city-location-client.url", cityLocationClientUrl))
                .applyTo(configurableApplicationContext);
    }
}
