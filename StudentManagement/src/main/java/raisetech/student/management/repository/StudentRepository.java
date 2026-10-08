package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentsCourses;


@Mapper
public interface StudentRepository {

  //受講生一覧取得
  @Select("SELECT * FROM students WHERE isDeleted = false")
  List<Student> search();

  //受講生1件の情報取得
  @Select("SELECT * FROM students WHERE studentId = #{studentId}")
  Student searchStudent(String studentId);

  //受講生コース一覧取得
  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> searchStudentsCourses();

  //指定したIDの受講生のコース取得
  @Select("SELECT * FROM students_courses WHERE studentId = #{studentId}")
  List<StudentsCourses> searchStudentsCoursesByStudentId(String studentId);

  //受講生登録
  @Insert(
      "INSERT INTO students(fullName, furigana, nickname, emailAddress, cityAddress, age, gender, remark, isDeleted) "
          +
          "VALUES(#{fullName}, #{furigana}, #{nickname}, #{emailAddress}, #{cityAddress}, #{age}, #{gender}, #{remark}, false)")
  @Options(useGeneratedKeys = true, keyProperty = "studentId")
  void registerStudent(Student student);

  //コース登録
  @Insert("INSERT INTO students_courses(courseName, studentId, startDate, completionDate)" +
      "VALUES(#{courseName}, #{studentId}, #{startDate}, #{completionDate})")
  @Options(useGeneratedKeys = true, keyProperty = "courseId")
  void registerStudentsCourses(StudentsCourses studentsCourses);


  //受講生情報更新
  @Update(
      "UPDATE students SET fullName = #{fullName}, furigana = #{furigana}, nickname = #{nickname}, "
          + "emailAddress = #{emailAddress}, cityAddress = #{cityAddress}, age = #{age},"
          + " gender = #{gender}, remark = #{remark}, isDeleted = #{isDeleted} WHERE studentId = #{studentId}")
  void updateStudent(Student student);

  //受講生コース更新
  @Update(
      "UPDATE students_courses SET "
          + "courseName = #{courseName} WHERE courseId = #{courseId}")
  void updateStudentsCourses(StudentsCourses studentsCourses);
}

