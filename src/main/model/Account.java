package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * represent an account
 */
public class Account implements Writable {
    private String userName;
    private List<Course> listOfCourse;


    //EFFECTS: set the user name to userName
    //         set the list of courses to an empty list
    public Account(String userName) {
        this.userName = userName;
        this.listOfCourse = new ArrayList<>();
    }

    //EFFECTS: calculate the average outcome of all courses on the course list
    //         return 0 if there is not course on the list
    public int calculateAverage() {
        int totalOutcome = 0;
        if (this.listOfCourse.size() != 0) {
            for (Course i : this.listOfCourse) {
                totalOutcome += i.getOutcome();
            }
            return totalOutcome / (this.listOfCourse.size());
        }
        return totalOutcome;
    }

    //MODIFIES: this
    //EFFECTS: add a course to the course list
    public void addCourse(Course course) {
        this.listOfCourse.add(course);
    }

    //MODIFIES: this
    //EFFECTS: remove a course on the course list if the course is in the list
    //         and return true
    //         otherwise return false
    public boolean removeCourse(String courseName) {
        for (Course c : this.listOfCourse) {
            if (c.getCourseName().equals(courseName)) {
                listOfCourse.remove(c);
                return true;
            }
        }
        return false;
    }

    //EFFECTS: return the courses on the list
    public List<Course> getListOfCourse() {
        return this.listOfCourse;
    }

    //EFFECTS: return a specific course on the list
    public Course getSpecificCourse(String courseName) {
        Course result = null;
        for (Course c : this.listOfCourse) {
            if (c.getCourseName().equals(courseName)) {
                result = c;
            }
        }
        return result;
    }

    //EFFECTS: return the user name
    public String getUserName() {
        return this.userName;
    }

    /**
     * NOTE: some code related to JSON are from JsonSerializationDemo.
     */
    // EFFECT: return an account as a JSON object
    @Override
    public JSONObject toJson() {
        //stub
        JSONObject json = new JSONObject();
        json.put("userName", this.userName);
        json.put("courses", coursesToJson());
        return json;
    }

    // EFFECT: return courses in the account as a JSON array
    private JSONArray coursesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Course c : this.listOfCourse) {
            jsonArray.put(c.toJson());
        }
        return jsonArray;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account account = (Account) o;
        return userName.equals(account.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName);
    }
}
