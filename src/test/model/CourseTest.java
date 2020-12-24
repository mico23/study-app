package model;

import exceptions.InvalidOutcomeError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CourseTest {
    private static final String COURSE_1 = "CPSC210";
    private static final Hours HOURS_1 = new Hours(6);
    private static final Hours HOURS_2 = new Hours(3);
    private static final int GRADE_1 = 80;
    private Course course;

    @BeforeEach
    void runBefore() {
        course = new Course(COURSE_1);
    }

    @Test
    void testConstructor() {
        Course anotherCourse = new Course(COURSE_1);
        assertEquals(COURSE_1, anotherCourse.getCourseName());
        assertEquals(0, anotherCourse.getOutcome());
    }

    @Test
    void testSetOutcome() {
        try {
            course.setOutcome(GRADE_1);
            assertEquals(GRADE_1, course.getOutcome());
        } catch (InvalidOutcomeError e) {
            e.printStackTrace();
            fail("Error not expected.");
        }
    }

    @Test
    void testSetNegativeOutcome() {
        try {
            course.setOutcome(-1);
            fail("Not expect to pass.");
        } catch (InvalidOutcomeError e) {
            e.printStackTrace();
            // expect error
        }
    }

    @Test
    void testSetOutcomeOverHundred() {
        try {
            course.setOutcome(101);
            fail("Not expect to pass.");
        } catch (InvalidOutcomeError e) {
            e.printStackTrace();
            // expect error
        }
    }

    @Test
    void testAddEffort() {
        List<Hours> hoursList = new ArrayList<>();
        hoursList.add(HOURS_1);

        course.addEffort(HOURS_1);

        assertEquals(hoursList, course.getEffort());
        assertEquals(1, course.getEffort().size());
    }

    @Test
    void testSumEffort() {
        int totalEffort = HOURS_1.getHours() + HOURS_2.getHours();
        course.addEffort(HOURS_1);
        course.addEffort(HOURS_2);

        assertEquals(totalEffort, course.sumEffort());
    }


}
