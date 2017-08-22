package com.zycus.service.client;

import com.zycus.service.ServiceItem;
import com.zycus.service.ServiceMetadata;
import com.zycus.service.discovery.filter.ServiceFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kuldeep.gupta on 8/22/2017.
 */
public class TenantFilter implements ServiceFilter<ServiceMetadata> {
    private static final String TENANT_KEY = "tenants";
    List<String> tenantsToMatch;

    public TenantFilter(String... tenants) {
        tenantsToMatch = new ArrayList<>();
        for(String tenant : tenants) {
            tenantsToMatch.add(tenant.trim());
        }
        //tenantsToMatch = Arrays.asList(tenants);

    }

    public boolean apply(ServiceItem<ServiceMetadata> input) {
        System.out.println("TenantFilter on input: " + input);
        System.out.println("tenantsToMatch: " + tenantsToMatch);
        //boolean result = input.getMetadata().getTenants().containsAll(tenantsToMatch);
        boolean result = true;
        System.out.println("returning result: " + result);
        return result;
        //return input.getMetadata().getTenants().containsAll(tenantsToMatch);

    }
}
