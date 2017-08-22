package com.zycus.service.discovery.api.impl;

import com.zycus.service.discovery.DiscoveryClient;
import com.zycus.service.discovery.DiscoveryManager;
import com.zycus.service.discovery.api.ServiceQuery;
import com.zycus.service.discovery.filter.ServiceFilter;
import com.zycus.service.discovery.strategy.SelectorStrategy;
import com.zycus.service.discovery.strategy.factory.StrategyFactory;

//TODO: Remove coupling with DiscoveryManager
public class ServiceQueryBuilder<T> {

    private ServiceFilter<T> serviceFilter;
    private StrategyFactory<? extends SelectorStrategy<T>> selectorStrategy;
    private DiscoveryClient<T> discoveryClient;
    private DiscoveryManager<T> discoveryManager;

    public ServiceQueryBuilder(DiscoveryClient<T> discoveryClient, DiscoveryManager<T> discoveryManager) {
        this.discoveryClient = discoveryClient;
        this.discoveryManager = discoveryManager;
    }

    public ServiceQueryBuilder<T> withServiceFilter(ServiceFilter<T> serviceFilter) {
        this.serviceFilter = serviceFilter;
        return this;
    }

    public ServiceQueryBuilder<T> withSelectorStrategyFactory(StrategyFactory<? extends SelectorStrategy<T>> selectorStrategy) {
        this.selectorStrategy = selectorStrategy;
        return this;
    }

//    public ServiceQueryBuilder<T> withDiscoveryClient(DiscoveryClient<T> discoveryClient) {
//        this.discoveryClient = discoveryClient;
//        return this;
//    }

    public DiscoveryManager<T> build() {
        discoveryManager.setQueryApi(new ServiceQueryImpl<>(serviceFilter, selectorStrategy, discoveryClient));
        return discoveryManager;
    }

    ServiceQuery<T> _build() {
        return new ServiceQueryImpl<>(serviceFilter, selectorStrategy, discoveryClient);
    }


}
