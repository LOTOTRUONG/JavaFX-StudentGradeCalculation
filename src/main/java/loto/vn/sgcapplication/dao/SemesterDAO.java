package loto.vn.sgcapplication.dao;

import loto.vn.sgcapplication.metier.Course;
import loto.vn.sgcapplication.metier.Semester;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class SemesterDAO extends DAO<Semester, Semester, Integer> {
    @Override
    public Semester getByID(Integer integer) {
        return null;
    }

    @Override
    public ArrayList<Semester> getAll() {
        ArrayList<Semester> listSemester = new ArrayList<>();
        String sqlRequest = "SELECT * FROM SEMESTER";
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlRequest);
            while (resultSet.next()) {
                listSemester.add(new Semester(resultSet.getInt(1),resultSet.getString(2)));
            } resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSemester;
    }

    @Override
    public ArrayList<Semester> getLike(Semester object) {
        return null;
    }

    @Override
    public boolean insert(Semester object) {
        return false;
    }

    @Override
    public boolean update(Semester object) {
        return false;
    }

    @Override
    public boolean delete(Semester object) {
        return false;
    }
}
