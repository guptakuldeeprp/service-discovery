package com.zycus.service.discovery.strategy;

import com.zycus.service.ServiceItem;

import java.util.List;
import java.util.Random;

public class RandomStrategy<T> implements SelectorStrategy<T> {
    private final Random random = new Random();

    @Override
    public ServiceItem<T> selectService(List<ServiceItem<T>> serviceItems) {
        System.out.println("serviceItems: " + serviceItems);
        if (serviceItems == null || serviceItems.size() == 0) {
            return null;
        }
        int thisIndex = random.nextInt(serviceItems.size());
        return serviceItems.get(thisIndex);
    }
}
