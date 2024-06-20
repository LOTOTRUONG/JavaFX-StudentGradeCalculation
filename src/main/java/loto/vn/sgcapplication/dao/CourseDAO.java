package loto.vn.sgcapplication.dao;

import loto.vn.sgcapplication.metier.Course;

import java.sql.ResultSet;
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
        String sqlRequest = "SELECT ID_Course, Name_Course FROM COURSE";
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlRequest);
            while (resultSet.next()) {
                listCourse.add(new Course(resultSet.getInt(1),resultSet.getString(2)));
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
        return false;
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
