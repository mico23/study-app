package ui;

import exceptions.InvalidOutcomeError;
import exceptions.NegativeValueError;
import model.Account;
import model.Course;
import model.Hours;

import java.util.List;
import java.util.Scanner;

/**
 * AccountManager is to run the command line UI of a user's account.
 */
public class ConsoleAccountManager {
    private Scanner input;
    private DisplayMenu menu = new DisplayMenu();

    // EFFECTS: construct a manager for the account menu
    public ConsoleAccountManager() {
        this.input = new Scanner(System.in);
    }

    // EFFECTS: display the courses saved on the account
    //          and display options to user to interact with courses
    public void getCourses(Account account) {
        if (account.getListOfCourse().size() != 0) {
            runCourseMenu(account);
        } else {
            System.out.println("\nYou do not have any saved courses.");
        }
    }

    // EFFECTS: display course menu to user until the user quits
    public void runCourseMenu(Account account) {
        boolean keepGoing = true;
        while (keepGoing) {
            menu.displayCourseList(account);
            menu.displayCourseMenuOptions();
            String command = getStringInput();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                interactWithCoursesMenu(account, command);
            }
        }
    }

    // EFFECTS: processes user command for the course menu
    public void interactWithCoursesMenu(Account account, String command) {
        switch (command) {
            case "1":
                searchCourseDetails(account);
                break;
            case "2":
                updateCourseDetails(account);
                break;
            case "3":
                addCourseToAccount(account);
                break;
            default:
                System.out.println("\nInvalid Input!");
                break;
        }
    }

    // EFFECTS: add a course to the account
    public void addCourseToAccount(Account account) {
        System.out.println("\nPlease entre your course name:");
        String courseName = getStringInput();
        if (!courseName.equals("")) {
            Course course = new Course(courseName);
            account.addCourse(course);
            System.out.println("\nCourse saved!");
        } else {
            System.out.println("\nInvalid Input!");
        }
    }

    // EFFECTS: update the details of a course and displays the result
    public void updateCourseDetails(Account account) {
        System.out.println("\nPlease entre your course name:");
        String courseName = getStringInput();
        Course course = account.getSpecificCourse(courseName);
        if (course != null) {
            menu.displayCourseUpdateMenu();
            String courseCommand = input.next();
            input.nextLine();
            if (courseCommand.equals("1")) {
                setCourseHours(course);
            } else if (courseCommand.equals("2")) {
                setCourseEffort(course);
            } else {
                System.out.println("\nYour output is invalid!");
            }
        }
    }

    // EFFECTS: process user input for setting up a course
    private void setCourseHours(Course course) {
        System.out.println("\nPlease the hours (as integer digits):");
        int inputHours = getIntegerInput();
        try {
            Hours hours = new Hours(inputHours);
            course.addEffort(hours);
            System.out.println("\nYour course effort has been updated!");
        } catch (NegativeValueError e) {
            System.out.println("\nYour output is invalid!");
        }
    }

    // EFFECTS: process user input for adding efforts to a course
    private void setCourseEffort(Course course) {
        System.out.println("\nPlease the grades (as integer digits):");
        int outcome = getIntegerInput();
        try {
            course.setOutcome(outcome);
            System.out.println("\nYour course outcome has been updated!");
        } catch (InvalidOutcomeError e) {
            System.out.println("\nPLease entre an integer within the range from 0 to 100!");
        }
    }

    // EFFECTS: searches a course and displays the result
    public void searchCourseDetails(Account account) {
        System.out.println("\nPlease entre the course name:");
        String courseName = getStringInput();
        if (!courseName.equals("")) {
            Course result = account.getSpecificCourse(courseName);
            if (result != null) {
                System.out.println("\nThe course name: " + result.getCourseName());
                getCourseEffort(result);
            }
        } else {
            System.out.println("\nCannot find your course.");
        }
    }

    // EFFECTS: remove a course from the account and displays the result
    public void removeCourseFromAccount(Account account) {
        System.out.println("\nPlease entre your course name:");
        String courseName = getStringInput();
        if (!courseName.equals("")) {
            boolean result = account.removeCourse(courseName);
            if (result) {
                System.out.println("\nThe course has been removed.");
            } else {
                System.out.println("\nCannot find your course.");
            }
        }
    }

    // EFFECTS: retrieve the course efforts if there are efforts saved,
    //          otherwise return a message to users.
    private void getCourseEffort(Course course) {
        if (course.getEffort().size() != 0) {
            for (Hours h : course.getEffort()) {
                System.out.println("\n You worked for " + h.getHours() + "hours on " + h.getDate() + ".");
            }
        } else {
            System.out.println("\nNo details recorded yet.");
        }
    }

    // EFFECTS: displays the user's overall average of all courses
    public void calculateAverage(Account account) {
        System.out.println("\nYour overall average is:");
        System.out.println(account.calculateAverage());
        runCourseMenu(account);
    }

    // EFFECTS: searches a user name and displays the result
    public Account lookForAccount(List<Account> accounts) {
        System.out.println("\nPlease entre your user name: ");
        Account result;
        String userName = getStringInput();
        for (Account account : accounts) {
            if (account.getUserName().equals(userName)) {
                result = account;
                System.out.println("\nUser name found. Retrieving your data.");
                return result;
            }
        }
        System.out.println("\nThe user name you entered does not exist.");
        return null;
    }

    // EFFECTS: return the display menu
    public DisplayMenu getDisplayMenu() {
        return this.menu;
    }

    // MODIFIES: this
    // EFFECTS: parse strings from user input
    private String getStringInput() {
        String userInput = input.nextLine();
        return userInput.replaceAll("\\s+", "");
    }

    // MODIFIES: this
    // EFFECTS: parse integers from user input
    private int getIntegerInput() {
        try {
            String inputHours = input.nextLine();
            return Integer.parseInt(inputHours);
        } catch (NumberFormatException e) {
            System.out.println("\nInvalid Input. Please entre integers that are greater than 0.");
            return -1;
        }
    }
}
