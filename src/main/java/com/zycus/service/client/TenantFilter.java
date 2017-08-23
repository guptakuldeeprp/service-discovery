package com.zycus.service.client;

import com.zycus.service.ServiceItem;
import com.zycus.service.ServiceMetadata;
import com.zycus.service.discovery.filter.ServiceFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kuldeep.gupta on 8/22/2017.
 */
public class TenantFilter implements ServiceFilter<ServiceMetadata> {
    private static final String TENANT_KEY = "tenants";
    List<String> tenantsToMatch;

    public TenantFilter(String... tenants) {
        tenantsToMatch = new ArrayList<>();
        for (String tenant : tenants) {
            tenantsToMatch.add(tenant.trim());
        }
        //tenantsToMatch = Arrays.asList(tenants);

    }

    public boolean apply(ServiceItem<ServiceMetadata> input) {
        System.out.println("TenantFilter on input: " + input);
        System.out.println("tenantsToMatch: " + tenantsToMatch);
        System.out.println("tenantsToMatch type: " + tenantsToMatch.getClass().getName());
        System.out.println("tenants type: " + input.getMetadata().getTenants().getClass().getName());
        System.out.println("tenants: " + input.getMetadata().getTenants());

        boolean result = input.getMetadata()
                .getTenants()
                .containsAll(tenantsToMatch);
        //boolean result = containsAll(tenantsToMatch, tenantsToMatch);
        return result;
        //return input.getMetadata().getTenants().containsAll(tenantsToMatch);

    }

    private boolean containsAll(List<String> candidate, List<String> target) {
        for (String str : candidate) {
            if (!target.contains(str)) {
                System.out.println("mismatch:" + str + "..");
                return false;
            }
        }
        return true;
    }

//    public static void main(String[] args) {
//        List<String> l1 = Arrays.asList(new String[]{"t1", "t2"});
//        List<String> l2 = new ArrayList<String>() {
//            {
//                add("t2");
//                add("t1");
//            }
//        };
//        System.out.println("l1: " + l1);
//        System.out.println("l2: " + l2);
//
//        System.out.println(l1.containsAll(l2));
//    }
}
