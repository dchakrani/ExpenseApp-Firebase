package com.example.inclass08;

import java.io.Serializable;

public class Expense implements Serializable {

    private String id;
    private String expenseName, category, amount, expenseDate;

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", expenseName='" + expenseName + '\'' +
                ", category='" + category + '\'' +
                ", amount='" + amount + '\'' +
                ", expenseDate='" + expenseDate + '\'' +
                '}';
    }

    public Expense(){}

    public Expense(String id, String expenseName, String category, String amount, String expenseDate) {
        this.expenseName = expenseName;
        this.category = category;
        this.amount = amount;
        this.expenseDate = expenseDate;
        this.id = id;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(String expenseDate) {
        this.expenseDate = expenseDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
