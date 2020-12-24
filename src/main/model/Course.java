package model;

import exceptions.InvalidOutcomeError;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

/**
 * represent a course
 */
public class Course implements Writable {
    private String courseName;
    private List<Hours> effort;
    private int outcome;

    //EFFECTS: set the course name to courseName
    //         set effort to an empty list
    //         set the outcome to 0
    public Course(String name) {
        this.courseName = name;
        this.effort = new ArrayList<>();
        this.outcome = 0;
    }

    //REQUIRES: 0<= outcome <= 100
    //MODIFIES: this
    //EFFECTS: set this.outcome to outcome
    public void setOutcome(int outcome) throws InvalidOutcomeError {
        if (outcome < 0 || outcome > 100) {
            throw new InvalidOutcomeError();
        }
        this.outcome = outcome;
    }

    //REQUIRES: hours >= 0
    //MODIFIES: this
    //EFFECTS: add hours to this.effort
    public void addEffort(Hours hours) {
        this.effort.add(hours);
    }

    //EFFECTS: calculate the total effort of this course
    public int sumEffort() {
        int totalHours = 0;
        for (Hours i : this.effort) {
            totalHours += i.getHours();
        }
        return totalHours;
    }

    //EFFECTS: return the name of the course
    public String getCourseName() {
        return this.courseName;
    }

    //EFFECTS: return the outcome of the course
    public int getOutcome() {
        return this.outcome;
    }

    //EFFECTS: return the list of hours spent in this course
    public List<Hours> getEffort() {
        return this.effort;
    }

    /**
     * NOTE: some code related to JSON are from JsonSerializationDemo.
     */
    // EFFECT: return a course as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("courseName", courseName);
        json.put("effort", effortToJson());
        json.put("outcome", outcome);
        return json;
    }

    // EFFECT: return courses in the account as a JSON array
    private JSONArray effortToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Hours h : this.effort) {
            jsonArray.put(h.toJson());
        }

        return jsonArray;
    }
}
