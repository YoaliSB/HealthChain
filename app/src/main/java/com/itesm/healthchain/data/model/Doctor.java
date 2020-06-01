package com.itesm.healthchain.data.model;

public class Doctor {
    private String id;
    private String name;
    private String desc;
    private boolean active;

    public Doctor(String id, String name, String desc, boolean hasAccess) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.active = hasAccess;
    }

    public Doctor() {
        this.id = "1";
        this.name = "Kenyiro Tsuru";
        this.desc = "Pediatra";
        this.active = true;
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
        return active;
    }

    public void setIsActive(boolean active) {
        this.active = active;
    }
}
