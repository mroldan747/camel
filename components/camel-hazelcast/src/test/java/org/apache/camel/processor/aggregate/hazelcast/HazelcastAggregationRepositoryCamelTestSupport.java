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
package org.apache.camel.processor.aggregate.hazelcast;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import org.apache.camel.test.infra.hazelcast.services.HazelcastService;
import org.apache.camel.test.infra.hazelcast.services.HazelcastServiceFactory;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.RegisterExtension;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HazelcastAggregationRepositoryCamelTestSupport extends CamelTestSupport {

    private static HazelcastInstance hzOne;
    private static HazelcastInstance hzTwo;

    @RegisterExtension
    protected static HazelcastService service = HazelcastServiceFactory.createService();

    protected static HazelcastInstance getFirstInstance() {
        return hzOne;
    }

    protected static HazelcastInstance getSecondInstance() {
        return hzTwo;
    }

    @BeforeAll
    public static void setUpHazelcastCluster() {
        hzOne = HazelcastClient.newHazelcastClient(createConfig("hzOne"));
        hzTwo = HazelcastClient.newHazelcastClient(createConfig("hzTwo"));
    }

    @AfterAll
    public static void shutDownHazelcastCluster() {
        if (hzOne != null) {
            hzOne.shutdown();
        }

        if (hzTwo != null) {
            hzTwo.shutdown();
        }

    }

    private static ClientConfig createConfig(String name) {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setInstanceName(name);
        clientConfig.getMetricsConfig().setEnabled(false);
        clientConfig.getNetworkConfig().addAddress(service.getServiceAddress());
        return clientConfig;
    }
}
