package typeqast.entities;

import java.util.ArrayList;
import java.util.List;

public class Meter {

    private List<Reading> readings;

    public List<Reading> getReadings() {
        return this.readings;
    }

    public void addReading(Reading reading) {
        if (this.readings == null) {
            this.readings = new ArrayList<>();
        }
        this.readings.add(reading);

    }

}
