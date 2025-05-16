package com.mandragora.orderingproducts.ui;

import androidx.room.TypeConverter;

public class UnitConverter {
    @TypeConverter
    public static Unit toUnit(String value) {
        return value == null ? Unit.SZTUKI : Unit.valueOf(value);
    }

    @TypeConverter
    public static String fromUnit(Unit unit) {
        return unit == null ? Unit.SZTUKI.name() : unit.name();
    }

}
