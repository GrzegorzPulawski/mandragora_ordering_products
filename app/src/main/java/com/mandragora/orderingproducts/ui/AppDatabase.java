package com.mandragora.orderingproducts.ui;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.mandragora.orderingproducts.Product;


import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Product.class}, version = 2)
@TypeConverters({UnitConverter.class, DepartmentConverter.class}) // jeśli masz enum
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract ProductDAO productDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "product_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries() // na razie OK do testów
                    .build();
        }
        return INSTANCE;
    }
}
