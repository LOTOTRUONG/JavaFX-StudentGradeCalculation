package loto.vn.sgcapplication.metier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {
    private Integer idTeacher;
    private String lastName;
    private String firstName;
    private String email;
}
