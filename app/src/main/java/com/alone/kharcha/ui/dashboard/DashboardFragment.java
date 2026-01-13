package com.alone.kharcha.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.alone.kharcha.data.entity.TransactionEntity;

import com.alone.kharcha.R;
import com.alone.kharcha.data.database.AppDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;



public class DashboardFragment extends Fragment {

    TextView tvIncome, tvExpense,tvBalance,tvCurrentMonthExpense,tvLastTransactions;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        tvIncome = view.findViewById(R.id.tvIncome);
        tvExpense = view.findViewById(R.id.tvExpense);
        tvBalance=view.findViewById(R.id.tvBalance);
        tvCurrentMonthExpense=view.findViewById(R.id.tvCurrentMonthExpense);
        tvLastTransactions = view.findViewById(R.id.tvLastTransactions);

        loadDashboardData();
        return view;
    }

    private void loadDashboardData() {

        AppDatabase db = AppDatabase.getInstance(getContext());

        long start = getMonthStart();
        long end = getMonthEnd();
        List<TransactionEntity> last3 =
                db.transactionDao().getLast3Transactions();
        double income = db.transactionDao()
                .getMonthlySum("INCOME", start, end);

        double expense = db.transactionDao()
                .getMonthlySum("EXPENSE", start, end);

        double balance = income - expense;

        tvIncome.setText("+ ₹" + income);
        tvExpense.setText("- ₹" + expense);
        tvCurrentMonthExpense.setText("₹ " + expense);
        tvBalance.setText("₹ " + balance);

        if (last3.isEmpty()) {
            tvLastTransactions.setText("No transactions");
        } else {
            StringBuilder sb = new StringBuilder();

            for (TransactionEntity t : last3) {
                sb.append(t.getTitle())
                        .append("  ");

                if ("INCOME".equals(t.getType())) {
                    sb.append("+ ₹");
                } else {
                    sb.append("- ₹");
                }

                sb.append(t.getAmount())
                        .append("\n");
            }

            tvLastTransactions.setText(sb.toString());
        }

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
        return cal.getTimeInMillis();
    }
}
