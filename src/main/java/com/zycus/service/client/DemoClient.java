package com.zycus.service.client;

import com.zycus.service.ServiceMetadata;
import com.zycus.service.discovery.DiscoveryManager;
import com.zycus.service.discovery.api.ServiceQuery;
import com.zycus.service.discovery.strategy.RandomStrategy;
import com.zycus.service.discovery.strategy.factory.SimpleStrategyFactory;
import org.apache.curator.x.discovery.ServiceProvider;

import java.util.Map;

/**
 * Created by kuldeep.gupta on 8/22/2017.
 */
public class DemoClient {



    public static void main(String[] args) throws Exception {

        DiscoveryManager<ServiceMetadata> dm = DiscoveryManager.<ServiceMetadata>getInstance(ServiceMetadata.class);
        SimpleStrategyFactory<RandomStrategy<ServiceMetadata>> ssf = new SimpleStrategyFactory<RandomStrategy<ServiceMetadata>>(RandomStrategy.class);
        TenantFilter filter = new TenantFilter("t1","t2");
        dm.queryApi().withSelectorStrategyFactory(ssf).withServiceFilter(filter).build();
        ServiceQuery<ServiceMetadata> queryApi = dm.getQueryApi();

        System.out.println("instance: " + queryApi.getInstance("demo"));

//        DiscoveryManager<ServiceMetadata> dm = DiscoveryManager.<ServiceMetadata>getInstance(ServiceMetadata.class);
//        ServiceProvider<ServiceMetadata> provider = dm.getDiscoveryClient().getServiceDiscovery().serviceProviderBuilder().serviceName("demo").providerStrategy(new org.apache.curator.x.discovery.strategies.RandomStrategy<ServiceMetadata>()).build();
//        provider.start();
//        System.out.println(provider.getInstance());
    }

}
