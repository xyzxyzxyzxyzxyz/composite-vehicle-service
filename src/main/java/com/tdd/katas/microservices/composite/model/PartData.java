package com.tdd.katas.microservices.composite.model;

public class PartData {
    private String partId;
    private String description;

    public PartData(String partId, String description) {
        this.partId = partId;
        this.description = description;
    }

    public String getPartId() {
        return partId;
    }

    public void setPartId(String partId) {
        this.partId = partId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
