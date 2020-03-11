package com.imobile3.groovypayments.ui.orderhistory;

import com.imobile3.groovypayments.data.CartDataSource;
import com.imobile3.groovypayments.data.CartRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class OrderHistoryViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(OrderHistoryViewModel.class)) {
            return (T)new OrderHistoryViewModel(
                    CartRepository.getInstance(new CartDataSource()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
