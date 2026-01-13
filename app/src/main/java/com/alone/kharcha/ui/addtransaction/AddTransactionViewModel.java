package com.alone.kharcha.ui.addtransaction;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alone.kharcha.data.database.AppDatabase;
import com.alone.kharcha.data.entity.TransactionEntity;
import com.alone.kharcha.repository.TransactionRepository;

import kotlinx.coroutines.CoroutineScope;

public class AddTransactionViewModel extends ViewModel {

    private final AppDatabase db;

    public AddTransactionViewModel() {
        // ⚠️ Context yahan nahi milega
        // isliye DB init yahan mat karo
        db = null;
    }

}
