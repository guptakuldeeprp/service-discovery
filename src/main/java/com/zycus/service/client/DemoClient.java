package com.zycus.service.client;

import com.zycus.service.ServiceMetadata;
import com.zycus.service.discovery.DiscoveryManager;
import com.zycus.service.discovery.api.ServiceQuery;
import com.zycus.service.discovery.strategy.RandomStrategy;
import com.zycus.service.discovery.strategy.factory.SimpleStrategyFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;

import java.net.InetAddress;

/**
 * Created by kuldeep.gupta on 8/22/2017.
 */
public class DemoClient {


    public static void main(String[] args) throws Exception {

//        DiscoveryManager<ServiceMetadata> dm = DiscoveryManager.<ServiceMetadata>getInstance(ServiceMetadata.class);
//        SimpleStrategyFactory<RandomStrategy<ServiceMetadata>> ssf = new SimpleStrategyFactory<RandomStrategy<ServiceMetadata>>(RandomStrategy.class);
//        TenantFilter filter = new TenantFilter("t1", "t2");
//        dm.queryApi().withSelectorStrategyFactory(ssf).withServiceFilter(filter).build();
//        ServiceQuery<ServiceMetadata> queryApi = dm.getQueryApi();
//
//        System.out.println("instance: " + queryApi.getInstance("demo"));

        System.out.println("starting");
        System.out.println(InetAddress.getLocalHost().getCanonicalHostName());
        System.out.println("started");

//        System.out.println("Starting..");
//        CuratorFramework client = CuratorFrameworkFactory.newClient("localhost:2181", new RetryNTimes(3, 1000));
//        System.out.println("Started!");

    }

}
