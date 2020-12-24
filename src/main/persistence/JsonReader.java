package persistence;

import model.Account;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import model.Course;
import model.Hours;
import org.json.*;

/**
 * JsonReader is to read account data on a JSON file.
 * NOTE: some code of this class are from JsonSerializationDemo.
 */
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // MODIFIES: accounts
    // EFFECTS: reads accounts from file and returns it;
    // throws IOException if an error occurs reading data from file
    public List<Account> read(List<Account> accounts) throws IOException {
        String jsonData = readFile(source);
        JSONArray jsonArray = new JSONArray(jsonData);
        return parseAccount(jsonArray, accounts);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // MODIFIES: accounts
    // EFFECTS: parses accounts from JSON array and returns it
    private List<Account> parseAccount(JSONArray jsonArray, List<Account> accounts) {
        for (Object json : jsonArray) {
            JSONObject accountObject = (JSONObject) json;
            String name = accountObject.getString("userName");
            Account account = new Account(name);
            addCourseLoad(account, accountObject);
            accounts.add(account);
        }
        return accounts;
    }

    // MODIFIES: account
    // EFFECTS: parses courses from JSON object and adds them to workroom
    private void addCourseLoad(Account account, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("courses");
        for (Object json : jsonArray) {
            JSONObject courseObject = (JSONObject) json;
            String name = courseObject.getString("courseName");
            Course course = new Course(name);
            int outcome = courseObject.getInt("outcome");
            course.setOutcome(outcome);
            addCourseEfforts(course, courseObject);
            account.addCourse(course);
        }
    }

    // MODIFIES: course
    // EFFECTS: parses efforts from JSON object and adds it to workroom
    private void addCourseEfforts(Course course, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("effort");
        for (Object json : jsonArray) {
            JSONObject effortObject = (JSONObject) json;
            int hours = effortObject.getInt("hours");
            String dateString = effortObject.getString("date");
            LocalDate date = LocalDate.parse(dateString);
            Hours effort = new Hours(hours);
            effort.setDate(date);
            course.addEffort(effort);
        }
    }
}
