package loto.vn.sgcapplication.metier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inscription {
    private Integer idInscription;
    private Student student;
    private String academicYear;
    private Date dateInscription;
    private String note;
    private Semester semester;

    public Inscription(String academicYear){
        this.academicYear = academicYear;
    }

    public Inscription(Student student, String academicYear){
        this.student = student;
        this.academicYear = academicYear;
    }

    public Inscription(Integer idInscription, Student student, String academicYear, Semester semester){
        this.idInscription = idInscription;
        this.student = student;
        this.academicYear = academicYear;
        this.semester = semester;
    }

    public Inscription(Integer idInscription){
        this.idInscription = idInscription;
    }
    @Override
    public String toString() {
        return academicYear;
    }
}
