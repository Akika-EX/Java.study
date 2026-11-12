package raisetech.student.management.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
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
  void 受講生詳細一覧検索テスト() {
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
  void 受講生詳細検索のテスト() {
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
   * 存在しない受講生を検索したときのテストです。
   */
  @Test
  void 受講生詳細検索_受講生が存在しないときのテスト() {
    //Arrange
    String studentId = "1000000";
    when(repository.searchStudent(studentId)).thenReturn(null);

    //Act
    StudentDetail actual = sut.searchStudent(studentId);

    //Assert/Verify
    assertNull(actual.getStudent());
    assertEquals(0, actual.getStudentCourseList().size());
  }

  /**
   * 受講生詳細の登録のテストです。
   */
  @Test
  void 受講生詳細の登録テスト() {
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
   * 受講生詳細の登録に失敗したとき、コース情報の登録が行われないことのテストです。
   */
  @Test
   void 受講生詳細の登録_登録に失敗したときコース情報の登録が行われないことのテスト(){
  //Arrange
    Student student = new Student();
    student.setStudentId("1");
    StudentCourse studentCourse = new StudentCourse();
    StudentDetail studentDetail = new StudentDetail(student, List.of(studentCourse));
    doThrow(new RuntimeException("Error")).when(repository).registerStudent(student);

  //Act
  assertThrows(RuntimeException.class, () -> {
    sut.registerStudent(studentDetail);
  });

  //Verify
    verify(repository, times(1)).registerStudent(student);
    verify(repository, never()).registerStudentCourse(studentCourse);
  }

  /**
   * 受講生詳細の登録時、コース情報が空欄のときにはコース登録が行われないことのテストです。
   */
  @Test
  void 受講生詳細の登録_コース情報が空欄だったときコース登録が行われないこと(){
    //Arrange
    Student student = new Student();
    student.setStudentId("1");
    StudentDetail studentDetail = new StudentDetail(student, new ArrayList<>());

    //Act
    sut.registerStudent(studentDetail);

    //Assert/Verify
    verify(repository, times(1)).registerStudent(student);
    verify(repository, never()).registerStudentCourse(any());
  }

  /**
   * 受講生詳細の更新のテストです。
   */
  @Test
  void 受講生詳細の更新テスト() {
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

