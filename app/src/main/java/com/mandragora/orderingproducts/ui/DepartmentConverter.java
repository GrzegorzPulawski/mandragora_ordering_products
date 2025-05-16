package com.mandragora.orderingproducts.ui;

import androidx.room.TypeConverter;

public class DepartmentConverter {
    @TypeConverter
    public static Department toDepartment(String value) {
        return value == null ? Department.BAR : Department.valueOf(value);
    }

    @TypeConverter
    public static String fromDepartment(Department department) {
        return department == null ? Department.BAR.name() : department.name();
    }
}
