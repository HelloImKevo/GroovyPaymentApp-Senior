package com.imobile3.groovypayments.data.dao;

import com.imobile3.groovypayments.data.entities.ProductTaxJunctionEntity;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductTaxJunctionDao {

    @Query("SELECT * FROM product_tax_junction")
    List<ProductTaxJunctionEntity> getProductTaxJunctions();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProductTaxJunctions(ProductTaxJunctionEntity... values);

    @Update
    void updateProductTaxJunctions(ProductTaxJunctionEntity... values);

    @Delete
    void deleteProductTaxJunctions(ProductTaxJunctionEntity... values);
}
