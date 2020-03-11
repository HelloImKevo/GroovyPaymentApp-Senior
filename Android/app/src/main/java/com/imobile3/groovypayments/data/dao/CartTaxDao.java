package com.imobile3.groovypayments.data.dao;

import com.imobile3.groovypayments.data.entities.CartTaxEntity;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CartTaxDao {

    @Query("SELECT * FROM cart_tax")
    List<CartTaxEntity> getCartTaxes();

    @Insert // Default to OnConflictStrategy.ABORT
    void insertCartTaxes(CartTaxEntity... values);

    @Update
    void updateCartTaxes(CartTaxEntity... values);

    @Delete
    void deleteCartTaxes(CartTaxEntity... values);
}
