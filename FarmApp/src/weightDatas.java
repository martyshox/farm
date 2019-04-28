import java.io.Serializable;

public class weightDatas implements Serializable {

    private String Eartag;
    private String Weight;
    private String dates;


    public weightDatas(String Eartag, String Weight, String dates)
    {
        this.setEartag(Eartag);
        this.setWeight(Weight);
        this.setDates(dates);

    }

    public String getEartag() {return Eartag;}

    public void setEartag(String Eartag) {this.Eartag = Eartag;}

    public String getWeight() {return Weight;}

    public void setWeight(String Weight) {this.Weight = Weight;}

    public String getWeightDates() {return getDates();}

    public void setWeightDates(String dates) {
        this.setDates(dates);}

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }
}
