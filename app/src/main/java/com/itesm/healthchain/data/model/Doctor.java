package com.itesm.healthchain.data.model;

public class Doctor {
    private String id;
    private String name;
    private String desc;
    private int active;

    public Doctor(String id, String name, String desc, int hasAccess) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.active = hasAccess;
    }

    public Doctor(String id, String name, String desc, boolean hasAccess) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.active = hasAccess ? 1 : 0;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public boolean isActive() {
        if (active == 1) {
            return true;
        } else {
            return false;
        }
    }

    public void setIsActive(boolean active) {
        this.active = active ? 1 : 0;
    }
}
