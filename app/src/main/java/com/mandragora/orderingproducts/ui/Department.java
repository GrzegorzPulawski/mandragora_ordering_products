package com.mandragora.orderingproducts.ui;

public enum Department {
    All("WSZYSTKIE DZIAŁY"),
    BAR("BAR"),
    PEPSI("PEPSI"),
    MIESO("MIĘSA"),
    NABIAL("NABIAŁ"),
    OWOCE("OWOCE"),
    WARZYWA("WARZYWA"),
    MROZONKI("MROŻONKI"),
    RYBY("RYBY"),
    SYPKIE("SYPKIE");

    private final String displayEnum;

    Department(String displayEnum) {
        this.displayEnum = displayEnum;
    }

    public String getDisplayEnum() {
        return displayEnum;
    }

    @Override
    public String toString() {
        return "dział: " + displayEnum ;
    }
}
