package com.example.zamanyonetimi.ui.Inbox;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InboxViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public InboxViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Acil-Önemli   Acil Değil-Önemli\nAcil-Önemsiz    Acil Değil-Önemsiz  ");
    }

    public LiveData<String> getText() {
        return mText;
    }
}