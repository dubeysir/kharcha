package com.alone.kharcha.ui.report;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alone.kharcha.R;
import com.alone.kharcha.data.database.AppDatabase;
import com.alone.kharcha.data.entity.TransactionEntity;
//import com.alone.kharcha.databinding.FragmentSlideshowBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReportFragment extends Fragment {


    RecyclerView rvTransactions;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_report, container, false);

        rvTransactions = view.findViewById(R.id.rvTransactions);
        rvTransactions.setLayoutManager(new LinearLayoutManager(getContext()));
        loadAllTransactions();
        return view;
    }



    private void loadAllTransactions() {

        AppDatabase db = AppDatabase.getInstance(getContext());

        List<TransactionEntity> list =
                db.transactionDao().getAllTransactions();

        TransactionAdapter adapter =
                new TransactionAdapter(list);

        rvTransactions.setAdapter(adapter);
    }



}
