package loto.vn.sgcapplication.metier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourGrade {
    private Integer idStudent;
    private String fullName;
    private String courseName;
    private Float grade1ST;
    private Integer credits1ST;
    private Float grade2ND;
    private Integer credits2ND;

    public CourGrade(String courseName, Float grade1ST, Integer credits1ST, Float grade2ND, Integer credits2ND) {
        this.courseName = courseName;
        this.grade1ST = grade1ST;
        this.credits1ST = credits1ST;
        this.grade2ND = grade2ND;
        this.credits2ND = credits2ND;
    }
}
