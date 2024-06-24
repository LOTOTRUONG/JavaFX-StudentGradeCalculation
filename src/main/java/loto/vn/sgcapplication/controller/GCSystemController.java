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
import loto.vn.sgcapplication.metier.*;

import java.net.URL;
import java.util.*;

public class GCSystemController implements Initializable {
    @FXML
    private Button btnCalculate, btnReset, btnCalculateFinal;

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
    @FXML
    private Label labelSemester, labelIDandName, labelAcademicYear, labelCourse, labelAttendance, labelFinal, labelTotalPerCourse, labelAverageCourse, labelGrade, labelMidterm, labelQuiz, labelTotal, labelGradeCourse, labelGradePerCourse;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboBoxStudent();
        setupComboBoxListener();
        setupButtonActions();

    }
    private void setupButtonActions() {
        btnCalculate.setOnAction(event -> calculateAndDisplayGrade());
        btnCalculateFinal.setOnAction(event -> calculateTotalGrade());
        btnReset.setOnAction(event -> clearAll());
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
                updateAcademicYearComboBox(selectedStudent);
                //comboboxCourse
                comboboxYear.setOnAction(inscriptionEvent -> {
                    Inscription selectedAcademicYear = comboboxYear.getValue();
                    if (selectedAcademicYear != null) {
                        updateCourseComboBox(selectedStudent, selectedAcademicYear);
                        //comboboxSemester
                        comboboxCourse.setOnAction(courseEvent -> {
                            Course selectedCourse = comboboxCourse.getValue();
                            if (selectedCourse != null) {
                                updateSemesterComboBox(selectedStudent,selectedAcademicYear, selectedCourse);
                            }
                            else {
                                comboboxSemester.setValue(null);
                            }
                        });
                    }
                });
                }
        });
    }

    private void updateAcademicYearComboBox(Student student){
        List<Inscription> inscriptions = DAOFactory.getStudentDAO().getAcademicYearByStudentFullName(student.getFullName());
        Set<String> uniqueAYName = new HashSet<>();
        List<Inscription> uniqueAY = new ArrayList<>();
        for (Inscription inscription : inscriptions) {
            if (uniqueAYName.add(inscription.getAcademicYear())){
                uniqueAY.add(inscription);
            }
        }
        uniqueAY.add(0, null);
        ObservableList<Inscription> inscriptionObservableList = FXCollections.observableArrayList(uniqueAY);
        comboboxYear.setItems(inscriptionObservableList);
    }

    private void updateCourseComboBox(Student student, Inscription inscription){
        List<Course> courses = DAOFactory.getStudentDAO().getCoursesByStudentFullNameAndAcademicYear(student.getFullName(), inscription.getAcademicYear());
        Set<String> uniqueCourseName = new HashSet<>();
        List<Course> uniqueCourse = new ArrayList<>();
        for (Course course : courses) {
            if (uniqueCourseName.add(course.getNameCourse())){
                uniqueCourse.add(course);
            }
        }
        uniqueCourse.add(0, null);
        ObservableList<Course> courseObservableList = FXCollections.observableArrayList(uniqueCourse);
        comboboxCourse.setItems(courseObservableList);
    }

    private  void updateSemesterComboBox(Student student,Inscription inscription, Course course){
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


    private void calculateAndDisplayGrade() {
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

        if (selectedStudent == null || selectedAcademicYear == null || selectedSemester == null) {
            if (selectedCourse == null)
                clearFieldsSemesterMark();
            // If any required selection is null, display "N/A" and return
            labelAcademicYear.setText("N/A");
            labelCourse.setText("N/A");
            labelSemester.setText("N/A");
            labelAttendance.setText("N/A");
            labelMidterm.setText("N/A");
            labelFinal.setText("N/A");
            labelQuiz.setText("N/A");
            labelTotalPerCourse.setText("N/A");
            labelGradePerCourse.setText("N/A");
            return;
        }

        labelAcademicYear.setText(selectedAcademicYear.getAcademicYear());
        labelCourse.setText(selectedCourse.getNameCourse());
        labelSemester.setText(selectedSemester.getNameSemester());

        Float attendanceSemester = DAOFactory.getGradeDAO().getGrade(selectedStudent.getFullName(), selectedAcademicYear.getAcademicYear(), selectedCourse.getNameCourse(), selectedSemester.getNameSemester(), "Attendance");
        Float midtermSemester = DAOFactory.getGradeDAO().getGrade(selectedStudent.getFullName(), selectedAcademicYear.getAcademicYear(), selectedCourse.getNameCourse(), selectedSemester.getNameSemester(), "Midterm");
        Float finalSemester = DAOFactory.getGradeDAO().getGrade(selectedStudent.getFullName(), selectedAcademicYear.getAcademicYear(), selectedCourse.getNameCourse(), selectedSemester.getNameSemester(), "Final");
        Float quiz1Semester = DAOFactory.getGradeDAO().getGrade(selectedStudent.getFullName(), selectedAcademicYear.getAcademicYear(), selectedCourse.getNameCourse(), selectedSemester.getNameSemester(), "quiz1");
        Float  quiz2Semester = DAOFactory.getGradeDAO().getGrade(selectedStudent.getFullName(), selectedAcademicYear.getAcademicYear(), selectedCourse.getNameCourse(), selectedSemester.getNameSemester(), "quiz2");
        Float quiz3Semester = DAOFactory.getGradeDAO().getGrade(selectedStudent.getFullName(), selectedAcademicYear.getAcademicYear(), selectedCourse.getNameCourse(), selectedSemester.getNameSemester(), "quiz3");
        Float quiz4Semester = DAOFactory.getGradeDAO().getGrade(selectedStudent.getFullName(), selectedAcademicYear.getAcademicYear(), selectedCourse.getNameCourse(), selectedSemester.getNameSemester(), "quiz4");
        Float  quiz5Semester = DAOFactory.getGradeDAO().getGrade(selectedStudent.getFullName(), selectedAcademicYear.getAcademicYear(), selectedCourse.getNameCourse(), selectedSemester.getNameSemester(), "quiz5");

        // Calculate weighted averages
        attendanceSemester = attendanceSemester != null ? attendanceSemester : 0f;
        Float weightedAttendance = (attendanceSemester) * (100f / 30f);
        midtermSemester = midtermSemester != null ? midtermSemester : 0f;
        finalSemester = finalSemester != null ? finalSemester : 0f;
        quiz1Semester = quiz1Semester != null ? quiz1Semester : 0f;
        quiz2Semester = quiz2Semester != null ? quiz2Semester : 0f;
        quiz3Semester = quiz3Semester != null ? quiz3Semester : 0f;
        quiz4Semester = quiz4Semester != null ? quiz4Semester : 0f;
        quiz5Semester = quiz5Semester != null ? quiz5Semester : 0f;
        Float weightedQuiz = (quiz1Semester + quiz2Semester + quiz3Semester + quiz4Semester + quiz5Semester)*(100f / 500f);

        Float totalYearPerCourse =  weightedAttendance *0.1f + weightedQuiz*0.1f + midtermSemester*0.3f + finalSemester*0.5f;
            labelTotalPerCourse.setText(String.format("%.2f/100", totalYearPerCourse));
            String letterGrade = calculateLetterGrade(totalYearPerCourse);
            labelGradePerCourse.setText(letterGrade);

        // Display calculated grades
        labelAttendance.setText(String.format("%.2f/100", weightedAttendance));
        labelMidterm.setText(String.format("%.2f/100", midtermSemester));
        labelFinal.setText(String.format("%.2f/100", finalSemester));
        labelQuiz.setText(String.format("%.2f/100", weightedQuiz));
        labelTotal.setText("N/A");
        labelGrade.setText("N/A");
        labelAverageCourse.setText("Average:");
        labelIDandName.setText(selectedStudent.getIdStudent().toString() + " - " + selectedStudent.getFullName());
    }

    private void calculateTotalGrade(){
        Student selectedStudent = comboboxStudentName.getValue();
        Inscription selectedAcademicYear = comboboxYear.getValue();

        // Fetch grades and credits for each course
        List<CourGrade> courseGrades = DAOFactory.getStudentDAO().getGradesAndCreditsForStudent(selectedStudent.getFullName(), selectedAcademicYear.getAcademicYear());
        float totalWeightedGrades1st = 0;
        int totalCredits1st = 0;
        float totalWeightedGrades2nd = 0;
        int totalCredits2nd = 0;
        float totalWeightedGrades = 0;
        int totalCredits = 0;

        for (CourGrade courseGrade : courseGrades) {
            totalWeightedGrades1st += courseGrade.getGrade1ST() * courseGrade.getCredits1ST();
            totalCredits1st += courseGrade.getCredits1ST();

            totalWeightedGrades2nd += courseGrade.getGrade2ND() * courseGrade.getCredits2ND();
            totalCredits2nd += courseGrade.getCredits2ND();
        }
        totalCredits = totalCredits1st + totalCredits2nd;
        totalWeightedGrades = totalWeightedGrades1st + totalWeightedGrades2nd;

        float totalGrade = totalWeightedGrades / totalCredits;

        labelTotal.setText(String.format("%.2f/100", totalGrade));
        labelAcademicYear.setText(selectedAcademicYear.getAcademicYear());
        labelSemester.setText("N/A");
        labelCourse.setText("Total Year");
        labelAttendance.setText("10% of Total");
        labelQuiz.setText("10% of Total");
        labelMidterm.setText("30% of Total");
        labelFinal.setText("50% of Total");
        labelTotalPerCourse.setText(String.valueOf(totalCredits));
        labelAverageCourse.setText("Total Credits:");
        labelIDandName.setText(selectedStudent.getIdStudent() + " - " + selectedStudent.getFullName());
        comboboxSemester.getItems().clear();
        comboboxCourse.setItems(null);
        clearFieldsSemesterMark();
        labelGradePerCourse.setText("");
        labelGradeCourse.setText("");
        String letterGrade = calculateLetterGrade(totalGrade);
        labelGrade.setText(letterGrade);
    }

    private String calculateLetterGrade(float totalGrade) {
        if (totalGrade >= 97) {
            return "A+";
        } else if (totalGrade >= 93) {
            return "A";
        } else if (totalGrade >= 90) {
            return "A-";
        } else if (totalGrade >= 87) {
            return "B+";
        } else if (totalGrade >= 83) {
            return "B";
        } else if (totalGrade >= 80) {
            return "B-";
        } else if (totalGrade >= 77) {
            return "C+";
        } else if (totalGrade >= 73) {
            return "C";
        } else if (totalGrade >= 70) {
            return "C-";
        } else if (totalGrade >= 67) {
            return "D+";
        } else if (totalGrade >= 63) {
            return "D";
        } else if (totalGrade >= 60) {
            return "D-";
        } else {
            return "F";
        }
    }



    private void clearAll() {
        comboboxStudentName.setValue(null);
        fieldIDStudent.clear();
        comboboxYear.setValue(null);
        comboboxCourse.setValue(null);
        comboboxSemester.setValue(null);
        labelAcademicYear.setText("N/A");
        labelCourse.setText("N/A");
        labelSemester.setText("N/A");
        labelAttendance.setText("N/A");
        labelMidterm.setText("N/A");
        labelFinal.setText("N/A");
        labelQuiz.setText("N/A");
        labelTotalPerCourse.setText("N/A");
        labelTotal.setText("N/A");
        labelGrade.setText("N/A");
        labelAverageCourse.setText("Average:");
        labelIDandName.setText("");
        clearFieldsSemesterMark();
        labelGradePerCourse.setText("N/A");
        labelGradeCourse.setText("Grade:");
    }

    private void clearFieldsSemesterMark(){
        fieldAttendance.clear();
        fieldMidterm.clear();
        fieldFinal.clear();
        fieldQuiz1.clear();
        fieldQuiz2.clear();
        fieldQuiz3.clear();
        fieldQuiz4.clear();
        fieldQuiz4.clear();
        fieldQuiz5.clear();
    }
}
