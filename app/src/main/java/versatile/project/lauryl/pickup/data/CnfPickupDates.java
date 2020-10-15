package versatile.project.lauryl.pickup.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CnfPickupDates implements Serializable {
    private String date;
    private static int lastContactId = 0;
    public CnfPickupDates(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public static List<CnfPickupDates> createCnfPickupDatesList(int numContacts, int offset) {
        List<CnfPickupDates> contacts = new ArrayList<CnfPickupDates>();
        for (int i = 1; i <= numContacts; i++) {
            contacts.add(new CnfPickupDates(""));
        }

        return contacts;
    }
}
