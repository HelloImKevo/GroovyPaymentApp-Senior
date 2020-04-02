package com.imobile3.groovypayments.ui.chart;

import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
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

    // Dummy data
    LiveData<List<DataEntry>> getProductList() {
        final MutableLiveData<List<DataEntry>> observable =
                new MutableLiveData<>(new ArrayList<>());
        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Cappuccino", 312));
        data.add(new ValueDataEntry("Latte", 522));
        data.add(new ValueDataEntry("Hot Chocolate", 701));
        data.add(new ValueDataEntry("Mocha", 421));
        data.add(new ValueDataEntry("Tea", 200));
        observable.setValue(data);

        return observable;
    }
}
