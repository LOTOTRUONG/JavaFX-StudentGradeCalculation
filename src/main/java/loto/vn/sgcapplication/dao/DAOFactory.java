package loto.vn.sgcapplication.dao;

public class DAOFactory {
    public static StudentDAO getStudentDAO() {return new StudentDAO();}

    public static CourseDAO getCourseDAO() {return new CourseDAO();}
    public static SemesterDAO getSemesterDAO() {return new SemesterDAO();}

    public static GradeDAO getGradeDAO() {return new GradeDAO();}

    public static InscriptionDAO getInscriptionDAO() {return new InscriptionDAO();}
}
