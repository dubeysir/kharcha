package com.alone.kharcha.ui.dashboard;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alone.kharcha.repository.TransactionRepository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DashboardViewModel extends AndroidViewModel {

    private final TransactionRepository repository;

    private final MutableLiveData<Double> totalIncome = new MutableLiveData<>();
    private final MutableLiveData<Double> totalExpense = new MutableLiveData<>();
    private final MutableLiveData<Double> currentMonthExpense = new MutableLiveData<>();

    public DashboardViewModel(@NonNull Application application) {
        super(application);
        repository = new TransactionRepository(application);
        loadDashboardData();
    }

    private void loadDashboardData() {
        totalIncome.setValue(repository.getTotalIncome());
        totalExpense.setValue(repository.getTotalExpense());

        long start = getMonthStart();
        long end = getMonthEnd();

        double expense = repository.getCurrentMonthExpense(start, end);
        double income  = repository.getCurrentMonthIncome(start, end);


//        String currentMonth =
//                new SimpleDateFormat("yyyy-MM", Locale.getDefault())
//                        .format(new Date());
//
//        currentMonthExpense.setValue(
//                repository.getCurrentMonthExpense(currentMonth)
//        );

        // 🔥 THIS is the only line needed
        currentMonthExpense.setValue(expense);
    }

    private long getMonthStart() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    private long getMonthEnd() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH,
                cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTimeInMillis();
    }


    public LiveData<Double> getTotalIncome() {
        return totalIncome;
    }

    public LiveData<Double> getTotalExpense() {
        return totalExpense;
    }

    public LiveData<Double> getCurrentMonthExpense() {
        return currentMonthExpense;
    }
}
