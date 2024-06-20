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

    @Override
    public String toString() {
        return nameSemester;
    }
}
