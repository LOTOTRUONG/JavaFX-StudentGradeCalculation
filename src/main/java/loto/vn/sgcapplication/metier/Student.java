package loto.vn.sgcapplication.metier;

import javafx.beans.property.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Student {
    private IntegerProperty idStudent;
    private StringProperty lastName;
    private StringProperty firstName;
    private StringProperty fullName;
    private Date DOB;
    private StringProperty email;
    private IntegerProperty idInscription;
    private StringProperty academicYear;
    private Date dateInscription;
    private StringProperty nameSemester;
    private StringProperty nameCourse;
    private IntegerProperty credits;
    private StringProperty nameCategory;
    private FloatProperty grade;

    public Student(Integer idStudent, String lastName, String firstName, String fullName,  Date DOB, String email,
                   Integer idInscription, String academicYear, Date dateInscription, String nameSemester, String nameCourse,
                   Integer credits, String nameCategory, Float grade) {
        this.idStudent = new SimpleIntegerProperty(idStudent);
        this.lastName = new SimpleStringProperty(lastName);
        this.firstName = new SimpleStringProperty(firstName);
        this.fullName = new SimpleStringProperty(fullName);
        this.DOB = DOB;
        this.email = new SimpleStringProperty(email);
        this.idInscription = new SimpleIntegerProperty(idInscription);
        this.academicYear = new SimpleStringProperty(academicYear);
        this.dateInscription = dateInscription;
        this.nameSemester = new SimpleStringProperty(nameSemester);
        this.nameCourse = new SimpleStringProperty(nameCourse);
        this.credits = new SimpleIntegerProperty(credits);
        this.nameCategory = new SimpleStringProperty(nameCategory);
        this.grade = new SimpleFloatProperty(grade);
    }

    public Student (String nameCourse){
        this.nameCourse = new SimpleStringProperty(nameCourse);
    }


    @Override
    public String toString() {
        return fullName.get();
    }
}
