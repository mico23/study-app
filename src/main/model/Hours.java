package model;

import exceptions.NegativeValueError;
import org.json.JSONObject;
import persistence.Writable;

import java.time.LocalDate;

/**
 * represent the number of hours with the date of the recorded hours
 */
public class Hours implements Writable {
    private int hours;
    private LocalDate date;

    //EFFECTS: set the hours to hours and set the date to today's day in the system
    public Hours(int hours) throws NegativeValueError {
        if (hours < 0) {
            throw new NegativeValueError();
        }
        this.hours = hours;
        this.date = LocalDate.now();
    }

    //MODIFIES: this
    //EFFECTS: set the date to the input
    public void setDate(LocalDate date) {
        this.date = date;
    }

    //EFFECTS: return the hours of TimeTracker
    public int getHours() {
        return this.hours;
    }

    //EFFECTS: return the date of TimeTracker
    public LocalDate getDate() {
        return this.date;
    }

    /**
     * NOTE: some code related to JSON are from JsonSerializationDemo.
     */
    // EFFECT: return hours as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("hours", hours);
        json.put("date", date);
        return json;
    }
}
