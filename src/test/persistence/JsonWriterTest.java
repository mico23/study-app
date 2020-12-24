package persistence;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * NOTE: NOTE: some code of this class are from JsonSerializationDemo.
 */
class JsonWriterTest extends JsonTest {
    private static Hours hours1 = new Hours(8);
    private static Hours hours2 = new Hours(10);
    private final int OUTCOME_1 = 85;
    private final int OUTCOME_2 = 90;

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterNoAccount() {
        try {
            List<Account> accounts = new ArrayList<>();
            JsonWriter writer = new JsonWriter("./data/testWriterNoAccount.json");
            writer.open();
            writer.write(accounts);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterNoAccount.json");
            List<Account> accountLoaded = reader.read(new ArrayList<>());
            assertEquals(0, accountLoaded.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterSingleAccount() {
        try {
            List<Account> singleAccount = createSingleAccount();
            JsonWriter writer = new JsonWriter("./data/testWriterSingleAccount.json");
            writer.open();
            writer.write(singleAccount);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterSingleAccount.json");
            List<Account> accountLoaded = reader.read(new ArrayList<>());
            List<Course> courseList = setUpCourseListAccount1();

            assertEquals("MARRY", accountLoaded.get(0).getUserName());
            checkCourse(accountLoaded.get(0), courseList);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterMultipleAccounts() {
        try {
            List<Account> multipleAccounts = createMultipleAccounts(createSingleAccount());
            JsonWriter writer = new JsonWriter("./data/testWriterMultipleAccounts.json");
            writer.open();
            writer.write(multipleAccounts);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterMultipleAccounts.json");
            List<Account> accountLoaded = reader.read(new ArrayList<>());
            List<Course> courseList1 = setUpCourseListAccount1();
            List<Course> courseList2 = setUpCourseListAccount2();

            assertEquals("MARRY", accountLoaded.get(0).getUserName());
            checkCourse(accountLoaded.get(0), courseList1);

            assertEquals("JOSEPH", accountLoaded.get(1).getUserName());
            checkCourse(accountLoaded.get(1), courseList2);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    //EFFECTS: create one account for testing
    private List<Account> createSingleAccount() {
        List<Account> accounts = new ArrayList<>();
        List<Course> courseList = setUpCourseListAccount1();
        Account account1 = new Account("MARRY");

        for (Course c : courseList) {
            account1.addCourse(c);
        }

        accounts.add(account1);

        return accounts;
    }

    //REQUIRES: the account list must be not empty
    //EFFECTS: create multiple accounts for testing by adding it the an exist account list
    private List<Account> createMultipleAccounts(List<Account> accounts) {
        Account account2 = new Account("JOSEPH");
        List<Course> courseList = setUpCourseListAccount2();

        account2.addCourse(courseList.get(0));
        accounts.add(account2);

        return accounts;
    }

    //EFFECTS: set up the courses for testing
    private List<Course> setUpCourseListAccount1() {
        List<Course> courseList = new ArrayList<>();
        Course course1 = new Course("CPSC121");
        Course course2 = new Course("CPSC210");

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

        course3.setOutcome(OUTCOME_2);
        course3.addEffort(hours2);
        courseList.add(course3);

        return courseList;
    }
}
