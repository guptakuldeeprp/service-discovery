package com.zycus.service.discovery.api.impl;

import com.google.common.cache.*;
import com.zycus.service.ServiceItem;
import com.zycus.service.discovery.api.ServiceQuery;
import com.zycus.service.discovery.filter.ServiceFilter;
import com.zycus.service.discovery.DiscoveryClient;
import com.zycus.service.discovery.strategy.SelectorStrategy;
import com.zycus.service.discovery.strategy.factory.StrategyFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.ServiceProvider;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.locks.ReentrantLock;

import static com.zycus.service.discovery.util.Adatpers.wrap;

public class ServiceQueryImpl<T> implements ServiceQuery<T> {

    private static final Log logger = LogFactory.getLog(ServiceQueryImpl.class);
    private ReentrantLock lock = new ReentrantLock();

    //DiscoveryClient<T> discoveryClient =
    private ServiceFilter<T> defaultServiceFilter;
    private StrategyFactory<? extends SelectorStrategy<T>> defaultStrategyFactory;
    private DiscoveryClient<T> discoveryClient;
    private Cache<String, ServiceProvider<T>> cacheInstance;

    ServiceQueryImpl(final ServiceFilter<T> defaultServiceFilter, final StrategyFactory<? extends SelectorStrategy<T>> defaultStrategyFactory, final DiscoveryClient<T> discoveryClient) {

        System.out.println("defaultServiceFilter: " + defaultServiceFilter);
        System.out.println("defaultSelectorStrategy: " + defaultStrategyFactory);
        System.out.println("discoveryClient: " + discoveryClient);
        this.defaultServiceFilter = defaultServiceFilter;
        this.defaultStrategyFactory = defaultStrategyFactory;
        this.discoveryClient = discoveryClient;
        cacheInstance = CacheBuilder.<String, ServiceProvider<T>>newBuilder().softValues().removalListener(new RemovalListener<String, ServiceProvider<T>>() {

            public void onRemoval(RemovalNotification<String, ServiceProvider<T>> removalNotification) {
                try {
                    logger.info("Removing cached instance if ServiceProvider for " + removalNotification.getKey());
                    removalNotification.getValue().close();
                } catch (IOException e) {
                    logger.warn("Internal error while closing ServiceProvider: " + e.getLocalizedMessage());
                }
            }
        }).build();
//        .build(new CacheLoader<String, ServiceProvider<T>>() {
//            @Override
//            public ServiceProvider<T> load(String key) throws Exception {
//                ServiceProvider<T> provider = discoveryClient.getServiceDiscovery()
//                        .serviceProviderBuilder()
//                        .serviceName(key)
//                        .additionalFilter(wrap(defaultServiceFilter))
//                        .providerStrategy(wrap(defaultStrategyFactory.getStrategy()))
//                        .build();
//                provider.start();
//                return provider;
//            }
//        });

    }

    @Override
    public void configure(String serviceName, SelectorStrategy<T> selectorStrategy) throws Exception {
        lock.lock();
        try {
            cacheInstance.invalidate(serviceName);
            ServiceProvider<T> provider = discoveryClient.getServiceDiscovery()
                    .serviceProviderBuilder()
                    .serviceName(serviceName)
                    .additionalFilter(wrap(defaultServiceFilter))
                    .providerStrategy(wrap(selectorStrategy))
                    .build();
            provider.start();
            cacheInstance.put(serviceName, provider);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void configure(String serviceName, ServiceFilter<T> serviceFilter, SelectorStrategy<T> selectorStrategy) throws Exception {
        lock.lock();
        try {
            cacheInstance.invalidate(serviceName);
            ServiceProvider<T> provider = discoveryClient.getServiceDiscovery()
                    .serviceProviderBuilder()
                    .serviceName(serviceName)
                    .additionalFilter(wrap(serviceFilter))
                    .providerStrategy(wrap(selectorStrategy))
                    .build();
            provider.start();
            cacheInstance.put(serviceName, provider);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public ServiceItem<T> getInstance(final String serviceName) throws Exception {
        ServiceProvider<T> provider = discoveryClient.getServiceDiscovery()
                .serviceProviderBuilder()
                .serviceName(serviceName)
                //.additionalFilter(wrap(defaultServiceFilter))
                //.providerStrategy(wrap(defaultStrategyFactory.getStrategy()))
                .additionalFilter(wrap(defaultServiceFilter))
                .providerStrategy(wrap(defaultStrategyFactory.getStrategy()))
                .build();
        provider.start();
        ServiceInstance<T> inst = provider.getInstance();
        System.out.println("ServiceQueryImpl instance: " + inst);
        return inst == null ? null : wrap(inst);
        //return wrap(cacheInstance.get(serviceName).getInstance());
        //return null;
    }

    @Override
    public Collection<ServiceItem<T>> getAllInstances(String serviceName) throws Exception {
        return null;
//        return wrap(cacheInstance.get(serviceName)
//                .getAllInstances());
    }

    @Override
    public Collection<String> getAllNames() throws Exception {
        return discoveryClient.getServiceDiscovery().queryForNames();
    }


}
