package loto.vn.sgcapplication.metier;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    private Integer idCourse;
    private String nameCourse;
    private String description;
    private Integer credits;

    public Course(Integer idCourse, String nameCourse, Integer credits){
        this.idCourse = idCourse;
        this.nameCourse = nameCourse;
        this.credits = credits;
    }
    public Course(String nameCourse){
        this.nameCourse = nameCourse;
    }

    public Course(Integer idCourse){
        this.idCourse = idCourse;
    }

    public Course(Integer idCourse, String nameCourse){
        this.idCourse = idCourse;
        this.nameCourse = nameCourse;
    }
    @Override
    public String toString() {
        return nameCourse;
    }
}
