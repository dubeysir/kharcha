package com.alone.kharcha.data.entity;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(
        tableName = "transactions"

)
public class TransactionEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;
    public String type;       // INCOME / EXPENSE

    public double amount;

    public Integer quantity;      // nullable
    public Double pricePerUnit;   // nullable


    public  long timestamp; //for date with time yyyy-mm-dd hh:mm

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}

