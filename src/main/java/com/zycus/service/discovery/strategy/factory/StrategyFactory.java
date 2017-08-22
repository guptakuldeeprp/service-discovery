package com.zycus.service.discovery.strategy.factory;

import com.zycus.service.discovery.strategy.SelectorStrategy;

public abstract class StrategyFactory<T extends SelectorStrategy<?>> {

    protected Class<?> strategyClass;

    public StrategyFactory(Class<?> clazz) {
        this.strategyClass = clazz;
    }

    public abstract T getStrategy();

    public Class<?> getStrategyClass() {
        return strategyClass;
    }
}
