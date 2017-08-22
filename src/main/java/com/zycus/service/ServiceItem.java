package com.zycus.service;

import com.zycus.service.ex.InternalServiceException;
import com.zycus.service.ex.ServiceException;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.ServiceInstanceBuilder;
import org.apache.curator.x.discovery.ServiceType;

import java.util.UUID;

public class ServiceItem<T> {

    private ServiceInstance<T> _serviceInst;

    ServiceItem(String name, String address, Integer port, T serviceMetadata) {
        // please use builder wherever possible.
        _serviceInst = new ServiceInstance<>(name, UUID.randomUUID().toString(), address, port, null, serviceMetadata, System.currentTimeMillis(), ServiceType.DYNAMIC, null, true);
    }

    public ServiceItem(ServiceInstance<T> serviceInst) {
        _serviceInst = serviceInst;
    }

    public static <T> ServiceItemBuilder<T> builder() {
        return new ServiceItemBuilder();
    }

    public String getName() {
        return _serviceInst.getName();
    }

    public String getAddress() {
        return _serviceInst.getAddress();
    }

    public Integer getPort() {
        return _serviceInst.getPort();
    }

    public T getMetadata() {
        return _serviceInst.getPayload();
    }

    public String getId() {
        return _serviceInst.getId();
    }

    public long getRegistrationTime() {
        return _serviceInst.getRegistrationTimeUTC();
    }

    /**
     * WARNING: Internal method, do not use this. Will be removed in future releases
     *
     * @return
     */
    @Deprecated
    public ServiceInstance<T> _serviceInst() {
        return _serviceInst;
    }

    @Override
    public String toString() {
        return _serviceInst.toString();
    }

    public static class ServiceItemBuilder<T> {

        private ServiceInstanceBuilder<T> _builder = null;

        private ServiceItemBuilder() {
            try {
                _builder = ServiceInstance.<T>builder();
            } catch (Exception e) {
                throw new InternalServiceException(e.getMessage(), e);
            }
        }

        public ServiceItemBuilder withServiceName(String serviceName) {
            _builder.name(serviceName);
            return this;
        }

        public ServiceItemBuilder withAddress(String address) {
            _builder.address(address);
            return this;
        }

        public ServiceItemBuilder withPort(Integer port) {
            _builder.port(port);
            return this;
        }

        public ServiceItemBuilder withServiceMetadata(T serviceMetadata) {
            _builder.payload(serviceMetadata);
            return this;
        }

        public ServiceItem build() {
            _builder.id(UUID.randomUUID().toString());
            _builder.serviceType(ServiceType.DYNAMIC);
            _builder.registrationTimeUTC(System.currentTimeMillis());
            return new ServiceItem(_builder.build());
        }

    }

}
