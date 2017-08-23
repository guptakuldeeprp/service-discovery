package com.zycus.service;

import java.util.*;

public class ServiceMetadata {

    private String description = "";

    private List<String> tenants = new ArrayList<>();
    private List<String> tags = new ArrayList<>();
    private Map<String, Object> labels = new HashMap<>();

    public ServiceMetadata() {

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getTenants() {
        return tenants;
    }

    public void addTenants(List<String> tenants) {
        if (tenants != null)
            this.tenants.addAll(tenants);
    }

    public void addTenants(String... tenants) {
        if (tenants != null)
            this.tenants.addAll(Arrays.asList(tenants));
    }

    public void addTags(List<String> tags) {
        if (tags != null)
            this.tags.addAll(tags);
    }

    public void addTags(String... tenants) {
        if (tags != null)
            this.tags.addAll(Arrays.asList(tenants));
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Map<String, Object> getLabels() {
        return labels;
    }

    public void addLabel(String key, Object value) {
        labels.put(key, value);
    }

    public void setLabels(Map<String, Object> labels) {
        this.labels = labels;
    }

    @Override
    public String toString() {
        return "ServiceMetadata{" +
                "description='" + description + '\'' +
                ", tenants=" + tenants +
                ", tags=" + tags +
                ", labels=" + labels +
                '}';
    }
}
