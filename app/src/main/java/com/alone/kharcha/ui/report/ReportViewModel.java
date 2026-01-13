
package com.alone.kharcha.ui.report;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alone.kharcha.data.entity.TransactionEntity;
import com.alone.kharcha.repository.TransactionRepository;

import java.util.List;

public class ReportViewModel extends AndroidViewModel {

    private final MutableLiveData<String> mText = new MutableLiveData<>();
    private final TransactionRepository repository;

    public MutableLiveData<List<TransactionEntity>> transactions =
            new MutableLiveData<>();

    // ✅ ONLY constructor allowed
    public ReportViewModel(@NonNull Application application) {
        super(application);

        mText.setValue("This is slideshow fragment");

        repository = new TransactionRepository(application);
        loadTransactions();
    }

    public LiveData<String> getText() {
        return mText;
    }

    private void loadTransactions() {
        transactions.setValue(repository.getAllTransactions());
    }
}

