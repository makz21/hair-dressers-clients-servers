package pl.makz.hdsalon.server;


import java.util.HashMap;
import java.util.Map;

public class HairDressersSalonTimetable {
    public Map<Integer, String> timetable = new HashMap<>();

    public HairDressersSalonTimetable() {
        timetable.put(10, "Wolny Termin");
        timetable.put(11, "Wolny Termin");
        timetable.put(12, "Wolny Termin");
        timetable.put(13, "Wolny Termin");
        timetable.put(14, "Wolny Termin");
        timetable.put(15, "Wolny Termin");
        timetable.put(16, "Wolny Termin");
        timetable.put(17, "Wolny Termin");
        timetable.put(18, "Wolny Termin");
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
            if (timetable.get(i) == "Wolny Termin") {
                terminarz += i + ":00 " + timetable.get(i) + "\n";
            }
        }
        return terminarz;
    }

    public void bookTheDate(String hour, String name) {
        int key = Integer.parseInt(hour);
        if (timetable.get(key) == "Wolny Termin") {
            timetable.replace(key, name);
        } else {
            System.out.println("zajęty termin");
        }

    }
}
