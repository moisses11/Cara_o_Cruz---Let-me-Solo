package com.example.cara_o_cruz.ui.caraocruz;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CaraOCruzViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CaraOCruzViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}