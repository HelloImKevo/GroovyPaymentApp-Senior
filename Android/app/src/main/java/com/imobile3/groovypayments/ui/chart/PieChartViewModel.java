package com.imobile3.groovypayments.ui.chart;

import com.imobile3.groovypayments.data.CartRepository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class PieChartViewModel extends ViewModel {

    private CartRepository mCartRepository;

    PieChartViewModel(CartRepository repository) {
        mCartRepository = repository;
    }

    LiveData<List<String>> getProductList() {
        final MutableLiveData<List<String>> observable =
                new MutableLiveData<>(new ArrayList<>());

        return observable;
    }
}
