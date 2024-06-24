package loto.vn.sgcapplication.dao;

import loto.vn.sgcapplication.metier.Grade;

import java.sql.*;
import java.util.ArrayList;

public class GradeDAO extends DAO<Grade, Grade, Integer> {
    @Override
    public Grade getByID(Integer integer) {
        return null;
    }

    @Override
    public ArrayList<Grade> getAll() {
        return null;
    }

    @Override
    public ArrayList<Grade> getLike(Grade object) {
        return null;
    }
    public Float getGrade(String studentFullName, String academicYear, String courseName, String semester, String nameCategory){
        Float grade = null;
        String sql = "{call SearchStudentGrades(?,?,?,?,?)}";
        try (CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.setString(1, studentFullName);
            stmt.setString(2, academicYear);
            stmt.setString(3, courseName);
            stmt.setString(4, semester);
            stmt.setString(5,nameCategory);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                grade = rs.getFloat("Grade");
            }
        } catch (Exception e){
            e.printStackTrace();}
        return grade;
    }

    @Override
    public boolean insert(Grade object) {
        String sqlRequest = "INSERT INTO GRADE (ID_Inscription, ID_Course, ID_Category) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            preparedStatement.setInt(1, object.getInscription().getIdInscription());
            preparedStatement.setInt(2, object.getCourse().getIdCourse());
            preparedStatement.setInt(3, object.getGradeCategory().getIdCategory());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Grade object) {
        return false;
    }

    public boolean updateGrade(String fullName, String academicYear, String courseName, String semesterName, String categoryName, Float grade) {
        String sqlUpdate = "{CALL UpdateGrade(?, ?, ?, ?, ?, ?)}";

        try (CallableStatement callableStatement = connection.prepareCall(sqlUpdate)) {

            callableStatement.setString(1, fullName);
            callableStatement.setString(2, academicYear);
            callableStatement.setString(3, courseName);
            callableStatement.setString(4, semesterName);
            callableStatement.setString(5, categoryName);
            if (grade != null) {
                callableStatement.setFloat(6, grade);
            } else {
                callableStatement.setNull(6, Types.FLOAT);
            }

            callableStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Grade object) {
        return false;
    }
}
