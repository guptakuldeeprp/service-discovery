package com.zycus.service.discovery;

import com.zycus.service.discovery.api.ServiceQuery;
import com.zycus.service.discovery.api.ServiceRegistration;

public interface ServiceDiscoveryFramework<T> {

    ServiceQuery<T> getServiceQuery();

    ServiceRegistration<T> getServiceRegistration();

}
