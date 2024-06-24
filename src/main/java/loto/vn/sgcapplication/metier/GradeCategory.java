package loto.vn.sgcapplication.metier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GradeCategory {
    private Integer idCategory;
    private String nameCategory;
    private String description;

    public GradeCategory(Integer idCategory){
        this.idCategory = idCategory;
    }
}
