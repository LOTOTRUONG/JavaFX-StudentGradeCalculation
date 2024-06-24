package loto.vn.sgcapplication.dao;

import loto.vn.sgcapplication.metier.Inscription;
import loto.vn.sgcapplication.metier.Semester;
import loto.vn.sgcapplication.metier.Student;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class InscriptionDAO extends DAO <Inscription, Inscription, Integer> {
    @Override
    public Inscription getByID(Integer integer) {
        return null;
    }

    public Integer getInscriptionId(Integer studentId, String academicYear, String semester) {
        String sqlRequest = "SELECT id_inscription FROM inscription WHERE id_student = ? AND academic_year = ? AND id_semester = (SELECT id_semester FROM semester WHERE name_semester = ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            preparedStatement.setInt(1, studentId);
            preparedStatement.setString(2, academicYear);
            preparedStatement.setString(3, semester);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id_inscription");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public ArrayList<Inscription> getAll() {
        return null;
    }

    public List<String> getAcademicYearsForStudent(Integer id) {
        List<String> academicYears = new ArrayList<>();
        String sql = "SELECT DISTINCT Academic_Year " +
                "FROM INSCRIPTION i " +
                "JOIN STUDENT s ON i.ID_Student = s.ID_Student " +
                "WHERE s.ID_Student = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String academicYear = resultSet.getString("Academic_Year");
                academicYears.add(academicYear);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return academicYears;
    }

    public List<String> getSemesterByAcademicYearsForStudent(Integer idStudent, String academicYear) {
        List<String> semesterList = new ArrayList<>();
        String sql = "SELECT DISTINCT sem.Name_Semester " +
                "FROM INSCRIPTION i " +
                "JOIN SEMESTER sem ON sem.ID_Semester = i.ID_Semester " +
                "WHERE i.ID_Student = ? AND i.Academic_Year = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idStudent);
            preparedStatement.setString(2, academicYear);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String semesterName = resultSet.getString("Name_Semester");
                semesterList.add(semesterName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return semesterList;
    }

    @Override
    public ArrayList<Inscription> getLike(Inscription object) {
        return null;
    }

    @Override
    public boolean insert(Inscription object) {
        String sqlRequest = "INSERT INTO INSCRIPTION (ID_Student, Academic_Year, Date_Inscription, ID_Semester) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            preparedStatement.setInt(1, object.getStudent().getIdStudent());
            preparedStatement.setString(2, object.getAcademicYear());
            LocalDate now = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            preparedStatement.setDate(3, Date.valueOf(now.format(formatter)));
            preparedStatement.setInt(4, object.getSemester().getIdSemester());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Inscription object) {
        return false;
    }

    @Override
    public boolean delete(Inscription object) {
        return false;
    }
}
