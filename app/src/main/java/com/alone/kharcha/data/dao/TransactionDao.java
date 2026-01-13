package com.alone.kharcha.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.alone.kharcha.data.entity.TransactionEntity;

import java.util.List;

@Dao
public interface TransactionDao {

    // Insert
    @Insert
    void insert(TransactionEntity t);

    // All transactions (latest first)
    @Query("SELECT * FROM transactions ORDER BY timestamp DESC")
    List<TransactionEntity> getAllTransactions();

    // 🔥 Total Income / Expense (all time)
    @Query("SELECT IFNULL(SUM(amount),0) FROM transactions WHERE type = :type")
    double getTotalSum(String type);


//    // 🔹 Current Month Expense
//    @Query(" SELECT IFNULL(SUM(amount), 0) FROM transactions WHERE type = 'EXPENSE' AND date = :month ")
//    double getCurrentMonthExpense(String month);
    // 🔥 Monthly Income / Expense (timestamp based)
    @Query(
            "SELECT IFNULL(SUM(amount),0) " +
                    "FROM transactions " +
                    "WHERE type = :type " +
                    "AND timestamp BETWEEN :start AND :end"
    )
    double getMonthlySum(String type, long start, long end);

}


//package com.alone.kharcha.data.dao;
//
//        import com.alone.kharcha.data.entity.TransactionEntity;
//
//        import androidx.room.Dao;
//        import androidx.room.Insert;
//        import androidx.room.Query;
//
//        import java.util.List;
//
//@Dao
//public interface TransactionDao {
//
//    @Insert
//    void insert(TransactionEntity t);
//    long start = getMonthStart();
//    long end = getMonthEnd();
//
//
//
//    @Query("SELECT * FROM transactions ORDER BY id DESC")
//    List<TransactionEntity> getAllTransactions();
//
//    // 🔥 TOTAL Income / Expense (ALL TIME)
//    @Query("SELECT IFNULL(SUM(amount),0) FROM transactions WHERE type = :type")
//    double getTotalSum(String type);
//
//    // 🔹 Current Month Expense
//    @Query(" SELECT IFNULL(SUM(amount), 0) FROM transactions WHERE type = 'EXPENSE' AND date = :month ")
//    double getCurrentMonthExpense(String month);
//
//    // Monthly Income
//    @Query("SELECT IFNULL(SUM(amount),0) FROM transactions WHERE type='INCOME' AND date = :month")
//    double getMonthlyIncome(String month);
//
//    // Monthly Expense
//    @Query("SELECT IFNULL(SUM(amount),0) FROM transactions WHERE type='EXPENSE' AND date = :month")
//    double getMonthlyExpense(String month);
//
////    // Monthly Sum (Income / Expense)
////    @Query("SELECT IFNULL(SUM(amount),0) FROM transactions WHERE type = :type AND date LIKE :month|| '%'")
////    double getMonthlySum(String type, String month);
//
//    @Query("SELECT IFNULL(SUM(amount),0) FROM transactions WHERE type = :type AND timestamp BETWEEN :start AND :end)
//            double getMonthlySum(String type, long start, long end);
//
//
//
//
//    // Yearly Sum (Income / Expense)
//    @Query("SELECT IFNULL(SUM(amount),0) FROM transactions WHERE type = :type AND date LIKE :year || '%'")
//    double getYearlySum(String type, String year);
//
////    // Category Total
////    @Query("SELECT IFNULL(SUM(amount),0) FROM transactions WHERE catId")
////    double getCategoryTotal(int catId);
//}