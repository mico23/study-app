package ui;

import model.Account;
import model.Course;
import model.Hours;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.buttons.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * GraphicStudyApp is the graphic UI for the study app. Run this class in main to use the graphic UI.
 */

public class GraphicStudyApp extends JFrame {

    public enum Page { LOGIN, MAIN, SEARCH, CREATE, COURSE, ADD, HOURS, OUTCOME }

    private static final int WIDTH = 380;
    private static final int HEIGHT = 380;
    private static final String JSON_DATA = "./data/studyApp.json";
    private static final String SOUND_DATA = "./data/mouseClickSound.wav";
    private static final String ICON_DATA = "./data/stopIcon.jpg";
    private static final String DEFAULT_TEXT = "Please select from:";
    private static final String DEFAULT_TITLE = "Welcome to Study Effort Tracker!";
    private List<Account> accounts = new ArrayList<>();
    private Account currentAccount;
    private Course currentCourse;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JPanel contentArea;
    private JPanel buttonArea;
    private JLabel titleText;
    private JLabel contentText;
    private JTextField textField;
    private JScrollPane scrollText;
    private SoundEffect soundEffect;
    private ImageIcon warningIcon;
    private Page currentPage;

    // MODIFIES: this
    // EFFECTS: runs the study application
    public GraphicStudyApp() {
        super("Study Effort Tracker");
        soundEffect = new SoundEffect(SOUND_DATA);
        soundEffect.loadSound();
        initializeIcon();
        initializeWindow();
        initializeTextPanel();
        initializeButtonPanel();
        renderWelcomeButtons();
        contentArea.add(contentText);
        initializeJsonObjects();
        loadAccount();
    }

    // MODIFIES: this
    // EFFECTS:  initialize the window of the app
    private void initializeWindow() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // MUST TO HAVE IT TO CLOSE THE APP
        setLocationRelativeTo(null);
        setVisible(true);
        this.currentPage = Page.LOGIN;
        pack();
    }

    // MODIFIES: this
    // EFFECTS: initialize the JPanel area for buttons and dropdown list
    private void initializeButtonPanel() {
        buttonArea = new JPanel();
        buttonArea.setLayout(new GridLayout(0, 1));
        buttonArea.setSize(new Dimension(WIDTH / 2, HEIGHT / 2));
        buttonArea.setBorder(new EmptyBorder(13, 13, 13, 13));
        add(buttonArea, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: initialize the JPanel area for showing text
    private void initializeTextPanel() {
        contentArea = new JPanel();
        contentArea.setLayout(new GridLayout(0, 1));
        contentArea.setSize(new Dimension(WIDTH - 10, HEIGHT - 200));
        contentArea.setBorder(new EmptyBorder(13, 13, 13, 13));
        add(contentArea, BorderLayout.SOUTH);
        titleText = new JLabel(DEFAULT_TITLE);
        contentArea.add(titleText);
        contentText = new JLabel(DEFAULT_TEXT);
        contentArea.add(contentText);
    }

    /**
     * source: https://stackoverflow.com/questions/6714045/how-to-resize-jlabel-imageicon
     */
    // EFFECT: render the icon to fit the size of the message window
    private void initializeIcon() {
        ImageIcon icon = new ImageIcon(ICON_DATA);
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        warningIcon = new ImageIcon(scaledImage);
    }

    // EFFECTS: render the buttons on the first page when the app lunches
    public void renderWelcomeButtons() {
        contentText.setText(DEFAULT_TEXT);
        new CreateAccountButton(this, buttonArea, soundEffect);
        new LookForAccountButton(this, buttonArea, soundEffect);
        new ExitButton(this, buttonArea, soundEffect);
        refreshWindow();
    }

    // MODIFIES: this
    // EFFECTS: render the submission menu
    public void renderSubmissionMenu() {
        renderTitleOfContentArea();
        textField = new JTextField();
        contentArea.add(textField);
        new SubmitButton(this, buttonArea, soundEffect, accounts);
        new GoBackButton(this, buttonArea, soundEffect);
        refreshWindow();
    }

    // MODIFIES: this
    // EFFECTS: render the title of the submission menu
    public void renderTitleOfContentArea() {
        if (this.currentPage.equals(Page.SEARCH) || this.currentPage.equals(Page.CREATE)) {
            contentText.setText("Please Entre Your User Name:");
        } else if (this.currentPage.equals(Page.ADD)) {
            contentText.setText("Please Entre Your Course Name:");
        } else if (this.currentPage.equals(Page.HOURS)) {
            contentText.setText("Please Entre Hours:");
        } else if (this.currentPage.equals(Page.OUTCOME)) {
            contentText.setText("Please Entre Your Grades (as digits):");
        }
    }

    // MODIFIES: this
    // EFFECTS: remove the submission menu from the window
    public void removeSubmissionMenu() {
        contentText.setText(DEFAULT_TEXT);
        contentArea.remove(textField);
        buttonArea.removeAll();
    }

    // MODIFIES: this
    // EFFECTS: render the main menu
    public void renderMainMenu() {
        setPage(Page.MAIN);
        int numberOfCourses = currentAccount.getListOfCourse().size();
        titleText.setText(DEFAULT_TITLE);
        contentText.setText("You have " + numberOfCourses + " courses.");
        new CourseDropDownList(this, buttonArea, currentAccount);
        new AddCourseButton(this, buttonArea, soundEffect);
        new CalculateAvgButton(this, buttonArea, soundEffect, currentAccount);
        new SaveButton(this, buttonArea, soundEffect, accounts);
        new GoBackButton(this, buttonArea, soundEffect);
        refreshWindow();
    }

    // MODIFIES: this
    // EFFECTS: remove the course menu
    public void removeCourseMenu() {
        titleText.setText(DEFAULT_TITLE);
        contentText.setText(DEFAULT_TEXT);
        contentArea.remove(scrollText);
        buttonArea.removeAll();
    }

    // MODIFIES: this
    // EFFECTS: render the menu with course details, such as dates and grades
    public void renderCourseDetailMenu(Course course) {
        setPage(Page.COURSE);
        titleText.setText(course.getCourseName());
        contentText.setText("Course Details:");
        JLabel courseDetails = new JLabel(buildCourseDetails(course));
        scrollText = new JScrollPane(courseDetails);
        contentArea.add(scrollText);
        new AddHoursButton(this, buttonArea, soundEffect);
        new SetOutcomeButton(this, buttonArea, soundEffect);
        new RemoveCourseButton(this, buttonArea, soundEffect);
        new GoBackButton(this, buttonArea, soundEffect);
        refreshWindow();
    }

    // EFFECTS: create the string that contains the details of the input course
    public String buildCourseDetails(Course course) {
        List<Hours> listOfRecords = course.getEffort();
        int textSize = 17 + 32 * listOfRecords.size();
        StringBuilder stringBuilder = new StringBuilder(textSize);
        stringBuilder.append("<html>");
        if (listOfRecords.size() != 0) {
            stringBuilder.append("Your grade is: " + course.getOutcome() + ".<br>");
            for (Hours h : listOfRecords) {
                String hoursDetail = "You worked for "
                        + h.getHours() + "hours on " + h.getDate() + ".<br>";
                stringBuilder.append(hoursDetail);
            }
            stringBuilder.append("<html>");
        } else {
            stringBuilder.append("You have no courses saved yet.<html>");
        }
        return stringBuilder.toString();
    }

    // MODIFIES: this
    // EFFECTS: remove buttons in the button area
    public void removeButtons() {
        buttonArea.removeAll();
    }

    // EFFECTS: refresh the window for added or removed components
    public void refreshWindow() {
        this.revalidate();
        this.repaint();
    }

    // EFFECTS: initialize Json Objects for loading and saving features
    private void initializeJsonObjects() {
        jsonWriter = new JsonWriter(JSON_DATA);
        jsonReader = new JsonReader(JSON_DATA);
    }

    // MODIFIES: this
    // EFFECTS: loads accounts from a Json file
    private void loadAccount() {
        try {
            accounts = jsonReader.read(accounts);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to load file.");
            System.out.println("Unable to read from file: " + JSON_DATA);
        }
    }

    // MODIFIES: this
    // EFFECTS: save accounts to a Json file
    public void saveAccount() {
        try {
            jsonWriter.open();
            jsonWriter.write(accounts);
            jsonWriter.close();
            JOptionPane.showMessageDialog(null, "Your course information has been saved.");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error - file cannot be saved.");
        }
    }

    // EFFECTS: return the warning icon
    public ImageIcon getWarningIcon() {
        return this.warningIcon;
    }

    // EFFECTS: return the text field
    public JTextField getTextField() {
        return this.textField;
    }

    // EFFECTS: return the current page status
    public Page getCurrentPage() {
        return this.currentPage;
    }

    // EFFECTS: set the current page status
    public void setPage(Page page) {
        this.currentPage = page;
    }

    // MODIFIES: this
    // EFFECTS: set the current account to the input account
    public List<Account> getAccounts() {
        return this.accounts;
    }

    // MODIFIES: this
    // EFFECTS: set the current account to the input account
    public void setCurrentAccount(Account account) {
        this.currentAccount = account;
    }

    // MODIFIES: this
    // EFFECTS: set the current account to the input account
    public Account getCurrentAccount() {
        return this.currentAccount;
    }

    // MODIFIES: this
    // EFFECTS: set the current course as the input course
    public void setCurrentCourse(Course course) {
        this.currentCourse = course;
    }

    // EFFECTS: return the current course
    public Course getCurrentCourse() {
        return this.currentCourse;
    }
}
