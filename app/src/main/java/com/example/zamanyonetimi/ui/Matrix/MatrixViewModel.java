package com.example.zamanyonetimi.ui.Matrix;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MatrixViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MatrixViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Acil-Önemli   Acil Değil-Önemli\nAcil-Önemsiz    Acil Değil-Önemsiz");
    }

    public LiveData<String> getText() {
        return mText;
    }
}