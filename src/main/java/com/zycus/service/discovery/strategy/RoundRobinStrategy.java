package com.zycus.service.discovery.strategy;

import com.zycus.service.ServiceItem;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobinStrategy<T> implements SelectorStrategy<T> {

    private final AtomicInteger index = new AtomicInteger(0);

    @Override
    public ServiceItem<T> selectService(List<ServiceItem<T>> serviceItems) {
        if (serviceItems == null || serviceItems.size() == 0) {
            return null;
        }
        int thisIndex = Math.abs(index.getAndIncrement());
        return serviceItems.get(thisIndex % serviceItems.size());
    }
}
