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

  //第16回演習課題　受講生一覧から名前をクリックしたら紐づく情報を表示させる
  //受講生情報を取得
  @Select("SELECT * FROM students WHERE studentId = #{studentId}")
  Student searchStudent(String studentId);

  //IDに紐づいたコース情報を取得
  @Select("SELECT * FROM students_courses WHERE studentId = #{studentId}")
  List<StudentsCourses> searchStudentsCoursesByStudentId(String studentId);
}

