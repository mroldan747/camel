/* Generated by camel build tools - do NOT edit this file! */
package org.apache.camel.component.mllp;

import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.camel.spi.EndpointUriFactory;

/**
 * Generated by camel build tools - do NOT edit this file!
 */
public class MllpEndpointUriFactory extends org.apache.camel.support.component.EndpointUriFactorySupport implements EndpointUriFactory {

    private static final String BASE = ":hostname:port";

    private static final Set<String> PROPERTY_NAMES;
    private static final Set<String> SECRET_PROPERTY_NAMES;
    private static final Set<String> MULTI_VALUE_PREFIXES;
    static {
        Set<String> props = new HashSet<>(28);
        props.add("bindRetryInterval");
        props.add("lenientBind");
        props.add("idleTimeoutStrategy");
        props.add("hostname");
        props.add("bridgeErrorHandler");
        props.add("bindTimeout");
        props.add("connectTimeout");
        props.add("stringPayload");
        props.add("acceptTimeout");
        props.add("requireEndOfData");
        props.add("tcpNoDelay");
        props.add("autoAck");
        props.add("maxConcurrentConsumers");
        props.add("keepAlive");
        props.add("hl7Headers");
        props.add("sendBufferSize");
        props.add("exchangePattern");
        props.add("reuseAddress");
        props.add("lazyStartProducer");
        props.add("backlog");
        props.add("receiveBufferSize");
        props.add("port");
        props.add("receiveTimeout");
        props.add("readTimeout");
        props.add("idleTimeout");
        props.add("exceptionHandler");
        props.add("charsetName");
        props.add("validatePayload");
        PROPERTY_NAMES = Collections.unmodifiableSet(props);
        SECRET_PROPERTY_NAMES = Collections.emptySet();
        MULTI_VALUE_PREFIXES = Collections.emptySet();
    }

    @Override
    public boolean isEnabled(String scheme) {
        return "mllp".equals(scheme);
    }

    @Override
    public String buildUri(String scheme, Map<String, Object> properties, boolean encode) throws URISyntaxException {
        String syntax = scheme + BASE;
        String uri = syntax;

        Map<String, Object> copy = new HashMap<>(properties);

        uri = buildPathParameter(syntax, uri, "hostname", null, true, copy);
        uri = buildPathParameter(syntax, uri, "port", null, true, copy);
        uri = buildQueryParameters(uri, copy, encode);
        return uri;
    }

    @Override
    public Set<String> propertyNames() {
        return PROPERTY_NAMES;
    }

    @Override
    public Set<String> secretPropertyNames() {
        return SECRET_PROPERTY_NAMES;
    }

    @Override
    public Set<String> multiValuePrefixes() {
        return MULTI_VALUE_PREFIXES;
    }

    @Override
    public boolean isLenientProperties() {
        return false;
    }
}

