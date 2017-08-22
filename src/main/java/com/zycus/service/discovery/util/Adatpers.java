package com.zycus.service.discovery.util;

import com.zycus.service.ServiceItem;
import com.zycus.service.discovery.filter.ServiceFilter;
import com.zycus.service.discovery.strategy.SelectorStrategy;
import org.apache.curator.x.discovery.InstanceFilter;
import org.apache.curator.x.discovery.ProviderStrategy;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.InstanceProvider;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Adatpers {

    public static <T> ProviderStrategy<T> wrap(final SelectorStrategy<T> selectorStrategy) {
        //System.out.println("selectorStrategy: " + selectorStrategy);
        if(selectorStrategy == null) return null;
        return new ProviderStrategy<T>() {

            SelectorStrategy<T> getSelectorStrategy() {
                return selectorStrategy;
            }

            @Override
            public ServiceInstance<T> getInstance(InstanceProvider<T> instanceProvider) throws Exception {
                //System.out.println("instanceProvider.getInstances(): " + instanceProvider.getInstances());
                //System.out.println("getSelectorStrategy(): " + getSelectorStrategy());
                Thread.dumpStack();
                return selectorStrategy.
                        selectService(
                                wrap(
                                        instanceProvider.getInstances())).
                        _serviceInst();
            }
        };

    }

    public static <T> InstanceFilter<T> wrap(final ServiceFilter<T> serviceFilter) {
        if(serviceFilter == null) return null;
        return new InstanceFilter<T>() {
            @Override
            public boolean apply(@Nullable ServiceInstance<T> input) {
                System.out.println("applying filter to input " + input);
                return serviceFilter.apply(wrap(input));
            }
        };
    }

    public static <T> ServiceItem<T> wrap(ServiceInstance<T> instance) {

        return new ServiceItem<T>(instance);
    }

    public static <T> List<ServiceItem<T>> wrap(Collection<ServiceInstance<T>> instances) {
        List<ServiceItem<T>> result = new ArrayList<>();
        if(instances != null) {
            for (ServiceInstance<T> instance : instances) {
                result.add(wrap(instance));
            }
        } else {
            System.out.println("null service instances");
        }
        return result;
    }


}
