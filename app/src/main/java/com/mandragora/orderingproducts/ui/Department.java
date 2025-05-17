package com.mandragora.orderingproducts.ui;

public enum Department {
    All("WSZYSTKIE DZIAŁY"),
    ALKOHOL("Alkohol"),
    BAR("BAR"),
    PEPSI("PEPSI"),
    MIESO("MIĘSA"),
    NABIAL("NABIAŁ"),
    OWOCE("OWOCE"),
    WARZYWA("WARZYWA"),
    MROZONKI("MROŻONKI"),
    RYBY("RYBY"),
    SYPKIE("SYPKIE"),
    PIECZYWO("PIECZYWO"),
    OPAKOWANIA("OPAKOWANIA");

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
