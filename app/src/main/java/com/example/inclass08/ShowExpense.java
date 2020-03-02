package com.example.inclass08;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShowExpense extends AppCompatActivity {

    Expense expense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_expense);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Show Expense");

        final Button btnEdit = findViewById(R.id.btnEditID);
        final Button btnClose = findViewById(R.id.btnClose);
        final TextView tvExpenseName = findViewById(R.id.tvExpenseName);
        final TextView tvCat = findViewById(R.id.tvCategory);
        final TextView tvAmt = findViewById(R.id.tvAmount);
        final TextView tvExpDate = findViewById(R.id.tvExpenseDate);

        if(getIntent() != null && getIntent().getExtras() != null) {
            expense = (Expense) getIntent().getExtras().getSerializable("expense_data");
            tvExpenseName.setText(expense.getExpenseName());
            tvCat.setText(expense.getCategory());
            tvAmt.setText(expense.getAmount());
            tvExpDate.setText(expense.getExpenseDate());
        }

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("demo", "Edit clicked");

                Intent i = new Intent(ShowExpense.this, EditExpense.class);
                i.putExtra("edit_data", expense);
                startActivity(i);
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("demo", "Close clicked");

                if(getFragmentManager().getBackStackEntryCount() == 0) {
                    onBackPressed();
                }
                else {
                    getFragmentManager().popBackStack();
                }
            }
        });
    }
}
