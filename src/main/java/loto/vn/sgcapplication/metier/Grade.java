package loto.vn.sgcapplication.metier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Grade {
    private Integer idGrade;
    private Float grade;
    private Inscription idInscription;
    private GradeCategory idCategory;
}
