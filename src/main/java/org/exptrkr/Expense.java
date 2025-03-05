package org.exptrkr;

import java.util.Date;

public class Expense {
    private long ExpID = System.currentTimeMillis();
    private String ExpName;
    private double ExpAmount;
    private long CatID;
    private String CatName;
    private Date ExpDate;
    private String ExpRemark;

    public Expense(){}
    public Expense(double amount, String name, String date) {
    }

    public String getExpRemark() {
        return ExpRemark;
    }

    public void setExpRemark(String expRemark) {
        ExpRemark = expRemark;
    }

    public Date getExpDate(Date date) {
        return ExpDate;
    }

    public void setExpDate(Date expDate) {
        ExpDate = expDate;
    }

    public String getCatName() {
        return CatName;
    }

    public void setCatName(String catName) {
        CatName = catName;
    }

    public long getCatID() {
        return CatID;
    }

    public void setCatID(long catID) {
        CatID = catID;
    }

    public double getExpAmount() {
        return ExpAmount;
    }

    public void setExpAmount(double expAmount) {
        ExpAmount = expAmount;
    }

    public String getExpName() {
        return ExpName;
    }

    public void setExpName(String expName) {
        ExpName = expName;
    }

    public long getExpID() {
        return ExpID;
    }

    public void setExpID(long expID) {
        ExpID = expID;
    }

    public java.sql.Date getDate() {
        return (java.sql.Date) ExpDate;
    }
}
