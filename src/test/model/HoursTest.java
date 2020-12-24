package model;

import exceptions.NegativeValueError;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class HoursTest {
    private LocalDate today = LocalDate.now();
    private LocalDate someday = LocalDate.parse("2020-01-01");

    @Test
    void testConstructorPositiveInteger() {
        try {
            Hours positiveHours = new Hours(9);
            assertEquals(9, positiveHours.getHours());
            assertEquals(today, positiveHours.getDate());
        } catch (NegativeValueError e) {
            e.printStackTrace();
            fail("Error not expected");
        }
    }

    @Test
    void testConstructorNonPositiveInteger() {
        try {
            new Hours(-1);
            fail("Not expect to pass");
        } catch (NegativeValueError e) {
            e.printStackTrace();
            // expect error
        }
    }

    @Test
    void testSetDate() {
        try {
            Hours hours = new Hours(9);
            hours.setDate(someday);
            assertEquals(someday, hours.getDate());
        } catch (NegativeValueError e) {
            e.printStackTrace();
            fail("Error not expected");
        }
    }
}
