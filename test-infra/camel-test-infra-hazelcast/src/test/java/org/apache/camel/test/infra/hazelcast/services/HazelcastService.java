package org.apache.camel.test.infra.hazelcast.services;

import org.apache.camel.test.infra.common.services.TestService;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public interface HazelcastService extends BeforeAllCallback, AfterAllCallback, TestService {

    String getHost();

    int getPort();

    default String getServiceAddress() {
        return String.format("%s:%d", getHost(), getPort());
    }

    @Override
    default void beforeAll(ExtensionContext extensionContext) {
        initialize();
    }

    @Override
    default void afterAll(ExtensionContext extensionContext) {
        shutdown();
    }
}
