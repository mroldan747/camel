/* Generated by camel build tools - do NOT edit this file! */
package org.apache.camel.component.nats;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.camel.spi.EndpointUriFactory;

/**
 * Generated by camel build tools - do NOT edit this file!
 */
public class NatsEndpointUriFactory extends org.apache.camel.support.component.EndpointUriFactorySupport implements EndpointUriFactory {

    private static final String BASE = ":topic";

    private static final Set<String> PROPERTY_NAMES;
    static {
        Set<String> set = new HashSet<>(29);
        set.add("topic");
        set.add("connectionTimeout");
        set.add("flushConnection");
        set.add("flushTimeout");
        set.add("maxPingsOut");
        set.add("maxReconnectAttempts");
        set.add("noEcho");
        set.add("noRandomizeServers");
        set.add("pedantic");
        set.add("pingInterval");
        set.add("reconnect");
        set.add("reconnectTimeWait");
        set.add("requestCleanupInterval");
        set.add("servers");
        set.add("verbose");
        set.add("bridgeErrorHandler");
        set.add("maxMessages");
        set.add("poolSize");
        set.add("queueName");
        set.add("replyToDisabled");
        set.add("exceptionHandler");
        set.add("exchangePattern");
        set.add("lazyStartProducer");
        set.add("replySubject");
        set.add("basicPropertyBinding");
        set.add("connection");
        set.add("synchronous");
        set.add("secure");
        set.add("sslContextParameters");
        PROPERTY_NAMES = set;
    }


    @Override
    public boolean isEnabled(String scheme) {
        return "nats".equals(scheme);
    }

    @Override
    public String buildUri(String scheme, Map<String, Object> properties) throws URISyntaxException {
        String syntax = scheme + BASE;
        String uri = syntax;

        Map<String, Object> copy = new HashMap<>(properties);

        uri = buildPathParameter(syntax, uri, "topic", null, true, copy);
        uri = buildQueryParameters(uri, copy);
        return uri;
    }

    @Override
    public Set<String> propertyNames() {
        return PROPERTY_NAMES;
    }

    @Override
    public boolean isLenientProperties() {
        return false;
    }
}
