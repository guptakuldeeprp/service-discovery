package com.zycus.service.discovery.strategy;

import com.zycus.service.ServiceItem;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class StickyStrategy<T> implements SelectorStrategy<T> {

    private final SelectorStrategy<T> masterStrategy;
    private final AtomicReference<ServiceItem<T>> ourInstance = new AtomicReference<ServiceItem<T>>(null);
    private final AtomicInteger instanceNumber = new AtomicInteger(-1);


    public StickyStrategy(SelectorStrategy<T> masterStrategy) {
        this.masterStrategy = masterStrategy;
    }

    @Override
    public ServiceItem<T> selectService(List<ServiceItem<T>> serviceItems) {
        if (serviceItems == null || serviceItems.size() == 0) {
            return null;
        }
        ServiceItem<T> localOurInstance = ourInstance.get();
        if (!serviceItems.contains(localOurInstance)) {
            ourInstance.compareAndSet(localOurInstance, null);
        }


        if (ourInstance.get() == null) {
            ServiceItem<T> instance = masterStrategy.selectService(serviceItems);

            if (ourInstance.compareAndSet(null, instance)) {
                instanceNumber.incrementAndGet();
            }
        }
        return ourInstance.get();

    }
}
