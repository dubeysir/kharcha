package com.alone.kharcha.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.alone.kharcha.data.entity.TransactionEntity;

import java.util.List;

@Dao
public interface TransactionDao {

    @Insert
    void insert(TransactionEntity t);

    // All transactions (latest first)
    @Query("SELECT * FROM transactions ORDER BY timestamp DESC")
    List<TransactionEntity> getAllTransactions();

    // 🔥 Total Income / Expense (all time)
    @Query("SELECT IFNULL(SUM(amount),0) FROM transactions WHERE type = :type")
    double getTotalSum(String type);

    //Monthly sum
    @Query(
            "SELECT IFNULL(SUM(amount),0) " +
                    "FROM transactions " +
                    "WHERE type = :type " +
                    "AND timestamp BETWEEN :start AND :end"
    )
    double getMonthlySum(String type, long start, long end);

   //recent 3 transections
    @Query("SELECT * FROM transactions ORDER BY timestamp DESC LIMIT 3")
    List<TransactionEntity> getLast3Transactions();



}
