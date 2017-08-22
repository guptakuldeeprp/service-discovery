package com.zycus.service.discovery.strategy.factory;

import com.zycus.service.discovery.strategy.SelectorStrategy;
import com.zycus.service.ex.InternalServiceException;

import java.lang.reflect.Constructor;

public class SimpleStrategyFactory<T extends SelectorStrategy<?>> extends StrategyFactory<T> {


    public SimpleStrategyFactory(Class<?> clazz) {
        super(clazz);
    }

    @Override
    public T getStrategy() {
        try {
            for (Constructor constructor : getStrategyClass().getConstructors()) {
                // In Java 8+, use constructor.getParameterCount()
                if (constructor.getParameterTypes().length == 0) {
                    return (T) constructor.newInstance(null);
                }
            }
        } catch (Exception e) {
            throw new InternalServiceException(String.format("Could not instantiate strategy of type %s " + e.getMessage(), getStrategyClass().getName()), e);
        }
        throw new InternalServiceException(String.format("Could not instantiate strategy of type %s. no-arg constructor not found ", getStrategyClass().getName()));

    }
}
