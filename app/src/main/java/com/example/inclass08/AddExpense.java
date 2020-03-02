package com.example.inclass08;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

public class AddExpense extends AppCompatActivity {

    private Button button_Cancel;
    private Button button_Add;
    private Spinner spinner;
    static String EXPENSE_LIST = "EXPENSE_LIST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Add Expense");

        button_Add = findViewById(R.id.btnAddExpense);
        button_Cancel = findViewById(R.id.btnCancel);
        spinner = findViewById(R.id.spinnerCategory);
        final Expense expense_Data = new Expense();
        final ArrayList<Expense> expenseArrayList= new ArrayList();
        final String id = UUID.randomUUID().toString();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("expenses");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot expenseSnap: dataSnapshot.getChildren()){
                    Expense expense = expenseSnap.getValue(Expense.class);
                    expenseArrayList.add(expense);
                    Log.d("demo", "MY DATA: " + expense.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("demo", "Failed to read value.", databaseError.toException());
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(AddExpense.this,
                R.array.categories_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        button_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText expenseName = findViewById(R.id.editTextAddExpense);
                EditText expenseAmount = findViewById(R.id.editText_Amount);
                Spinner category = findViewById(R.id.spinnerCategory);

                if(expenseName.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(v.getContext(), "Please Enter Expense", Toast.LENGTH_SHORT).show();
                }else if(expenseAmount.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(v.getContext(), "Please Enter Expense Amount", Toast.LENGTH_SHORT).show();
                }else if(category.getSelectedItem().toString().equalsIgnoreCase("Select Category")){
                    Toast.makeText(v.getContext(), "Please Select Expense Category", Toast.LENGTH_SHORT).show();
                }else {

                    expense_Data.setId(id);
                    expense_Data.setExpenseName(expenseName.getText().toString());
                    expense_Data.setAmount(expenseAmount.getText().toString());

                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");

                    expense_Data.setExpenseDate(sdf.format(cal.getTime()));
                    expense_Data.setCategory(category.getSelectedItem().toString());

                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("expenses/" + id);

                    myRef.setValue(expense_Data);

                    Intent in = new Intent(AddExpense.this, MainActivity.class);
                    in.putExtra(EXPENSE_LIST, expense_Data);
                    startActivity(in);
                }
            }
        });

        button_Cancel.setOnClickListener(new View.OnClickListener() {
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
    }
}
