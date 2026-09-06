package raisetech.student.management;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

@Getter
@Setter
public class StudentsCourses {

    private String courseId;
    private String studentId;
    private LocalDate startDate;
    private LocalDate completionDate;
}
