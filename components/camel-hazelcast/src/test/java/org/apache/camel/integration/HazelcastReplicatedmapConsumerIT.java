/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.integration;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.hazelcast.replicatedmap.ReplicatedMap;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.hazelcast.HazelcastConstants;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HazelcastReplicatedmapConsumerIT extends BaseHazelcast {

    private ReplicatedMap<Object, Object> map;

    @BeforeEach
    public void beforeEach() {
        if (map == null) {
            map = hazelcastInstance.getReplicatedMap("rm");
        }
        map.clear();
    }

    @Test
    public void testAdd() throws InterruptedException {
        MockEndpoint out = getMockEndpoint("mock:added");
        out.expectedMessageCount(1);

        map.put("4711", "my-foo");
        assertMockEndpointsSatisfied(5000, TimeUnit.MILLISECONDS);

        this.checkHeaders(out.getExchanges().get(0).getIn().getHeaders(), HazelcastConstants.ADDED);
    }

    /*
     * mail from talip (hazelcast) on 21.02.2011: MultiMap doesn't support eviction yet. We can and should add this feature.
     */
    @Test
    public void testEvict() throws InterruptedException {
        MockEndpoint out = getMockEndpoint("mock:evicted");
        out.expectedMessageCount(1);
        map.put("4711", "my-foo", 100, TimeUnit.MILLISECONDS);
        Thread.sleep(150);
        assertMockEndpointsSatisfied(30000, TimeUnit.MILLISECONDS);
    }

    @Test
    public void testRemove() throws InterruptedException {
        MockEndpoint out = getMockEndpoint("mock:removed");
        out.expectedMessageCount(1);
        map.put("4711", "my-foo");
        map.remove("4711");
        assertMockEndpointsSatisfied(5000, TimeUnit.MILLISECONDS);
        this.checkHeaders(out.getExchanges().get(0).getIn().getHeaders(), HazelcastConstants.REMOVED);
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from(String.format("hazelcast-%srm", HazelcastConstants.REPLICATEDMAP_PREFIX)).log("object...").choice()
                        .when(header(HazelcastConstants.LISTENER_ACTION).isEqualTo(HazelcastConstants.ADDED)).log("...added")
                        .to("mock:added")
                        .when(header(HazelcastConstants.LISTENER_ACTION).isEqualTo(HazelcastConstants.EVICTED))
                        .log("...evicted").to("mock:evicted")
                        .when(header(HazelcastConstants.LISTENER_ACTION).isEqualTo(HazelcastConstants.REMOVED))
                        .log("...removed").to("mock:removed").otherwise().log("fail!");
            }
        };
    }

    private void checkHeaders(Map<String, Object> headers, String action) {
        assertEquals(action, headers.get(HazelcastConstants.LISTENER_ACTION));
        assertEquals(HazelcastConstants.CACHE_LISTENER, headers.get(HazelcastConstants.LISTENER_TYPE));
        assertEquals("4711", headers.get(HazelcastConstants.OBJECT_ID));
        assertNotNull(headers.get(HazelcastConstants.LISTENER_TIME));
    }

}
