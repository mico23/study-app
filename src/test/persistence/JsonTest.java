package persistence;

import model.Account;
import model.Course;
import model.Hours;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * JsonTest is to compare the courses in the account parsed by JSON Reader with the expected courses
 */
public class JsonTest {
    protected void checkCourse(Account account, List<Course> coursesList) {
        List<Course> accountCourses = account.getListOfCourse();
        int listSize = coursesList.size();
        assertEquals(listSize, accountCourses.size());
        int cursor = 0;
        while (cursor < listSize) {
            Course course = coursesList.get(cursor);
            Course accountCourse = accountCourses.get(cursor);
            assertEquals(course.getCourseName(), accountCourse.getCourseName());
            assertEquals(course.getOutcome(), accountCourse.getOutcome());
            checkEffort(course.getEffort(), accountCourse.getEffort());

            cursor++;
        }
    }

    private void checkEffort(List<Hours> courseEffort, List<Hours> accountEffort) {
        assertEquals(courseEffort.size(), accountEffort.size());
        int listSize = courseEffort.size();
        int cursor = 0;
        while (cursor < listSize) {
            Hours courseHours = courseEffort.get(cursor);
            Hours accountHours = accountEffort.get(cursor);
            assertEquals(courseHours.getHours(), accountHours.getHours());
            assertEquals(courseHours.getDate(), accountHours.getDate());

            cursor++;
        }
    }
}
