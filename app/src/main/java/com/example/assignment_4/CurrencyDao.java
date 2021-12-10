package com.example.assignment_4;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CurrencyDao {

    @Insert
    void insert(Currency currency);

    @Delete
    void delete(Currency currency);

    @Query("Select * from Currency")
    List<Currency> getAllCurrency();


}
