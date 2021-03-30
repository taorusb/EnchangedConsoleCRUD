package com.taorusb.consolecrudenchanged.model;

public abstract class BaseModel {
    private Long id;

    protected BaseModel(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
