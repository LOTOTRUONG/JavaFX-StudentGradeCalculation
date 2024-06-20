package loto.vn.sgcapplication.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import loto.vn.sgcapplication.dao.DAOFactory;
import loto.vn.sgcapplication.dao.StudentDAO;
import loto.vn.sgcapplication.metier.Course;
import loto.vn.sgcapplication.metier.Semester;
import loto.vn.sgcapplication.metier.Student;

import java.net.URL;
import java.util.*;

public class GCSystemController implements Initializable {
    @FXML
    private Button btnCalculate;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnShowMark;

    @FXML
    private ComboBox<Course> comboboxCourse;

    @FXML
    private ComboBox<String> comboboxSemester;

    @FXML
    private ComboBox<Student> comboboxStudentName;
    @FXML
    private TextField fieldIDStudent;

    @FXML
    private TextField fieldAttendance;

    @FXML
    private TextField fieldFinal;

    @FXML
    private TextField fieldMidterm;

    @FXML
    private TextField fieldQuiz1;

    @FXML
    private TextField fieldQuiz2;

    @FXML
    private TextField fieldQuiz3;

    @FXML
    private TextField fieldQuiz4;

    @FXML
    private TextField fieldQuiz5;

    @FXML
    private Label labelAttendance;

    @FXML
    private Label labelFinal;

    @FXML
    private Label labelGrade;

    @FXML
    private Label labelMidterm;

    @FXML
    private Label labelQuiz;

    @FXML
    private Label labelTotal;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboBoxStudent();
        setupComboBoxListener();

    }


    public void comboBoxStudent(){
        List<Student> students = DAOFactory.getStudentDAO().getAll();
        Set<String> uniqueFullNames = new HashSet<>();
        List<Student> uniqueStudents = new ArrayList<>();

        for (Student student : students) {
            if (uniqueFullNames.add(String.valueOf(student.getFullName()))) {
                uniqueStudents.add(student);
            }
        }
        ObservableList<Student> nameStudents = FXCollections.observableArrayList(uniqueStudents);
        comboboxStudentName.setItems(nameStudents);
    }

    public void comboBoxCourse(){
        List<Course> courses = DAOFactory.getCourseDAO().getAll();
        ObservableList<Course> nameCourses = FXCollections.observableArrayList(courses);
        comboboxCourse.setItems(nameCourses);
    }

    private void setupComboBoxListener() {
        comboboxStudentName.setOnAction(event -> {
            Student selectedStudent = comboboxStudentName.getValue();
            if (selectedStudent != null) {
                fieldIDStudent.setText(String.valueOf(selectedStudent.getIdStudent().get()));

                //comboboxCourse
                List<Course> courses = DAOFactory.getStudentDAO().getCoursesByStudentFullName(selectedStudent.getFullName().get());
                Set<String> uniqueCourseName = new HashSet<>();
                List<Course> uniqueCourse = new ArrayList<>();
                for (Course course : courses) {
                    if (uniqueCourseName.add(course.getNameCourse())){
                        uniqueCourse.add(course);
                    }
                }
                ObservableList<Course> courseObservableList = FXCollections.observableArrayList(uniqueCourse);
                comboboxCourse.setItems(courseObservableList);

                //comboboxSemester
                List<Semester> semesters = DAOFactory.getSemesterDAO().getAll();
                ObservableList<String> semesterObservableList = FXCollections.observableArrayList();
                for (Semester semester : semesters) {
                    semesterObservableList.add(semester.getNameSemester());
                }
                comboboxSemester.setItems(semesterObservableList);
                }
                else {
                fieldIDStudent.setText("");
                comboboxCourse.setItems(FXCollections.emptyObservableList());
                comboboxSemester.setItems(FXCollections.emptyObservableList());
                }
        });
    }
}
