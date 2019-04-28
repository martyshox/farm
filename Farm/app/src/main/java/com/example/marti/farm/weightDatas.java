package com.example.marti.farm;

import java.io.Serializable;

public class weightDatas implements Serializable {

    String Eartag ;
    String Weight;
    String dates;


    public weightDatas(String Eartag, String Weight, String dates)
    {
        this.Eartag = Eartag;
        this.Weight = Weight;
        this.dates = dates;

    }

    public String getEartag() {return Eartag;}

    public void setEartag(String Eartag) {this.Eartag = Eartag;}

    public String getWeight() {return Weight;}

    public void setWeight(String Weight) {this.Weight = Weight;}

    public String getWeightDates() {return dates;}

    public void setWeightDates(String dates) {this.dates = dates;}

}
