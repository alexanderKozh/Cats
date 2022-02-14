package com.lex.cat;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

public abstract class AbstractTest {

    protected static final long RATE_LIMIT = 5;

    private static final PostgreSQLContainer<?> sqlContainer;

    static {
        sqlContainer = new PostgreSQLContainer<>("postgres:11.1")
                .withDatabaseName("integration-tests-db")
                .withUsername("sa")
                .withPassword("sa")
                .withInitScript("ddl.sql");
        sqlContainer.start();
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + sqlContainer.getJdbcUrl() + "&stringtype=unspecified",
                    "spring.datasource.username=" + sqlContainer.getUsername(),
                    "spring.datasource.password=" + sqlContainer.getPassword(),
                    "rate.limit=" + RATE_LIMIT
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
