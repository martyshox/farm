package com.example.marti.farmapp;

import java.io.Serializable;

public class heatData implements Serializable {

    String Eartag;
    String bullID;
    static String dates;


    public heatData(String Eartag, String BullID, String dates)
    {
        this.Eartag = Eartag;
        this.bullID = BullID;
        this.dates = dates;

    }

    public String getEartag() {return Eartag;}

    public void setEartag(String Eartag) {this.Eartag = Eartag;}

    public String getBullID() {return bullID;}

    public void setBullID(String BullID) {this.bullID = BullID;}

    public static String getHeatDates() {return dates;}

    public void setHeatDates(String dates) {this.dates = dates;}

}
