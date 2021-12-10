package com.example.assignment_4;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Currency.class},version = 1)
public abstract class CurrencyDataBase extends RoomDatabase {
    public abstract CurrencyDao getCurrencyDao();
}
