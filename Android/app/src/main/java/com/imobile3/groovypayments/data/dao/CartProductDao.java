package com.imobile3.groovypayments.data.dao;

import com.imobile3.groovypayments.data.entities.CartProductEntity;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CartProductDao {

    @Query("SELECT * FROM cart_product")
    List<CartProductEntity> getCartProducts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCartProducts(CartProductEntity... values);

    @Update
    void updateCartProducts(CartProductEntity... values);

    @Delete
    void deleteCartProducts(CartProductEntity... values);
}
