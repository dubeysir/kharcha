package com.alone.kharcha.ui.addtransaction;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.alone.kharcha.R;
import com.alone.kharcha.data.database.AppDatabase;
//import com.alone.kharcha.data.entity.Category;
import com.alone.kharcha.data.entity.TransactionEntity;
import com.alone.kharcha.repository.TransactionRepository;
//import com.alone.kharcha.ui.categories.CategoryViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class AddTransactionFragment extends Fragment {

    EditText etTitle, etQuantity, etPrice, etAmount;
    Spinner spCategory;
    RadioGroup rgType;
    Button btnSave;

    private AddTransactionViewModel transactionViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_transaction, container, false);


        TextWatcher calculatorWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handleAmountLogic();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };



        transactionViewModel =
                new ViewModelProvider(this).get(AddTransactionViewModel.class);

        etTitle = view.findViewById(R.id.etTitle);
        etQuantity = view.findViewById(R.id.etQuantity);
        etPrice = view.findViewById(R.id.etPrice);
        etAmount = view.findViewById(R.id.etAmount);


        // ⚠️ ADD watcher AFTER this
        etQuantity.addTextChangedListener(calculatorWatcher);
        etPrice.addTextChangedListener(calculatorWatcher);

        rgType = view.findViewById(R.id.rgType);
        btnSave = view.findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> saveTransaction());

        return view;
    }

    private void saveTransaction() {

        String title = etTitle.getText().toString().trim();
        if (title.isEmpty()) {
            etTitle.setError("Required");
            return;
        }

        String qtyStr = etQuantity.getText().toString();
        String priceStr = etPrice.getText().toString();

        Integer qty = null;
        Double price = null;
       double amount;

        if (!qtyStr.isEmpty() && !priceStr.isEmpty()) {
            qty = Integer.parseInt(qtyStr);
            price = Double.parseDouble(priceStr);
            amount = qty * price;
        } else {
            amount = Double.parseDouble(etAmount.getText().toString());
        }

        if (etAmount.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(), "Please enter amount", Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedId = rgType.getCheckedRadioButtonId();
        String type = (selectedId == R.id.rbIncome) ? "INCOME" : "EXPENSE";

        TransactionEntity t = new TransactionEntity();
        t.title = title;
        t.amount = amount;
        t.type = type;
        t.quantity = qty;
        t.pricePerUnit = price;
        t.timestamp = System.currentTimeMillis();

        AppDatabase db = AppDatabase.getInstance(getContext());

        db.transactionDao().insert(t);
        Toast.makeText(getContext(), "Transaction Saved ✅", Toast.LENGTH_SHORT).show();
        clearFields();
    }


    private void clearFields() {
        etTitle.setText("");
        etQuantity.setText("");
        etPrice.setText("");
        etAmount.setText("");
        rgType.clearCheck();
    }

    private void handleAmountLogic() {
        String qtyStr = etQuantity.getText().toString().trim();
        String priceStr = etPrice.getText().toString().trim();

        // CASE 1: Both present → auto calculate
        if (!qtyStr.isEmpty() && !priceStr.isEmpty()) {
            try {
                int qty = Integer.parseInt(qtyStr);
                double price = Double.parseDouble(priceStr);

                double total = qty * price;
                etAmount.setText(String.format(Locale.getDefault(), "%.2f", total));

                etAmount.setEnabled(false); // 🔒 lock manual input
            } catch (NumberFormatException e) {
                etAmount.setEnabled(true);
            }
        }
        // CASE 2: Any missing → manual entry allowed
        else {
            etAmount.setEnabled(true);
        }
    }

}
