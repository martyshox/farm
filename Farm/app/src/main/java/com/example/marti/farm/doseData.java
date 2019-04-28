package com.example.marti.farm;

import java.io.Serializable;

public class doseData implements Serializable {

    String Eartag;
    String DoseName;
    String Amounts;
    String dates;
    String Period;

    public doseData(String Eartag, String DoseName,String Amounts, String dates,String Period)
    {
        this.Eartag = Eartag;
        this.DoseName = DoseName;
        this.Amounts = Amounts;
        this.dates = dates;
        this.Period = Period;
    }

    public String getEartag() {return Eartag;}

    public void setEartag(String Eartag) {this.Eartag = Eartag;}

    public String getDoseName() {return DoseName;}

    public void setDoseName(String DoseName) {this.DoseName = DoseName;}

    public String getAmounts() {return Amounts;}

    public void setAmounts(String Amounts) {this.Amounts = Amounts;}

    public String getDates() {return dates;}

    public void setDates(String dates) {this.dates = dates;}

    public String getPeriod() {return Period;}

    public void setPeriod(String Period) {this.Period = Period;}







}