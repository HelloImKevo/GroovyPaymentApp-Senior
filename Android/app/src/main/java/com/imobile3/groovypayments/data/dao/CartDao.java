package com.imobile3.groovypayments.data.dao;

import com.imobile3.groovypayments.data.entities.CartEntity;
import com.imobile3.groovypayments.data.model.Cart;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CartDao {

    /**
     * @return List of populated cart models. Uses SQL join operations.
     */
    @Transaction
    @Query("SELECT * FROM cart")
    List<Cart> getCarts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCarts(CartEntity... values);

    @Update
    void updateCarts(CartEntity... values);

    @Delete
    void deleteCarts(CartEntity... values);
}
