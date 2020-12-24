package ui;

import model.Account;
import model.Course;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * CourseDropDownList is to create the dropdown list for the courses in an account
 */
public class CourseDropDownList implements ActionListener {
    private JComboBox courseList;
    private GraphicStudyApp frame;
    private Account account;

    // EFFECTS: constructor of CourseDropDownList
    //          it takes GraphicStudyApp object, JPanel object and Account object as the inputs
    public CourseDropDownList(GraphicStudyApp frame, JPanel panel, Account account) {
        this.frame = frame;
        this.account = account;
        createCourseDropDownList(panel);
    }

    // MODIFIES: this
    // EFFECTS: generate the course dropdown list and add it to the JPanel object
    //          the default selection is at Index 0
    public void createCourseDropDownList(JPanel panel) {
        //EFFECTS: Create the combo box, select item at index 0.
        createCourseList(account);
        courseList.setSelectedIndex(0);
        courseList.addActionListener(this);
        panel.add(courseList);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox) e.getSource();
        String courseName = (String) cb.getSelectedItem();
        if (!courseName.equals("")) {
            frame.setCurrentCourse(retrieveCourseInfo(courseName));
            frame.removeButtons();
            frame.renderCourseDetailMenu(frame.getCurrentCourse());
        }
    }

    // MODIFIES: this
    // EFFECTS: add courses in the account to the dropdown list
    public void createCourseList(Account account) {
        String[] defaultString = {"",};
        courseList = new JComboBox(defaultString);
        List<Course> courses = account.getListOfCourse();
        for (Course course : courses) {
            courseList.addItem(course.getCourseName());
        }
    }

    // EFFECTS: retrieve the course from an account
    public Course retrieveCourseInfo(String courseName) {
        return account.getSpecificCourse(courseName);
    }
}
