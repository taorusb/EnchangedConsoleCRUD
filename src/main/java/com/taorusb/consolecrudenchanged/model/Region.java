package com.taorusb.consolecrudenchanged.model;


public class Region extends BaseModel {

    private String name;

    public Region() {
        super(0L);
    }

    public Region(Long id) {
        super(id);
    }

    public Region(String name) {
        super(0L);
        this.name = name;
    }

    public Region(Long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Region region = (Region) o;
        return super.getId() == region.getId();
    }

    @Override
    public String toString() {
        return "Region{" +
                "id=" + super.getId() +
                ", name='" + name + '\'' +
                '}';
    }
}
