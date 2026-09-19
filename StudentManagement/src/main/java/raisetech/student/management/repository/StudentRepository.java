package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentsCourses;


@Mapper
public interface StudentRepository {

   @Select("SELECT * FROM students")
   List<Student> search();

   @Select("SELECT * FROM students_courses")
   List<StudentsCourses> searchStudentsCourses();

  @Insert("INSERT INTO students(fullName, furigana, nickname, emailAddress, cityAddress, age, gender, remark, isDeleted) " +
      "VALUES(#{fullName}, #{furigana}, #{nickname}, #{emailAddress}, #{cityAddress}, #{age}, #{gender}, #{remark}, false)")
  @Options(useGeneratedKeys = true, keyProperty = "studentId")
    void registerStudent(Student student);

  @Insert("INSERT INTO students_courses(courseName, studentId, startDate, completionDate)" +
          "VALUES(#{courseName}, #{studentId}, #{startDate}, #{completionDate})")
  @Options(useGeneratedKeys = true, keyProperty = "courseId")
    void registerStudentsCourses(StudentsCourses studentsCourses);
}

