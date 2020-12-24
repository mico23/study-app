package ui;

import model.Account;
import model.Course;

/**
 * display account menu and course menu for users
 */

public class DisplayMenu {

    // EFFECTS: create a display object
    public DisplayMenu() {
    }

    // EFFECTS: displays main menu of options to user
    public void displayMenu() {
        System.out.println("\nWelcome to Study Effort Tracker!");
        System.out.println("\nSelect from:");
        System.out.println("\n 1 -> create a new account");
        System.out.println("\n 2 -> find your account");
        System.out.println("\n q -> exit");
    }

    // EFFECTS: displays account menu of options to user
    public void displayAccountMenu(Account account) {
        System.out.println("\nWelcome " + account.getUserName() + "!");
        System.out.println("\nSelect from:");
        System.out.println("\n 1 -> get course list");
        System.out.println("\n 2 -> add a course");
        System.out.println("\n 3 -> remove a course");
        System.out.println("\n 4 -> calculate average");
        System.out.println("\n s -> save your courses to file");
        System.out.println("\n q -> go back to the previous menu");
    }

    // EFFECTS: display the reminder menu to user
    public void displayReminder() {
        System.out.println("\nDo you want to save your data before exit the app?");
        System.out.println("\nSelect from:");
        System.out.println("\n s -> save your courses to file");
        System.out.println("\n q -> exit");
    }

    // EFFECTS:
    public void displayCourseList(Account account) {
        System.out.println("\nYou have the following courses:");
        for (Course c : account.getListOfCourse()) {
            System.out.println("\n" + c.getCourseName() + ", Total Hours: "
                    + c.sumEffort() + ", Outcome: " + c.getOutcome());
        }
    }

    // EFFECTS: displays course menu to user
    public void displayCourseMenuOptions() {
        System.out.println("\n 1 -> show a course details");
        System.out.println("\n 2 -> update a course details");
        System.out.println("\n 3 -> add a course");
        System.out.println("\n q -> go back to the previous menu");

    }

    // EFFECTS: displays course update menu of options to user
    public void displayCourseUpdateMenu() {
        System.out.println("\nUpdate Your Course Information");
        System.out.println("\nSelect from:");
        System.out.println("\n 1 -> add hours");
        System.out.println("\n 2 -> update outcome");
    }

    // EFFECTS: display error message for invalid inputs
    public void displayInvalidInputErrorMessage() {
        System.out.println("\nInvalid Input!");
    }
}
