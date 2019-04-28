package com.example.marti.farm;

import java.io.Serializable;

public class feedData implements Serializable {

    String Eartag;
    String FeedName;
    String FeedAmounts;
    String dates;


    public feedData(String Eartag, String FeedName,String FeedAmounts, String dates)
    {
        this.Eartag = Eartag;
        this.FeedName = FeedName;
        this.FeedAmounts = FeedAmounts;
        this.dates = dates;

    }

    public String getEartag() {return Eartag;}

    public void setEartag(String Eartag) {this.Eartag = Eartag;}

    public String getFeedName() {return FeedName;}

    public void setFeedName(String DoseName) {this.FeedName = FeedName;}

    public String getFeedAmounts() {return FeedAmounts;}

    public void setFeedAmounts(String FeedAmounts) {this.FeedAmounts = FeedAmounts;}

    public String getFeedDates() {return dates;}

    public void setFeedDates(String dates) {this.dates = dates;}

}
