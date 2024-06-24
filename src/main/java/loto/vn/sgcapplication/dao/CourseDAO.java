package loto.vn.sgcapplication.dao;

import loto.vn.sgcapplication.metier.Course;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CourseDAO extends DAO <Course, Course, Integer> {

    @Override
    public Course getByID(Integer integer) {
        return null;
    }

    @Override
    public ArrayList<Course> getAll() {
        ArrayList<Course> listCourse = new ArrayList<>();
        String sqlRequest = "SELECT * FROM COURSE";
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlRequest);
            while (resultSet.next()) {
                Integer idCourse = resultSet.getInt("ID_Course");
                String nameCourse = resultSet.getString("Name_Course");
                Course course = new Course(idCourse, nameCourse);
                listCourse.add(course);
            } resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listCourse;
    }


    @Override
    public ArrayList<Course> getLike(Course object) {
        return null;
    }

    @Override
    public boolean insert(Course object) {
        String sqlRequest = "INSERT INTO COURSE(Name_Course, Credits) VALUES (?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            preparedStatement.setString(1, object.getNameCourse());
            preparedStatement.setInt(2, object.getCredits());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Course object) {
        return false;
    }

    @Override
    public boolean delete(Course object) {
        return false;
    }
}
