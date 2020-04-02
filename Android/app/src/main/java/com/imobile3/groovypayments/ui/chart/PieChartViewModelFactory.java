package com.imobile3.groovypayments.ui.chart;

import com.imobile3.groovypayments.data.CartDataSource;
import com.imobile3.groovypayments.data.CartRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class PieChartViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PieChartViewModel.class)) {
            return (T)new PieChartViewModel(
                    CartRepository.getInstance(new CartDataSource()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
