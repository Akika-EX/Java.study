package raisetech.student.management.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository repository;

  @Mock
  private StudentConverter converter;

  private StudentService sut;

  @BeforeEach
  void before() {
    sut = new StudentService(repository, converter);
  }

  //テストコードは 準備（Arrange）→ 実行（Act）→ 検証（Assert/Verify） の3段階で進む。

  /**
   * 受講生詳細の一覧検索のテストです。
   */
  @Test
  void VerifyTheListSearchForStudentDetails_TheRepositoryAndConverterProcessesAreBeingCalledCorrectly() {
    //Arrange
    List<Student> studentList = new ArrayList<>();
    List<StudentCourse> studentCourseList = new ArrayList<>();

    when(repository.search()).thenReturn(studentList);
    when(repository.searchStudentsCoursesList()).thenReturn(studentCourseList);

    //Act
    sut.searchStudentList();

    //Assert/Verify
    verify(repository, times(1)).search();
    verify(repository, times(1)).searchStudentsCoursesList();
    verify(converter, times(1)).convertStudentDetails(studentList, studentCourseList);
  }

  /**
   * 受講生詳細検索のテストです。
   */
  @Test
  void VerifySearchStudent_TheRepositoryProcessesAreBeingCalledCorrectlyAndReturnsExpectedStudentDetail() {
    //Arrange
    String studentId = "1";
    Student student = new Student();
    student.setStudentId(studentId);
    List<StudentCourse> studentCourseList = new ArrayList<>();

    when(repository.searchStudent(studentId)).thenReturn(student);
    when(repository.searchStudentCourseList(studentId)).thenReturn(studentCourseList);

    //Act
    StudentDetail actual = sut.searchStudent(studentId);

    //Assert/Verify
    verify(repository, times(1)).searchStudent(studentId);
    verify(repository, times(1)).searchStudentCourseList(studentId);
    assertEquals(student, actual.getStudent());
    assertEquals(studentCourseList, actual.getStudentCourseList());
  }

  /**
   * 受講生詳細の登録のテストです。
   */
  @Test
  void VerifyRegisterStudent_TheRepositoryProcessesAreBeingCalledCorrectlyAndSetsInitialCourseDates() {
    //Arrange
    Student student = new Student();
    student.setStudentId("1");

    StudentCourse studentCourse = new StudentCourse();
    List<StudentCourse> studentCourseList = new ArrayList<>();
    studentCourseList.add(studentCourse);

    StudentDetail studentDetail = new StudentDetail(student, studentCourseList);

    //Act
    StudentDetail actual = sut.registerStudent(studentDetail);

    //Assert/Verify
    verify(repository, times(1)).registerStudent(student);
    verify(repository, times(1)).registerStudentCourse(studentCourse);
  }

  /**
   * 受講生詳細の更新のテストです。
   */
  @Test
  void VerifyUpdateStudent_TheRepositoryProcessesAreBeingCalledCorrectly() {
    //Arrange
    Student student = new Student();
    student.setStudentId("1");

    StudentCourse studentCourse1 = new StudentCourse();
    StudentCourse studentCourse2 = new StudentCourse();
    List<StudentCourse> studentCourseList = List.of(studentCourse1, studentCourse2);

    StudentDetail studentDetail = new StudentDetail(student, studentCourseList);

    //Act
    sut.updateStudent(studentDetail);

    //Assert/Verify
    verify(repository, times(1)).updateStudent(student);
    verify(repository, times(1)).updateStudentCourse(studentCourse1);
    verify(repository, times(1)).updateStudentCourse(studentCourse2);
  }
}

