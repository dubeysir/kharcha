package com.alone.kharcha.repository;

import android.content.Context;

import com.alone.kharcha.data.database.AppDatabase;
import com.alone.kharcha.data.entity.TransactionEntity;

import java.util.List;

public class TransactionRepository {

    private final AppDatabase db;

    public TransactionRepository(Context context) {
        db = AppDatabase.getInstance(context);
    }


    public List<TransactionEntity> getAllTransactions() {
        return db.transactionDao().getAllTransactions();
    }


    // 🔹 Total Income (all time)
    public double getTotalIncome() {
        return db.transactionDao().getTotalSum("INCOME");
    }

    // 🔹 Total Expense (all time)
    public double getTotalExpense() {
        return db.transactionDao().getTotalSum("EXPENSE");
    }
    public void insert(TransactionEntity t) {
        db.transactionDao().insert(t);
    }

    public double getMonthlySum(String type, long start,long end) {
        return db.transactionDao().getMonthlySum(type, start,end);
    }
    public double getCurrentMonthExpense(long start, long end) {
        return db.transactionDao().getMonthlySum("EXPENSE", start, end);
    }

    public double getCurrentMonthIncome(long start, long end) {
        return db.transactionDao().getMonthlySum("INCOME", start, end);
    }



//    public double getCurrentMonthExpense(String month) {
//        return db.transactionDao().getCurrentMonthExpense(month);
//    }
}

