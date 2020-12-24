package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    private static final String USER_1 = "ABC123";
    private static final String COURSE_NAME = "MATH200";
    private Account account;
    private Course course1 = new Course("CPSC210");
    private Course course2 = new Course("CPSC310");

    @BeforeEach
    void runBefore() {
        account = new Account(USER_1);
    }

    @Test
    void testConstructor() {
        Account anotherAccount = new Account(USER_1);
        assertEquals(USER_1, anotherAccount.getUserName());
    }

    @Test
    void testEqualOverride() {
        Account sameAccount = new Account(USER_1);
        String accountString = USER_1;
        assertNotSame(sameAccount, account);
        assertNotEquals(account, null);
        assertNotEquals(account, accountString);
        assertEquals(account, account);
        assertEquals(sameAccount, account);
    }

    @Test
    void testHashOverride() {
        Account sameAccount = new Account(USER_1);
        Set<Account> accounts = new HashSet<Account>();
        accounts.add(account);
        assertTrue(accounts.contains(account));
        assertTrue(accounts.contains(sameAccount));
    }

    @Test
    void testAddCourse() {
        List<Course> courseList = new ArrayList<>();
        courseList.add(course1);

        account.addCourse(course1);
        assertEquals(courseList, account.getListOfCourse());
        assertEquals(1, account.getListOfCourse().size());
    }

    @Test
    void testRemoveCourseFail() {
        account.addCourse(course1);
        assertFalse(account.removeCourse(course2.getCourseName()));
    }

    @Test
    void testRemoveCourse() {
        account.addCourse(course1);
        assertTrue(account.removeCourse(course1.getCourseName()));
        assertEquals(0, account.getListOfCourse().size());
    }

    @Test
    void testNoAverage() {
        assertEquals(0, account.calculateAverage());
    }

    @Test
    void testCalculateAverage() {
        course1.setOutcome(80);
        course2.setOutcome(90);
        account.addCourse(course1);
        account.addCourse(course2);
        int average = (course1.getOutcome() + course2.getOutcome()) / 2;
        assertEquals(average, account.calculateAverage());
        assertEquals(2, account.getListOfCourse().size());
    }

    @Test
    void testCantFindCourse() {
        assertNull(account.getSpecificCourse(COURSE_NAME));
    }

    @Test
    void testFindCourse() {
        account.addCourse(course1);
        account.addCourse(course2);
        assertEquals(course1, account.getSpecificCourse("CPSC210"));
    }

}