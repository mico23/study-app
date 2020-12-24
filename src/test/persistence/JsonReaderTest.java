package persistence;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * NOTE: NOTE: some code of this class are from JsonSerializationDemo.
 */
public class JsonReaderTest extends JsonTest {
    private List<Account> accountHolder;
    private final LocalDate DATE_1 = LocalDate.parse("2020-10-29");
    private final int OUTCOME_1 = 85;
    private final int OUTCOME_2 = 90;
    private static Hours hours1 = new Hours(8);
    private static Hours hours2 = new Hours(10);

    @BeforeEach
    void runBefore() {
        accountHolder = new ArrayList<>();
    }

    @Test
    void testReaderFail() {
        JsonReader reader = new JsonReader("./data/nonExistentFile.json");
        try {
            reader.read(accountHolder);
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderNoAccount() {
        JsonReader reader = new JsonReader("./data/testReaderNoAccount.json");
        try {
            List<Account> accounts = reader.read(accountHolder);
            assertEquals(0, accounts.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderSingleAccount() {
        JsonReader reader = new JsonReader("./data/testReaderSingleAccount.json");
        try {
            List<Account> accounts = reader.read(accountHolder);
            List<Course> courseList = setUpCourseListAccount1();
            assertEquals(1, accounts.size());
            assertEquals("MIKE", accounts.get(0).getUserName());
            checkCourse(accounts.get(0), courseList);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderMultipleAccounts() {
        JsonReader reader = new JsonReader("./data/testReaderMultipleAccounts.json");
        try {
            List<Account> accounts = reader.read(accountHolder);
            List<Course> courseList1 = setUpCourseListAccount1();
            List<Course> courseList2 = setUpCourseListAccount2();
            assertEquals(2, accounts.size());

            assertEquals("MIKE", accounts.get(0).getUserName());
            checkCourse(accounts.get(0), courseList1);

            assertEquals("PETER", accounts.get(1).getUserName());
            checkCourse(accounts.get(1), courseList2);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    //MODIFIES: this
    //EFFECTS: set the dates to the dates corresponding to the saved file
    private void setCorrectDates() {
        hours1.setDate(DATE_1);
        hours2.setDate(DATE_1);
    }

    //EFFECTS: set up the courses for testing
    private List<Course> setUpCourseListAccount1() {
        List<Course> courseList = new ArrayList<>();
        Course course1 = new Course("CPSC121");
        Course course2 = new Course("CPSC210");
        setCorrectDates();

        course1.setOutcome(OUTCOME_1);
        course1.addEffort(hours1);
        courseList.add(course1);

        course2.setOutcome(OUTCOME_2);
        courseList.add(course2);

        return courseList;
    }

    // EFFECTS: creates a course list
    private List<Course> setUpCourseListAccount2() {
        List<Course> courseList = new ArrayList<>();
        Course course3 = new Course("MATH312");
        setCorrectDates();

        course3.setOutcome(OUTCOME_2);
        course3.addEffort(hours2);
        courseList.add(course3);

        return courseList;
    }
}
