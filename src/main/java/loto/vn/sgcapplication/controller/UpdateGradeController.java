package loto.vn.sgcapplication.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import loto.vn.sgcapplication.dao.DAOFactory;
import loto.vn.sgcapplication.interfaces.FieldUpdateListener;
import loto.vn.sgcapplication.metier.Course;
import loto.vn.sgcapplication.metier.Inscription;
import loto.vn.sgcapplication.metier.Semester;
import loto.vn.sgcapplication.metier.Student;

import java.net.URL;
import java.util.*;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class UpdateGradeController implements Initializable {
    @FXML
    private Button btnReset, btnShowMark, btnUpdate;

    @FXML
    private ComboBox<Course> comboboxCourse;

    @FXML
    private ComboBox<Semester> comboboxSemester;

    @FXML
    private ComboBox<Student> comboboxStudentName;

    @FXML
    private ComboBox<Inscription> comboboxYear;

    @FXML
    private TextField fieldIDStudent, fieldAttendance, fieldFinal, fieldMidterm, fieldQuiz1, fieldQuiz2, fieldQuiz3, fieldQuiz4, fieldQuiz5;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboBoxStudent();
        setupComboBoxListener();
        setupButtonActions();
    }
    private void setupButtonActions() {
        btnShowMark.setOnAction(event -> showStudentMarks());
        btnReset.setOnAction(event -> clearAll());
        btnUpdate.setOnAction(event -> {
            updateStudentGrades();
        });
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


    private void setupComboBoxListener() {
        comboboxStudentName.setOnAction(event -> {
            Student selectedStudent = comboboxStudentName.getValue();
            if (selectedStudent != null) {
                fieldIDStudent.setText(String.valueOf(selectedStudent.getIdStudent()));
                //comboboxAcademicYear
                getAcademicYearComboBox(selectedStudent);
                //comboboxCourse
                comboboxYear.setOnAction(inscriptionEvent -> {
                    Inscription selectedAcademicYear = comboboxYear.getValue();
                    if (selectedAcademicYear != null) {
                        getCourseComboBox(selectedStudent, selectedAcademicYear);
                        //comboboxSemester
                        comboboxCourse.setOnAction(courseEvent -> {
                            Course selectedCourse = comboboxCourse.getValue();
                            if (selectedCourse != null) {
                                getSemesterComboBox(selectedStudent,selectedAcademicYear, selectedCourse);
                            }
                        });
                    }
                });
            }
            else {
                fieldIDStudent.setText("");
                comboboxCourse.setItems(FXCollections.emptyObservableList());
                comboboxSemester.setItems(FXCollections.emptyObservableList());
            }
        });
    }

    private void getAcademicYearComboBox(Student student){
        List<Inscription> inscriptions = DAOFactory.getStudentDAO().getAcademicYearByStudentFullName(student.getFullName());
        Set<String> uniqueAYName = new HashSet<>();
        List<Inscription> uniqueAY = new ArrayList<>();
        for (Inscription inscription : inscriptions) {
            if (uniqueAYName.add(inscription.getAcademicYear())){
                uniqueAY.add(inscription);
            }
        }
        ObservableList<Inscription> inscriptionObservableList = FXCollections.observableArrayList(uniqueAY);
        comboboxYear.setItems(inscriptionObservableList);
    }

    private void getCourseComboBox(Student student, Inscription inscription){
        List<Course> courses = DAOFactory.getStudentDAO().getCoursesByStudentFullNameAndAcademicYear(student.getFullName(), inscription.getAcademicYear());
        Set<String> uniqueCourseName = new HashSet<>();
        List<Course> uniqueCourse = new ArrayList<>();
        for (Course course : courses) {
            if (uniqueCourseName.add(course.getNameCourse())){
                uniqueCourse.add(course);
            }
        }
        ObservableList<Course> courseObservableList = FXCollections.observableArrayList(uniqueCourse);
        comboboxCourse.setItems(courseObservableList);
    }

    private  void getSemesterComboBox(Student student,Inscription inscription, Course course){
        List<Semester> semesters = DAOFactory.getStudentDAO().getSemesterByStudentFullNameAndCourse(student.getFullName(), inscription.getAcademicYear(), course.getNameCourse());
        Set<String> uniqueSemesterName = new HashSet<>();
        List<Semester> uniqueSemester = new ArrayList<>();
        for (Semester semester : semesters) {
            if (uniqueSemesterName.add(semester.getNameSemester())){
                uniqueSemester.add(semester);
            }
        }
        ObservableList<Semester> semesterObservableList = FXCollections.observableArrayList(uniqueSemester);
        comboboxSemester.setItems(semesterObservableList);
    }

    private void showStudentMarks() {
        Student selectedStudent = comboboxStudentName.getValue();
        Inscription selectedAcademicYear = comboboxYear.getValue();
        Course selectedCourse = comboboxCourse.getValue();
        Semester selectedSemester = comboboxSemester.getValue();

        String[] categories = {"Attendance", "Midterm", "Final", "quiz1", "quiz2", "quiz3", "quiz4", "quiz5"};
        TextField[] fields = {fieldAttendance, fieldMidterm, fieldFinal, fieldQuiz1, fieldQuiz2, fieldQuiz3, fieldQuiz4, fieldQuiz5};
        if (selectedStudent != null && selectedAcademicYear != null && selectedCourse != null && selectedSemester != null) {
            for (int i = 0; i < categories.length; i++) {
                String category = categories[i];
                Float grade = DAOFactory.getGradeDAO().getGrade(selectedStudent.getFullName(), selectedAcademicYear.getAcademicYear(), selectedCourse.getNameCourse(), selectedSemester.getNameSemester(), category);
                if (grade != null) {
                    fields[i].setText(grade.toString());
                } else {
                    fields[i].clear();
                }
            }
        }
    }

    private void updateStudentGrades() {
        Student selectedStudent = comboboxStudentName.getValue();
        Inscription selectedAcademicYear = comboboxYear.getValue();
        Course selectedCourse = comboboxCourse.getValue();
        Semester selectedSemester = comboboxSemester.getValue();

        if (selectedStudent != null && selectedAcademicYear != null && selectedCourse != null && selectedSemester != null) {
            // Retrieve grades from text fields
            Float attendance = parseFloat(fieldAttendance.getText());
            Float finalGrade = parseFloat(fieldFinal.getText());
            Float midterm = parseFloat(fieldMidterm.getText());
            Float quiz1 = parseFloat(fieldQuiz1.getText());
            Float quiz2 = parseFloat(fieldQuiz2.getText());
            Float quiz3 = parseFloat(fieldQuiz3.getText());
            Float quiz4 = parseFloat(fieldQuiz4.getText());
            Float quiz5 = parseFloat(fieldQuiz5.getText());

            DAOFactory.getGradeDAO().updateGrade(selectedStudent.getFullName(), selectedAcademicYear.getAcademicYear(),
                    selectedCourse.getNameCourse(), selectedSemester.getNameSemester(), "Attendance", attendance);
            DAOFactory.getGradeDAO().updateGrade(selectedStudent.getFullName(), selectedAcademicYear.getAcademicYear(),
                    selectedCourse.getNameCourse(), selectedSemester.getNameSemester(), "Midterm", midterm);
            DAOFactory.getGradeDAO().updateGrade(selectedStudent.getFullName(), selectedAcademicYear.getAcademicYear(),
                    selectedCourse.getNameCourse(), selectedSemester.getNameSemester(), "Final", finalGrade);
            DAOFactory.getGradeDAO().updateGrade(selectedStudent.getFullName(), selectedAcademicYear.getAcademicYear(),
                    selectedCourse.getNameCourse(), selectedSemester.getNameSemester(), "quiz1", quiz1);
            DAOFactory.getGradeDAO().updateGrade(selectedStudent.getFullName(), selectedAcademicYear.getAcademicYear(),
                    selectedCourse.getNameCourse(), selectedSemester.getNameSemester(), "quiz2", quiz2);
            DAOFactory.getGradeDAO().updateGrade(selectedStudent.getFullName(), selectedAcademicYear.getAcademicYear(),
                    selectedCourse.getNameCourse(), selectedSemester.getNameSemester(), "quiz3", quiz3);
            DAOFactory.getGradeDAO().updateGrade(selectedStudent.getFullName(), selectedAcademicYear.getAcademicYear(),
                    selectedCourse.getNameCourse(), selectedSemester.getNameSemester(), "quiz4", quiz4);
            DAOFactory.getGradeDAO().updateGrade(selectedStudent.getFullName(), selectedAcademicYear.getAcademicYear(),
                    selectedCourse.getNameCourse(), selectedSemester.getNameSemester(), "quiz5", quiz5);
            showInfoAlert("Success", "Student grades updated successfully.");
        }
        else {
            showErrorAlert("Error", "Please ensure all fields are selected.");
        }
    }

    private Float parseFloat(String value) {
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void clearAll() {
        comboboxStudentName.setValue(null);
        fieldIDStudent.clear();
        comboboxYear.setValue(null);
        comboboxCourse.setValue(null);
        comboboxSemester.setValue(null);
        fieldAttendance.clear();
        fieldFinal.clear();
        fieldMidterm.clear();
        fieldQuiz1.clear();
        fieldQuiz2.clear();
        fieldQuiz3.clear();
        fieldQuiz4.clear();
        fieldQuiz5.clear();
    }

    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showInfoAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
