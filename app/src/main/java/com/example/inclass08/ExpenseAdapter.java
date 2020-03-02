package com.example.inclass08;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ExpenseAdapter extends ArrayAdapter<Expense> {

    public ExpenseAdapter(@NonNull Context context, int resource, @NonNull List<Expense> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Expense expense = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView tvExpenseName = convertView.findViewById(R.id.txtItemId);
        TextView tvPrice = convertView.findViewById(R.id.textView3);

        tvExpenseName.setText(expense.getExpenseName());
        tvPrice.setText(expense.getAmount());

        return convertView;
    }

}
