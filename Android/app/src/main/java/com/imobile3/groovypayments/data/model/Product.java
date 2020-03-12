/*
 *  Copyright (c) 2020 iMobile3, LLC. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, is permitted provided that adherence to the following
 *  conditions is maintained. If you do not agree with these terms,
 *  please do not use, install, modify or redistribute this software.
 *
 *  1. Redistributions of source code must retain the above copyright notice, this
 *  list of conditions and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 */

package com.imobile3.groovypayments.data.model;

import com.imobile3.groovypayments.data.entities.ProductEntity;
import com.imobile3.groovypayments.data.entities.ProductTaxJunctionEntity;
import com.imobile3.groovypayments.data.entities.TaxEntity;

import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

/**
 * @author Kevin Schanz
 */
public class Product extends ProductEntity {

    /**
     * List of the taxes associated with this product.
     */
    @Relation(
            parentColumn = "product_id",
            entityColumn = "tax_id",
            associateBy = @Junction(ProductTaxJunctionEntity.class))
    private List<TaxEntity> mTaxes;

    public Product() {
    }

    public List<TaxEntity> getTaxes() {
        return mTaxes;
    }

    public void setTaxes(List<TaxEntity> taxes) {
        mTaxes = taxes;
    }
}
