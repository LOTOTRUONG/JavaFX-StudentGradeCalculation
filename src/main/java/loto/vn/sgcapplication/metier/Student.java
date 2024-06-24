package loto.vn.sgcapplication.metier;

import javafx.beans.property.*;
import lombok.*;
import loto.vn.sgcapplication.enums.Gender;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private Integer idStudent;
    private String lastName;
    private String firstName;
    private String fullName;
    private Gender gender;
    private Date DOB;
    private String email;
    private Integer idInscription;
    private String academicYear;
    private Date dateInscription;
    private String nameSemester;
    private String nameCourse;
    private Integer credits;
    private String nameCategory;
    private Float grade;

    public Student(Integer idStudent, String lastName, String firstName, String email){
        this.idStudent = idStudent;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
    }

    public Student (Integer idStudent, String lastName, String firstName, String fullName, Date DOB, String email, Integer idInscription, String academicYear, Date dateInscription,String nameSemester, String nameCourse, Integer credits, String nameCategory, Float grade){
        this.idStudent = idStudent;
        this.lastName = lastName;
        this.firstName = firstName;
        this.fullName = fullName;
        this.DOB = DOB;
        this.email = email;
        this.idInscription = idInscription;
        this.academicYear = academicYear;
        this.dateInscription = dateInscription;
        this.nameSemester = nameSemester;
        this.nameCourse = nameCourse;
        this.credits = credits;
        this.nameCategory = nameCategory;
        this.grade = grade;
    }
    public Student (String nameCourse){
        this.nameCourse = nameCourse;
    }
    public Student (Integer idStudent){
        this.idStudent = idStudent;
    }

    public Student (Integer idStudent, String lastName, String firstName){
        this.idStudent = idStudent;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public Student (String nameCourse, Float grade, Integer credits){
        this.nameCourse = nameCourse;
        this.grade = grade;
        this.credits = credits;
    }

    @Override
    public String toString() {
        return fullName;
    }

}
