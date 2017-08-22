package com.zycus.service.discovery.api;

import com.zycus.service.ServiceItem;

public interface ServiceRegistration<T> {

    /**
     * Register/re-register a service
     *
     * @param service service to add
     * @throws Exception errors
     */
    public void registerService(ServiceItem<T> service) throws Exception;

    /**
     * Update a service
     *
     * @param service service to update
     * @throws Exception errors
     */
    public void updateService(ServiceItem<T> service) throws Exception;

    /**
     * Unregister/remove a service instance
     *
     * @param service the service
     * @throws Exception errors
     */
    public void unregisterService(ServiceItem<T> service) throws Exception;

}
