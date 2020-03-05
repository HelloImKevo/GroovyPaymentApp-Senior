package com.imobile3.groovypayments.ui.orderentry;

import com.imobile3.groovypayments.data.ProductDataSource;
import com.imobile3.groovypayments.data.ProductRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class OrderEntryViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(OrderEntryViewModel.class)) {
            return (T)new OrderEntryViewModel(
                    ProductRepository.getInstance(new ProductDataSource()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
