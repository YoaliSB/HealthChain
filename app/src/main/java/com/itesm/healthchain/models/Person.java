package com.itesm.healthchain.models;

public class Person {
    private String name;
    private String desc;
    private boolean activated;

    public Person(String name, String desc, boolean hasAccess) {
        this.name = name;
        this.desc = desc;
        this.activated = hasAccess;
    }

    public Person() {
        this.name = "Kenyiro Tsuru";
        this.desc = "Pediatra";
        this.activated = true;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public boolean isActivated() {
        return activated;
    }
}
