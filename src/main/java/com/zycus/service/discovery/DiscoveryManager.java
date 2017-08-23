package com.zycus.service.discovery;

import com.zycus.ext.commons.configuration2.ConfigurationManager;
import com.zycus.service.ServiceItem;
import com.zycus.service.ServiceMetadata;
import com.zycus.service.discovery.api.ServiceQuery;
import com.zycus.service.discovery.api.ServiceRegistration;
import com.zycus.service.discovery.api.impl.ServiceQueryBuilder;
import com.zycus.service.discovery.api.impl.ServiceRegistrationImpl;
import com.zycus.service.ex.InternalServiceException;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

//TODO: make it use config library
public class DiscoveryManager<T> {

    private Configuration configuration;
    private Class<T> metadataClass;
    private final AtomicReference<DiscoveryClient<T>> discoveryClientAtomicReference = new AtomicReference<>();
    private final AtomicReference<ServiceRegistration<T>> serviceRegistrationAtomicReference = new AtomicReference<>();
    private final AtomicReference<ServiceQuery<T>> serviceQueryAtomicReference = new AtomicReference<>();
    public static final String CONN_STR = "service.connStr";
    public static final String SRVC_PORT = "service.port";
    public static final String SRVC_BASEPATH = "service.basePath";
    public static final String SRVC_ADDR = "service.addr";
    public static final String SRVC_NAME = "service.name";
    public static final String SRVC_DESC = "service.desc";
    public static final String SRVC_TAGS = "service.tags";
    public static final String SRVC_TENANTS = "service.tenants";
    public static final String SRVC_LABELS = "service.label";


    public static <T> DiscoveryManager getInstance(Class<T> metadataClass) {
        return new DiscoveryManager<T>(metadataClass);
    }

    private DiscoveryManager(Class<T> metadataClass) {
        this.metadataClass = metadataClass;
        try {
            configuration = ConfigurationManager.getConfigInstance();
        } catch (ConfigurationException e) {
            throw new InternalServiceException(e.getMessage(), e);
        }
    }

    public DiscoveryClient<T> getDiscoveryClient() {

        discoveryClientAtomicReference.compareAndSet(null, DiscoveryClient.<T>getInstance(metadataClass, configuration.getString(CONN_STR, "localhost:2181"), configuration.getString(SRVC_BASEPATH, "/discovery/dev"), new ExponentialBackoffRetry(1000, 3)).start());
        return discoveryClientAtomicReference.get();
    }

    public void registerThisService() {

        ServiceItem.<T>builder()
                .withAddress(configuration.getString(SRVC_ADDR))
                .withPort(configuration.getInteger(SRVC_PORT, 0))
                .withServiceName(configuration.getString(SRVC_NAME))
                .withServiceMetadata(getThisServiceMetadata());
        registerThisService(getThisServiceMetadata());
    }

    public void registerThisService(Object metadata) {
        try {
            getRegistrationApi().registerService(ServiceItem.<T>builder()
                    .withAddress(configuration.getString(SRVC_ADDR))
                    .withPort(configuration.getInteger(SRVC_PORT, 0))
                    .withServiceName(configuration.getString(SRVC_NAME))
                    .withServiceMetadata(metadata).build());
        } catch (Exception e) {
            throw new InternalServiceException(e.getMessage(), e);
        }

    }


    private ServiceMetadata getThisServiceMetadata() {
        ServiceMetadata metadata = new ServiceMetadata();
        metadata.setDescription(configuration.getString(SRVC_DESC, ""));
        String tenantsStr = configuration.getString(SRVC_TENANTS);
        if (tenantsStr != null)
            metadata.addTenants(tenantsStr.split(","));
        String tagsStr = configuration.getString(SRVC_TAGS);
        if (tagsStr != null)
            metadata.addTags(tagsStr.split(","));
        Configuration labelConf = configuration.subset(SRVC_LABELS);
        if (labelConf != null) {
            Iterator<String> keyItr = labelConf.getKeys();
            while (keyItr.hasNext()) {
                String key = keyItr.next();
                metadata.addLabel(key, labelConf.getString(key));
            }
        }

        return metadata;
    }

    public ServiceQueryBuilder<T> queryApi() {
        return new ServiceQueryBuilder<T>(getDiscoveryClient(), this);
    }

    public void setQueryApi(ServiceQuery<T> serviceQuery) {

        serviceQueryAtomicReference.set(serviceQuery);
    }


    public ServiceRegistration<T> getRegistrationApi() {
        serviceRegistrationAtomicReference.compareAndSet(null, new ServiceRegistrationImpl<T>(getDiscoveryClient()));
        return serviceRegistrationAtomicReference.get();
    }

    public ServiceQuery<T> getQueryApi() {
        return serviceQueryAtomicReference.get();
    }


}
