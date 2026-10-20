package raisetech.student.management.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentCourse {

    //コースIDは自動採番
    private String courseId;

    @NotBlank(message = "Course name is required.")
    @Size(max = 100, message = "Please enter a course name of 100 characters or fewer.")
    private String courseName;

    private String studentId;
    private LocalDate startDate;
    private LocalDate completionDate;
}
