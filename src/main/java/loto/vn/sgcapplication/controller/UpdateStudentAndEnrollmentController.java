package loto.vn.sgcapplication.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import loto.vn.sgcapplication.dao.DAOFactory;
import loto.vn.sgcapplication.enums.Gender;
import loto.vn.sgcapplication.interfaces.StudentUpdateCallback;
import loto.vn.sgcapplication.metier.*;
import org.controlsfx.control.SearchableComboBox;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class UpdateStudentAndEnrollmentController implements Initializable, StudentUpdateCallback {
    @FXML
    private Button btnAddNewStudent, btnModifyStudent, btnReset, btnEnrollment, btnCourseEnrollment, btnAddCourse;
    @FXML
    private TextField fieldAcademicYear, fieldDOB, fieldEmail, fieldFirstName, fieldIDStudent,fieldIDStudent2, fieldLastName, fieldIDStudent3, fieldCredits, fieldCourse;
    @FXML
    private SearchableComboBox<Student> searchBoxStudent,searchBoxStudent2, searchBoxStudent3;
    @FXML
    private ComboBox<Course> searchboxCourse;
    @FXML
    private ComboBox<String> searchboxSemester;
    @FXML
    private ComboBox<Gender> comboBoxGender;

    @FXML
    private ComboBox<String> comboBoxAcademicYear;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnAddNewStudent.setOnAction(event -> addNewStudent());
        btnModifyStudent.setOnAction(event -> modifyStudent());
        btnReset.setOnAction(event -> clearFields());
        btnEnrollment.setOnAction(event -> addNewEnrollment());
        btnCourseEnrollment.setOnAction(event -> addCourseInscriptionForStudent());
        btnAddCourse.setOnAction(event -> addNewCourse());
        searchBoxStudent();
        setupSearchBoxListener();
        populateGenderComboBox();
        setupCourseComboBox();
    }

    private void populateGenderComboBox() {
        ObservableList<Gender> genderOptions = FXCollections.observableArrayList(Gender.values());
        comboBoxGender.setItems(genderOptions);
        comboBoxGender.setValue(null);
        comboBoxGender.setPromptText("Select a gender");
    }

    private void addNewStudent() {
        String firstName = fieldFirstName.getText();
        String lastName = fieldLastName.getText();
        String dob = fieldDOB.getText();
        String email = fieldEmail.getText();
        String idStudent = fieldIDStudent.getText();
        Gender gender = comboBoxGender.getValue();

        if (firstName.isEmpty() || lastName.isEmpty() || dob.isEmpty() || email.isEmpty()) {
            showErrorAlert("Error", "All fields must be filled out.");
            return;
        }
        if (!idStudent.isEmpty()) {
            showErrorAlert("Error", "existed ID! Could not ask new student");
            return;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDOB;
        try {
            parsedDOB = dateFormat.parse(dob);
        } catch (ParseException e) {
            showErrorAlert("Error", "Date of Birth must be in the format yyyy-MM-dd.");
            return;
        }

        Student newStudent = new Student();
        String capitalizedFirstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase();
        newStudent.setFirstName(capitalizedFirstName);
        String capitalizedLastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase();
        newStudent.setLastName(capitalizedLastName);
        newStudent.setDOB(parsedDOB);
        String formatEmail = email.toLowerCase();
        newStudent.setEmail(formatEmail);
        newStudent.setGender(gender);

        boolean success = DAOFactory.getStudentDAO().insert(newStudent);

        if (success) {
            showInfoAlert("Success", "Student added successfully.");
            onStudentUpdated();
            clearFields();
        } else {
            showErrorAlert("Error", "Failed to add student.");
        }
    }

    private void modifyStudent() {
        String firstName = fieldFirstName.getText();
        String lastName = fieldLastName.getText();
        String dob = fieldDOB.getText();
        String email = fieldEmail.getText();
        String idStudent = fieldIDStudent.getText();
        Gender gender = comboBoxGender.getValue();

        if (firstName.isEmpty() || lastName.isEmpty() || dob.isEmpty() || email.isEmpty()) {
            showErrorAlert("Error", "All fields must be filled out.");
            return;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDOB;
        try {
            parsedDOB = dateFormat.parse(dob);
        } catch (ParseException e) {
            showErrorAlert("Error", "Date of Birth must be in the format yyyy-MM-dd.");
            return;
        }

        Student newStudent = new Student();
        String capitalizedFirstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase();
        newStudent.setFirstName(capitalizedFirstName);
        String capitalizedLastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase();
        newStudent.setLastName(capitalizedLastName);
        newStudent.setDOB(parsedDOB);
        String formatEmail = email.toLowerCase();
        newStudent.setEmail(formatEmail);
        newStudent.setIdStudent(Integer.parseInt(idStudent));
        newStudent.setGender(gender);

        boolean success = DAOFactory.getStudentDAO().update(newStudent);

        if (success) {
            showInfoAlert("Success", "Student edited successfully.");
            onStudentUpdated();
            clearFields();
        } else {
            showErrorAlert("Error", "Failed to edit student.");
        }
    }
    public void searchBoxStudent(){
        List<Student> students = DAOFactory.getStudentDAO().getAll();
        Map<String, Student> uniqueFullNames = new LinkedHashMap<>();

        for (Student student : students) {
            String fullName = student.getFullName();
            if (!uniqueFullNames.containsKey(fullName)) {
                uniqueFullNames.put(fullName, student);
            }
        }
        Student placeholderStudent = new Student();
        placeholderStudent.setFirstName("Select");
        placeholderStudent.setLastName("a student");

        ObservableList<Student> nameStudents = FXCollections.observableArrayList(uniqueFullNames.values());
        nameStudents.add(0, placeholderStudent);
        searchBoxStudent.setItems(nameStudents);
        searchBoxStudent.setPromptText("Select a student");
        searchBoxStudent2.setItems(nameStudents);
        searchBoxStudent2.setPromptText("Select a student");
        searchBoxStudent3.setItems(nameStudents);
        searchBoxStudent3.setPromptText("Select a student");

    }

    private void setupCourseComboBox(){
        List<Course> courses = DAOFactory.getCourseDAO().getAll();
        ObservableList<Course> listCourse= FXCollections.observableArrayList(courses);
        searchboxCourse.setItems(listCourse);
        searchboxCourse.setPromptText("Select a course");
    }
    private void setupSearchBoxListener() {
        searchBoxStudent.setOnAction(event -> {
            Student selectedStudent = searchBoxStudent.getValue();
            if (selectedStudent != null && !selectedStudent.getFirstName().equals("Select")) {
                fieldLastName.setText(selectedStudent.getLastName());
                fieldFirstName.setText(selectedStudent.getFirstName());
                fieldDOB.setText(selectedStudent.getDOB().toString());
                fieldEmail.setText(selectedStudent.getEmail());
                fieldIDStudent.setText(selectedStudent.getIdStudent().toString());
                comboBoxGender.setValue(selectedStudent.getGender());
            }
        });
        searchBoxStudent2.setOnAction(event ->{
            Student selectedStudent2 = searchBoxStudent2.getValue();
            if (selectedStudent2 != null && !selectedStudent2.getFirstName().equals("Select")) {
                fieldIDStudent2.setText(selectedStudent2.getIdStudent().toString());
            }
        });

        searchBoxStudent3.setOnAction(event -> {
            Student selectedStudent3 = searchBoxStudent3.getValue();
            if (selectedStudent3 != null && !selectedStudent3.getFirstName().equals("Select")) {
                fieldIDStudent3.setText(selectedStudent3.getIdStudent().toString());
                chooseAcademicYearComboBox(selectedStudent3);

                comboBoxAcademicYear.setOnAction(event1 -> {
                    String selectedYear = comboBoxAcademicYear.getValue();
                    chooseSemesterComboBox(selectedStudent3, selectedYear);
                });

            }
        });
    }

    private void addNewEnrollment() {
        try {
            Integer idStudent = Integer.parseInt(fieldIDStudent2.getText());
            String academicYear = fieldAcademicYear.getText();

            if (idStudent == null || academicYear.isEmpty()) {
                showErrorAlert("Error", "All fields must be filled out.");
                return;
            }

            Student student = DAOFactory.getStudentDAO().getByID(idStudent); // Replace with appropriate method to retrieve student by ID
            if (student == null) {
                showErrorAlert("Error", "Student with ID " + idStudent + " not found.");
                return;
            }

            Inscription newInscriptionSemester1 = new Inscription();
            newInscriptionSemester1.setStudent(student);
            newInscriptionSemester1.setAcademicYear(academicYear);
            newInscriptionSemester1.setSemester(new Semester(1)); // Semester 1

            Inscription newInscriptionSemester2 = new Inscription();
            newInscriptionSemester2.setStudent(student);
            newInscriptionSemester2.setAcademicYear(academicYear);
            newInscriptionSemester2.setSemester(new Semester(2)); // Semester 2

            boolean successSemester1 = DAOFactory.getInscriptionDAO().insert(newInscriptionSemester1);
            boolean successSemester2 = DAOFactory.getInscriptionDAO().insert(newInscriptionSemester2);

            if (successSemester1 && successSemester2) {
                showInfoAlert("Success", "Inscription added successfully for both semesters.");
                onStudentUpdated();
                clearFields();
            } else if (!successSemester1 && !successSemester2) {
                showErrorAlert("Error", "Failed to add new inscription for both semesters.");
            } else if (!successSemester1) {
                showErrorAlert("Error", "Failed to add new inscription for Semester 1.");
            } else {
                showErrorAlert("Error", "Failed to add new inscription for Semester 2.");
            }
        } catch (NumberFormatException e) {
            showErrorAlert("Error", "Invalid student ID.");
        }
    }

    private void addCourseInscriptionForStudent() {
        try {
            Integer idStudent = Integer.parseInt(fieldIDStudent3.getText());
            String academicYear = comboBoxAcademicYear.getValue();
            String semester = searchboxSemester.getValue();
            Course selectedCourse = searchboxCourse.getValue();

            if (idStudent == null || academicYear.isEmpty() || academicYear.isEmpty() || selectedCourse == null) {
                showErrorAlert("Error", "All fields must be filled out.");
                return;
            }

            Student student = DAOFactory.getStudentDAO().getByID(idStudent);
            if (student == null) {
                showErrorAlert("Error", "Student with ID " + idStudent + " not found.");
                return;
            }
            if (selectedCourse.getIdCourse() == null) {
                showErrorAlert("Error", "Selected course does not have a valid ID.");
                return;
            }

            Integer idInscription = DAOFactory.getInscriptionDAO().getInscriptionId(student.getIdStudent(), academicYear, semester);
            if (idInscription == null || idInscription == 0 ) {
                throw new IllegalArgumentException("No inscription found for the given student, academic year, and semester.");
            }
            boolean allSuccess = true;
            for (int i = 1; i <= 8; i++) {
                Grade newGrade = new Grade();
                newGrade.setInscription(new Inscription(idInscription));
                newGrade.setCourse(selectedCourse);
                newGrade.setGradeCategory(new GradeCategory(i));

                boolean success = DAOFactory.getGradeDAO().insert(newGrade);
                if (!success) {
                    allSuccess = false;
                }
            }
            if (allSuccess) {
                showInfoAlert("Success", "Course added successfully for this inscription.");
                onStudentUpdated();
                clearFields();
            } else {
                showErrorAlert("Error", "Failed to add new course for this inscription.");
            }
        } catch (NumberFormatException e) {
            showErrorAlert("Error", "Something wrong");
        }
    }

    private void addNewCourse(){
        String nameCourse = fieldCourse.getText();
        Integer credit = Integer.valueOf(fieldCredits.getText());
        Course newCourse = new Course();
        newCourse.setNameCourse(nameCourse);
        newCourse.setCredits(credit);
        boolean success = DAOFactory.getCourseDAO().insert(newCourse);
        if (success){
            showInfoAlert("Success", "Course added successfully");
            onStudentUpdated();
            clearFields();
        }
        else
            showErrorAlert("Error", "Failed to add new course");
    }

    private void chooseAcademicYearComboBox(Student student){
        List<String> inscriptions = DAOFactory.getInscriptionDAO().getAcademicYearsForStudent(student.getIdStudent());
        inscriptions.add(0, null);
        ObservableList<String> inscriptionObservableList = FXCollections.observableArrayList(inscriptions);
        comboBoxAcademicYear.setItems(inscriptionObservableList);
        comboBoxAcademicYear.setPromptText("Select an academic year");
    }
    private void chooseSemesterComboBox(Student student, String academicYear){
        List<String> semesters = DAOFactory.getInscriptionDAO().getSemesterByAcademicYearsForStudent(student.getIdStudent(), academicYear);
        semesters.add(0, null);
        ObservableList<String> semesterObservableList = FXCollections.observableArrayList(semesters);
        searchboxSemester.setItems(semesterObservableList);
        searchboxSemester.setPromptText("Select a semester");

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

    private void clearFields() {
        fieldFirstName.clear();
        fieldLastName.clear();
        fieldDOB.clear();
        fieldEmail.clear();
        fieldIDStudent.clear();
        searchBoxStudent.getSelectionModel().select(0);
        searchBoxStudent2.getSelectionModel().select(0);
        fieldIDStudent2.clear();
        fieldAcademicYear.clear();
        searchBoxStudent3.getSelectionModel().select(0);
        comboBoxGender.setValue(null);
        fieldIDStudent3.clear();
        comboBoxAcademicYear.setValue(null);
        searchboxSemester.setValue(null);
        searchboxCourse.setValue(null);
        fieldCourse.clear();
        fieldCredits.clear();
    }


    @Override
    public void onStudentUpdated() {
        searchBoxStudent();
    }
}
