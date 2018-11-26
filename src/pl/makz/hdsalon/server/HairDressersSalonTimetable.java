package pl.makz.hdsalon.server;


import java.util.HashMap;
import java.util.Map;

public class HairDressersSalonTimetable {
    private String wolnyTermin = "Wolny Termin";
    public Map<Integer, String> timetable = new HashMap<>();

    public HairDressersSalonTimetable() {
        timetable.put(10, wolnyTermin);
        timetable.put(11, wolnyTermin);
        timetable.put(12, wolnyTermin);
        timetable.put(13, wolnyTermin);
        timetable.put(14, wolnyTermin);
        timetable.put(15, wolnyTermin);
        timetable.put(16, wolnyTermin);
        timetable.put(17, wolnyTermin);
        timetable.put(18, wolnyTermin);
    }

    public Object getKeyFromValue(String value) {
        for (Object o : timetable.keySet()) {
            if (timetable.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }

    public String printTimetable() {
        String terminarz = "";

        for (int i = 10; i < 19; i++) {
            if (timetable.get(i).equals(wolnyTermin)) {
                terminarz += i + ":00 " + timetable.get(i) + "\n";
            }
        }
        return terminarz;
    }

    public boolean bookTheDate(String hour, String name) {
        boolean flag;
        int key = Integer.parseInt(hour);
        if (timetable.get(key).equals(wolnyTermin)) {
            timetable.replace(key, name);
            flag = true;
        } else {
            System.out.println("zajęty termin");
            flag = false;
        }
        return flag;
    }

    public boolean cancelTheDate(String hour, String name) {
        boolean flag;
        int key = Integer.parseInt(hour);
        if (timetable.get(key).equalsIgnoreCase(name)) {
            timetable.replace(key, wolnyTermin);
            flag = true;
        } else {
            System.out.println("nie udało sie usunac");
            flag = false;
        }
        return flag;
    }
}
