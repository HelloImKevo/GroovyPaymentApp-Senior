package com.imobile3.groovypayments.data.dao;

import com.imobile3.groovypayments.data.entities.TaxEntity;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaxDao {

    @Query("SELECT * FROM tax")
    List<TaxEntity> getTaxes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTaxes(TaxEntity... values);

    @Update
    void updateTaxes(TaxEntity... values);

    @Delete
    void deleteTaxes(TaxEntity... values);
}
