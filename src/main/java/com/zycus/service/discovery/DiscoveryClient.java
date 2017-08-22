package com.zycus.service.discovery;

import com.zycus.service.ex.InternalServiceException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;

//TODO: figure out instantiation
public class DiscoveryClient<T> {

    private static final Log logger = LogFactory.getLog(DiscoveryClient.class);
    private String basePath;
    private Class<T> metadataClass;
    private CuratorFramework client;
    private ServiceDiscovery<T> serviceDiscovery;

    public static <T> DiscoveryClient<T> getInstance(Class<T> metadataClass, String connectionString, String basePath, RetryPolicy retryPolicy) {
        //return new DiscoveryClient<>(metadataClass);
        return new DiscoveryClient<>(metadataClass, connectionString, basePath, retryPolicy);
    }

    private DiscoveryClient(Class<T> metadataClass, String connectionString, String basePath, RetryPolicy retryPolicy) {
        client = CuratorFrameworkFactory.newClient(connectionString, retryPolicy);
        serviceDiscovery = ServiceDiscoveryBuilder.builder(metadataClass).client(client).basePath(basePath).build();
    }

    public DiscoveryClient<T> start() {
        try {
            client.start();
            serviceDiscovery.start();
            return this;
        } catch (Exception e) {
            throw new InternalServiceException(e.getMessage(), e);
        }
    }

    public void stop() {
        try {
            serviceDiscovery.close();
            client.close();
        } catch (Exception e) {
            //TODO: Decide whether to log a warning/throw checked exception
            throw new InternalServiceException(e.getMessage(), e);
        }
    }


    public ServiceDiscovery<T> getServiceDiscovery() {
        return serviceDiscovery;
    }


}
