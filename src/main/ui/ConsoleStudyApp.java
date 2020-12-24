package ui;

import model.Account;
import persistence.JsonWriter;
import persistence.JsonReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * StudyApp is the command line UI.
 * NOTE: some code of this class are from the TellerApp class of AccountNotRobust in this course.
 */
public class ConsoleStudyApp {
    private static final String JSON_DATA = "./data/studyApp.json";
    private Scanner input;
    private DisplayMenu menu = new DisplayMenu();
    private List<Account> accounts = new ArrayList<>();
    private ConsoleAccountManager manager = new ConsoleAccountManager();
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the study application
    public ConsoleStudyApp() {
        runStudy();
    }

    // MODIFIES: this
    // EFFECTS: processes user input for the main menu
    private void runStudy() {
        boolean keepGoing = true;
        jsonWriter = new JsonWriter(JSON_DATA);
        jsonReader = new JsonReader(JSON_DATA);

        loadAccount();
        manager.nextMenu();

        String command;
        input = new Scanner(System.in);

        while (keepGoing) {
            menu.displayMenu();
            command = input.next();
            input.nextLine();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processMenuCommand(command);
            }
        }

        System.out.println("\nStay Focus! Goodbye!");
    }

    // EFFECTS: processes user input for the account menu
    private void runAccount(Account account) {
        boolean keepGoing = true;
        String accountCommand;

        while (keepGoing) {
            menu.displayAccountMenu(account);
            accountCommand = input.next();
            input.nextLine();

            if (accountCommand.equals("q")) {
                keepGoing = reminder(account);
            } else {
                accountCommand = accountCommand.toLowerCase();
                processAccountCommand(accountCommand, account);
            }
        }
    }

    // EFFECTS: processes user command for the main menu
    private void processMenuCommand(String command) {
        if (command.equals("1")) {
            Account account = createAccount();
            if (account.getUserName() != null) {
                runAccount(account);
            } else {
                System.out.println("Failed to create your account.");
            }
        } else if (command.equals("2")) {
            Account account = manager.lookForAccount(accounts);
            manager.nextMenu();
            if (account != null) {
                runAccount(account);
            }
        } else {
            System.out.println("\nSelection not valid...");
        }
    }

    // MODIFIES: this, account
    // EFFECTS: processes user command for the account menu
    private void processAccountCommand(String command, Account account) {
        switch (command) {
            case "1":
                manager.getCourses(account);
                break;
            case "2":
                manager.addCourseToAccount(account);
                manager.runCourseMenu(account);
                break;
            case "3":
                manager.removeCourseFromAccount(account);
                break;
            case "4":
                manager.calculateAverage(account);
                break;
            case "s":
                saveAccount(account);
                manager.nextMenu();
                break;
            default:
                menu.displayInvalidInputErrorMessage();
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command and create a new account
    private Account createAccount() {
        Account user = null;
        System.out.println("Please entre your user name");
        String userName = input.nextLine();
        userName = userName.replace("\\s+", "");
        if (!userName.equals("")) {
            user = new Account(userName);
            accounts.add(user);
            System.out.println("\nYour account has been created!");
        } else {
            menu.displayInvalidInputErrorMessage();
        }
        return user;
    }

    // EFFECT: save accounts to a file if user presses s
    //         exit the app if user press q
    //         prompt error message otherwise
    private boolean reminder(Account account) {
        menu.displayReminder();
        String command = input.next();
        input.nextLine();
        if (command.equals("s")) {
            saveAccount(account);
            return false;
        } else if (command.equals("q")) {
            return false;
        } else {
            menu.displayInvalidInputErrorMessage();
        }
        return true;
    }

    /**
     * NOTE: some code of this class are from JsonSerializationDemo.
     */
    // EFFECTS: saves the accounts to file
    private void saveAccount(Account account) {
        try {
            jsonWriter.open();
            jsonWriter.write(accounts);
            jsonWriter.close();
            System.out.println("Saved " + account.getUserName() + " information to " + JSON_DATA);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_DATA);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads accounts from file
    private void loadAccount() {
        try {
            accounts = jsonReader.read(accounts);
            System.out.println("Loaded accounts from " + JSON_DATA);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_DATA);
        }
    }
}
