package loto.vn.sgcapplication.metier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Semester {
    private Integer idSemester;
    private String nameSemester;

    public Semester(String nameSemester){
        this.nameSemester = nameSemester;
    }
    public Semester(Integer idSemester){
        this.idSemester = idSemester;
    }


    @Override
    public String toString() {
        return nameSemester;
    }
}
