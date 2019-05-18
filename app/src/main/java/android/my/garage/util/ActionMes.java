package android.my.garage.util;

public class ActionMes {
    int garageID;
    int num;
    String action;

    public ActionMes(int garageID, int num, String action) {
        this.garageID = garageID;
        this.num = num;
        this.action = action;
    }

    public int getGarageID() {
        return garageID;
    }

    public int getNum() {
        return num;
    }

    public String getAction() {
        return action;
    }
}
