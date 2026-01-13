package com.alone.kharcha.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.alone.kharcha.R;
import com.alone.kharcha.data.database.AppDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
//import com.alone.kharcha.databinding.FragmentHomeBinding;



public class DashboardFragment extends Fragment {

    TextView tvIncome, tvExpense, tvProfitLoss, tvCurrentMonthExpense;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        tvIncome = view.findViewById(R.id.tvIncome);
        tvExpense = view.findViewById(R.id.tvExpense);
        tvProfitLoss = view.findViewById(R.id.tvProfitLoss);
        tvCurrentMonthExpense = view.findViewById(R.id.tvCurrentMonthExpense);
        loadDashboardData();

        return view;
    }

    private void loadDashboardData() {

        String month = new SimpleDateFormat("yyyy-MM", Locale.getDefault())
                .format(new Date());

        AppDatabase db = AppDatabase.getInstance(getContext());

//        double income = db.transactionDao().getMonthlyIncome(month);
//        double expense = db.transactionDao().getMonthlyExpense(month);

        long start = getMonthStart();
        long end = getMonthEnd();
        double income = db.transactionDao()
                .getMonthlySum("INCOME", start, end);

        double expense = db.transactionDao()
                .getMonthlySum("EXPENSE", start, end);
        double monthExpense = expense;

        double profitLoss = income - expense;

        tvCurrentMonthExpense.setText("₹ " + monthExpense);
        tvIncome.setText("Income: ₹" + income);
        tvExpense.setText("Expense: ₹" + expense);
        tvCurrentMonthExpense.setText("This Month expense: ₹" + monthExpense);

        if (profitLoss >= 0) {
            tvProfitLoss.setText("Profit: ₹" + profitLoss);
            tvProfitLoss.setTextColor(
                    getResources().getColor(android.R.color.holo_green_dark)
            );
        } else {
            tvProfitLoss.setText("Loss: ₹" + Math.abs(profitLoss));
            tvProfitLoss.setTextColor(
                    getResources().getColor(android.R.color.holo_red_dark)
            );
        }





        // UI update here
        tvIncome.setText("+ ₹" + income);
        tvExpense.setText("- ₹" + expense);
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
//public class DashboardFragment extends Fragment {
//
//    private FragmentHomeBinding binding;
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        DashboardViewModel dashboardViewModel =
//                new ViewModelProvider(this).get(DashboardViewModel.class);
//
//        binding = FragmentHomeBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
//
//        final TextView textView = binding.textHome;
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
//        return root;
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }
//}