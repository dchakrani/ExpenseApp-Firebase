package com.example.inclass08;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class EditExpense extends AppCompatActivity {

    private Button buttonCancel;
    private Spinner spinner;
    private EditText expenseName;
    private EditText expenseAmount;
    private Button buttonSave;

    Expense e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Edit Expense");
        spinner = findViewById(R.id.spinnerEditID);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(EditExpense.this,
                R.array.categories_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        expenseName = findViewById(R.id.etExpNameID);
        expenseAmount = findViewById(R.id.etAmtID);
        buttonSave = findViewById(R.id.btnSave);
        buttonCancel = findViewById(R.id.btnCancel);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("expenses");

        if(getIntent() != null && getIntent().getExtras() != null){
            e = (Expense) getIntent().getExtras().getSerializable("edit_data");
            expenseName.setText(e.getExpenseName());
            expenseAmount.setText(e.getAmount());
            int myAdapPos = adapter.getPosition(e.getCategory());
            spinner.setSelection(myAdapPos);
        }

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("demo", "Cancel clicked");

                if(getFragmentManager().getBackStackEntryCount() == 0) {
                    onBackPressed();
                }
                else {
                    getFragmentManager().popBackStack();
                }
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.d("demo", "Save clicked");
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");

                HashMap<String, Object> hm = new HashMap<>();

                if(expenseName.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(v.getContext(), "Please Enter Expense", Toast.LENGTH_SHORT).show();
                }else if(expenseAmount.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(v.getContext(), "Please Enter Expense Amount", Toast.LENGTH_SHORT).show();
                }else if(spinner.getSelectedItem().toString().equalsIgnoreCase("Select Category")){
                    Toast.makeText(v.getContext(), "Please Select Expense Category", Toast.LENGTH_SHORT).show();
                }else {
                    hm.put("id", e.getId());
                    hm.put("amount", expenseAmount.getText().toString());
                    hm.put("category", spinner.getSelectedItem().toString());
                    hm.put("expenseName", expenseName.getText().toString());
                    hm.put("expenseDate", sdf.format(cal.getTime()));
                myRef.child(e.getId()).updateChildren(hm);

                Intent in = new Intent(EditExpense.this, MainActivity.class);
                startActivity(in);
                }
            }
        });
    }
}
