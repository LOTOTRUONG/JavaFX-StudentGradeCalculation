package loto.vn.sgcapplication.dao;

import loto.vn.sgcapplication.enums.Gender;
import loto.vn.sgcapplication.metier.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StudentDAO extends DAO<Student, Student, Integer> {

    @Override
    public Student getByID(Integer id) {
        String sqlRequest = "Select * from STUDENT where ID_Student = " + id;
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlRequest);
            if(resultSet.next()) return new Student(resultSet.getInt(1),
                                                    resultSet.getString(2),
                                                    resultSet.getString(3),
                                                    resultSet.getString(4));
            return null;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public ArrayList<Student> getAll() {
        ArrayList<Student> listStudent = new ArrayList<Student>();
        String sqlRequest = "{call SearchStudentGrades}";
        try(CallableStatement callableStatement = connection.prepareCall(sqlRequest)) {
            ResultSet resultSet = callableStatement.executeQuery();
            while(resultSet.next()) {
                String genderStr = resultSet.getString("Gender");
                Gender gender = null;
                if (genderStr != null && !genderStr.isEmpty()) {
                    gender = Gender.fromChar(genderStr.charAt(0));
                }

                listStudent.add(new Student(resultSet.getInt("ID_STUDENT"),
                        resultSet.getString("Last_name"),
                        resultSet.getString("First_name"),
                        resultSet.getString("Full_Name"),
                        gender,
                        resultSet.getDate("DOB"),
                        resultSet.getString("Email"),
                        resultSet.getInt("ID_Inscription"),
                        resultSet.getString("Academic_Year"),
                        resultSet.getDate("Date_Inscription"),
                        resultSet.getString("Name_Semester"),
                        resultSet.getString("Name_Course"),
                        resultSet.getInt("Credits"),
                        resultSet.getString("Name_category"),
                        resultSet.getFloat("Grade")));
            }
            return listStudent;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listStudent;
    }

    @Override
    public ArrayList<Student> getLike(Student object) {
        return null;
    }

    public List<Inscription> getAcademicYearByStudentFullName(String studentFullName) {
        List<Inscription> inscriptions = new ArrayList<>();
        String sql = "{call SearchStudentGrades(?)}";
        try (CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.setString(1, studentFullName);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String academicYear = rs.getString("Academic_Year");
                Inscription inscription = new Inscription(academicYear);
                inscriptions.add(inscription);
            }
        } catch (Exception e){
            e.printStackTrace();}
        return inscriptions;
    }

    public List<Course> getCoursesByStudentFullNameAndAcademicYear(String studentFullName, String academicYear) {
        List<Course> courses = new ArrayList<>();
            String sql = "{call SearchStudentGrades(?,?)}";
            try (CallableStatement stmt = connection.prepareCall(sql)) {
                stmt.setString(1, studentFullName);
                stmt.setString(2, academicYear);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String courseName = rs.getString("Name_Course");
                    Course course = new Course(courseName);
                    courses.add(course);
                }
            } catch (Exception e){
                e.printStackTrace();}
        return courses;
    }
    public List<Semester> getSemesterByStudentFullNameAndCourse(String studentFullName, String academicYear, String courseName) {
        List<Semester> semesters = new ArrayList<>();
        String sql = "{call SearchStudentGrades(?,?,?)}";
        try (CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.setString(1, studentFullName);
            stmt.setString(2, academicYear);
            stmt.setString(3, courseName);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String semesterName = rs.getString("Name_Semester");
                Semester semester = new Semester(semesterName);
                semesters.add(semester);
            }
        } catch (Exception e){
            e.printStackTrace();}
        return semesters;
    }


    public List<CourGrade> getGradesAndCreditsForStudent(String studentFullName, String academicYear){
        List<CourGrade> courseGrades = new ArrayList<>();
        String sql = "{call CalculateGradePerCourse(?,?)}";

        try (CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.setString(1, studentFullName);
            stmt.setString(2, academicYear);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String courseName = rs.getString("Name_Course");
                float grade1ST = rs.getFloat("Semester1ST");
                int credit1ST = rs.getInt("Credits1ST");
                float grade2ND = rs.getFloat("Semester2ND");
                int credit2ND = rs.getInt("Credits2ND");

                courseGrades.add(new CourGrade(courseName, grade1ST, credit1ST, grade2ND, credit2ND));
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return courseGrades;
    }

    @Override
    public boolean insert(Student object) {
        String sqlRequest = "INSERT INTO STUDENT (Last_name, First_name, Gender, DOB, Email) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            preparedStatement.setString(1, object.getLastName());
            preparedStatement.setString(2, object.getFirstName());
            preparedStatement.setString(3, String.valueOf(object.getGender().getValue()));
            preparedStatement.setDate(4, new Date(object.getDOB().getTime()));
            preparedStatement.setString(5, object.getEmail());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Student object) {
        String sqlRequest = "UPDATE STUDENT SET Last_name = ?, First_name = ?, Gender = ?, DOB = ?, Email = ? WHERE ID_Student = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest,Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, object.getLastName());
            preparedStatement.setString(2, object.getFirstName());
            preparedStatement.setString(3, String.valueOf(object.getGender().getValue()));
            preparedStatement.setDate(4, new Date(object.getDOB().getTime()));
            preparedStatement.setString(5, object.getEmail());
            preparedStatement.setInt(6, object.getIdStudent());
            preparedStatement.executeUpdate();
            return true;
        }catch (SQLException E) {
            E.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Student object) {
        String sqlRequest = "Delete from STUDENT WHERE ID_Student = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            preparedStatement.setInt(1, Integer.valueOf(String.valueOf(object.getIdStudent())));
            preparedStatement.executeUpdate();
            return true;
        }catch (SQLException E) {
            return false;
        }
    }
}
