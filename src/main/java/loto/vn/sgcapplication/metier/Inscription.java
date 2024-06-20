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
    private Student idStudent;
    private Course idCourse;
    private String academicYear;
    private Date dateInscription;
    private String note;
    private Semester idSemester;
}
