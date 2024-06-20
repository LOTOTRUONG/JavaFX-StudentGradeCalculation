package loto.vn.sgcapplication.dao;

import loto.vn.sgcapplication.metier.Course;
import loto.vn.sgcapplication.metier.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO extends DAO<Student, Student, Integer> {

    @Override
    public Student getByID(Integer id) {
        String sqlRequest = "{call SearchStudentGrades}";
        try(CallableStatement callableStatement = connection.prepareCall(sqlRequest)) {
            callableStatement.setInt(1, id);
            ResultSet resultSet = callableStatement.executeQuery();
            if(resultSet.next()) return new Student(resultSet.getInt(1),resultSet.getString(2), resultSet.getString(3),
                                                   resultSet.getString(4), resultSet.getDate(5), resultSet.getString(6),
                                                    resultSet.getInt(7), resultSet.getString(8),resultSet.getDate(9),
                                                    resultSet.getString(10), resultSet.getString(11), resultSet.getInt(12),
                                                    resultSet.getString(13), resultSet.getFloat(14));
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
                listStudent.add(new Student(resultSet.getInt(1),resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getDate(5), resultSet.getString(6),
                        resultSet.getInt(7), resultSet.getString(8),resultSet.getDate(9),
                        resultSet.getString(10), resultSet.getString(11), resultSet.getInt(12),
                        resultSet.getString(13), resultSet.getFloat(14)));
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

    public List<Course> getCoursesByStudentFullName(String studentFullName) {
        List<Course> courses = new ArrayList<>();
            String sql = "{call SearchStudentGrades(?)}";

            try (CallableStatement stmt = connection.prepareCall(sql)) {
                stmt.setString(1, studentFullName);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    String courseName = rs.getString("Name_Course");

                    Course course = new Course(courseName);

                    courses.add(course);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }

        return courses;
    }


    @Override
    public boolean insert(Student object) {
        String sqlRequest = "insert into STUDENT values " + object.getFirstName() + object.getLastName() + object.getDOB() + object.getEmail();
        try(Statement statement = connection.createStatement()) {
            statement.execute(sqlRequest);
            return true;
        }catch (SQLException E) {
            E.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Student object) {
        String sqlRequest = "update STUDENT set Last_name = ? WHERE ID_Student = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest,Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, String.valueOf(object.getLastName()));
            preparedStatement.setInt(2, Integer.valueOf(String.valueOf(object.getIdStudent())));
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
