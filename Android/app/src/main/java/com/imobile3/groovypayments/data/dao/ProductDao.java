package com.imobile3.groovypayments.data.dao;

import com.imobile3.groovypayments.data.entities.ProductEntity;
import com.imobile3.groovypayments.data.model.Product;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductDao {

    /**
     * @return List of populated product models. Uses SQL join operations.
     */
    @Transaction
    @Query("SELECT * FROM product")
    List<Product> getProducts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProducts(ProductEntity... values);

    @Update
    void updateProducts(ProductEntity... values);

    @Delete
    void deleteProducts(ProductEntity... values);
}
