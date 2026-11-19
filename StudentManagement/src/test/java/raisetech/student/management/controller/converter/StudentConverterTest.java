package raisetech.student.management.controller.converter;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;

/*
メモ　コンバーターがやっていること
・「studentのstudentId」と「studentCourseのstudentId」が一致するものだけを「studentDetailのstudentCourseList」に入れること
・1人の受講生に複数のコースが紐づくときはすべてリストに入れる
・別の受講生のコースが紐づかないようにすること

準備すること
・受講生A(ID:1)　コース1つ
・受講生B(ID:2)　コース2つ
・受講生C(ID:3)　コース0
・受講生D(ID:4)　ダミーコース（紐づくはずがないもの）
 */

class StudentConverterTest {

  private StudentConverter sut;

  @BeforeEach
  void before() {
    sut = new StudentConverter();
  }

  @Test
  void 受講生リストと受講生コースリストのデータから受講生詳細リストが作成できること() {
    //受講生A
    Student studentA = new Student();
    studentA.setStudentId("1");
    studentA.setFullName("StudentA");
    //コース1つ
    StudentCourse courseA1 = new StudentCourse();
    courseA1.setStudentId("1");
    courseA1.setCourseName("Java course");

    //受講生B
    Student studentB = new Student();
    studentB.setStudentId("2");
    studentB.setFullName("StudentB");
    //コース2つ
    StudentCourse courseB1 = new StudentCourse();
    courseB1.setStudentId("2");
    courseB1.setCourseName("python course");

    StudentCourse courseB2 = new StudentCourse();
    courseB2.setStudentId("2");
    courseB2.setCourseName("AWS course");

    //受講生C
    Student studentC = new Student();
    studentC.setStudentId("3");
    studentC.setFullName("StudentC");
    //コース0

    //受講生D
    Student studentD = new Student();
    studentD.setStudentId("4");
    studentD.setFullName("StudentD");
    //ダミーコース
    StudentCourse courseD1 = new StudentCourse();
    courseD1.setStudentId("4");
    courseD1.setCourseName("Dummy course");

    //StudentD以外の3人でリストを作る
    List<Student> studentList = List.of(studentA, studentB, studentC);

    //コースリストにはダミーを混ぜる
    List<StudentCourse> studentCourseList = List.of(courseA1, courseB1, courseB2, courseD1);

    //受講生とコースを紐づけ
    List<StudentDetail> actual = sut.convertStudentDetails(studentList, studentCourseList);

    assertEquals(3, actual.size());

    //受講生A
    assertEquals(studentA, actual.get(0).getStudent());
    assertEquals(List.of(courseA1), actual.get(0).getStudentCourseList());

    //受講生B
    assertEquals(studentB, actual.get(1).getStudent());
    assertEquals(List.of(courseB1, courseB2), actual.get(1).getStudentCourseList());

    //受講生C
    assertEquals(studentC, actual.get(2).getStudent());
    assertEquals(List.of(), actual.get(2).getStudentCourseList());
  }
}