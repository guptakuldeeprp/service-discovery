package com.zycus.service.discovery.api;

import com.zycus.service.ServiceItem;
import com.zycus.service.discovery.filter.ServiceFilter;
import com.zycus.service.discovery.strategy.SelectorStrategy;

import java.util.Collection;

public interface ServiceQuery<T> {

    /**
     * Initialize the service for querying. This method can be used as a part of startup initialization of the application
     * to make the querying more performant in rest of the application
     */
    //public void initialize(String serviceName);


    //public void initialize(String... serviceNames);

    public void configure(String serviceName, SelectorStrategy<T> selectorStrategy) throws Exception;

    public void configure(String serviceName, ServiceFilter<T> serviceFilter, SelectorStrategy<T> selectorStrategy) throws Exception;

    /**
     * Return an item for a single use. <b>IMPORTANT: </b> users
     * should not hold on to the instance returned. They should always get a fresh instance.
     *
     * @return the instance to use
     * @throws Exception any errors
     */
    public ServiceItem<T> getInstance(String serviceName) throws Exception;

    /**
     * Return the current available set of instances <b>IMPORTANT: </b> users
     * should not hold on to the instance returned. They should always get a fresh list.
     *
     * @return all known instances
     * @throws Exception any errors
     */
    public Collection<ServiceItem<T>> getAllInstances(String serviceName) throws Exception;


    public Collection<String> getAllNames() throws Exception;

}
