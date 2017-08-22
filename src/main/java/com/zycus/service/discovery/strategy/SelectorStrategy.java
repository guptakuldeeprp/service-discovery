package com.zycus.service.discovery.strategy;

import com.zycus.service.ServiceItem;

import java.util.List;

public interface SelectorStrategy<T> {

    public ServiceItem<T> selectService(List<ServiceItem<T>> items);

}
