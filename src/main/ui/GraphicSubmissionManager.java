package ui;

import exceptions.InvalidOutcomeError;
import exceptions.NegativeValueError;
import model.Account;
import model.Course;
import model.Hours;

import javax.swing.*;
import java.util.List;

/**
 * GraphicSubmissionManager is to handle the methods called by the UI.
 */

public class GraphicSubmissionManager {
    private List<Account> accounts;
    private GraphicStudyApp frame;
    private String userInput;

    // EFFECTS: constructor of the graphic submission manager
    //          it takes GraphicStudyApp object and a string as inputs
    public GraphicSubmissionManager(GraphicStudyApp frame, String userInput) {
        this.userInput = userInput;
        this.frame = frame;
        this.accounts = frame.getAccounts();
    }

    // EFFECTS: constructor of the graphic submission manager
    //          it only takes GraphicStudyApp object as the input
    public GraphicSubmissionManager(GraphicStudyApp frame) {
        this.frame = frame;
        this.accounts = frame.getAccounts();
    }

    // EFFECTS: process the user input to create an account
    //          return a popup message if the account already exists or input is invalid
    public void createAccount() {
        if (!userInput.equals("")) {
            Account account = new Account(userInput);
            if (!accounts.contains(account)) {
                accounts.add(account);
                returnToMainMenu(account);
                JOptionPane.showMessageDialog(null, "Your account has been created.");
            } else {
                invalidInputMessage();
            }
        } else {
            invalidInputMessage();
        }
    }

    // EFFECTS: process the user input to search for an account
    //          return a popup message if the account cannot be found
    public void searchAccount() {
        Account account = lookForAccount(accounts, userInput);
        if (account != null) {
            returnToMainMenu(account);
            frame.setPage(GraphicStudyApp.Page.COURSE);
        } else {
            JOptionPane.showMessageDialog(null, "Your input user name does not exists.",
                    null, JOptionPane.WARNING_MESSAGE, frame.getWarningIcon());
        }
    }

    // EFFECTS: searches a user name and return the result
    //          return null if the account does not exist
    private Account lookForAccount(List<Account> accounts, String userName) {
        Account result;
        for (Account account : accounts) {
            if (account.getUserName().equals(userName)) {
                result = account;
                return result;
            }
        }
        return null;
    }

    // EFFECTS: add a course to the account
    //          return a popup message if the input is invalid
    public void addCourseToAccount() {
        Account account = frame.getCurrentAccount();
        if (!userInput.equals("")) {
            Course course = new Course(userInput);
            account.addCourse(course);
            returnToMainMenu(account);
            JOptionPane.showMessageDialog(null, "Your course has been added.");
        } else {
            invalidInputMessage();
        }
    }

    // EFFECTS: remove a course from the account
    public void removeCourseFromAccount() {
        Course courseToRemove = frame.getCurrentCourse();
        frame.getCurrentAccount().removeCourse(courseToRemove.getCourseName());
        frame.setCurrentCourse(null);
    }

    // EFFECTS: process user input to add hours to a course
    public void addHoursToCourse() {
        try {
            Hours effort = new Hours(parseIntegerInput(userInput));
            frame.getCurrentCourse().addEffort(effort);
            returnToCourseMenu();
        } catch (NegativeValueError e) {
            invalidInputMessage();
        }
    }

    // EFFECTS: process user input to set the outcome of a course
    public void setCourseOutcome() {
        try {
            frame.getCurrentCourse().setOutcome(parseIntegerInput(userInput));
            returnToCourseMenu();
        } catch (InvalidOutcomeError e) {
            invalidInputMessage();
        }
    }

    // EFFECTS: render the main menu window
    private void returnToMainMenu(Account account) {
        frame.setCurrentAccount(account);
        frame.removeSubmissionMenu();
        frame.renderMainMenu();
        frame.revalidate();
        frame.repaint();
    }

    // EFFECTS: render the course menu window
    private void returnToCourseMenu() {
        frame.removeSubmissionMenu();
        frame.renderCourseDetailMenu(frame.getCurrentCourse());
        frame.revalidate();
        frame.repaint();
    }

    // EFFECTS: parse integers from user input
    //          return -1 if the user input is non-integer
    private int parseIntegerInput(String userInput) {
        try {
            return Integer.parseInt(userInput);
        } catch (NumberFormatException e) {
            invalidInputMessage();
        }
        return -1;
    }

    // EFFECTS: prompt an error message for invalid inputs
    private void invalidInputMessage() {
        JOptionPane.showMessageDialog(null, "Invalid input, please try again.",
                null, JOptionPane.WARNING_MESSAGE, frame.getWarningIcon());
    }
}
