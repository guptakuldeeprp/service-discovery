package com.zycus.service.discovery.filter;

import com.zycus.service.ServiceItem;

public interface ServiceFilter<T> {

    boolean apply(ServiceItem<T> serviceItem);

}
