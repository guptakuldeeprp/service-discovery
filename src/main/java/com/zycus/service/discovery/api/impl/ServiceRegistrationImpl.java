package com.zycus.service.discovery.api.impl;

import com.zycus.service.ServiceItem;
import com.zycus.service.discovery.api.ServiceRegistration;
import com.zycus.service.discovery.DiscoveryClient;

public class ServiceRegistrationImpl<T> implements ServiceRegistration {

    private DiscoveryClient discoveryClient;

    public ServiceRegistrationImpl(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Override
    public void registerService(ServiceItem service) throws Exception {
        discoveryClient.getServiceDiscovery().registerService(service._serviceInst());
    }

    @Override
    public void updateService(ServiceItem service) throws Exception {
        discoveryClient.getServiceDiscovery().updateService(service._serviceInst());
    }

    @Override
    public void unregisterService(ServiceItem service) throws Exception {
        discoveryClient.getServiceDiscovery().unregisterService(service._serviceInst());
    }
}
