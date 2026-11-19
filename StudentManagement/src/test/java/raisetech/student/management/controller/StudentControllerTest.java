package raisetech.student.management.controller;

import static jakarta.validation.Validation.buildDefaultValidatorFactory;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.service.StudentService;


@WebMvcTest(StudentController.class)
class StudentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private StudentService service;

  private Validator validator = buildDefaultValidatorFactory().getValidator();

  @Test
  void 受講生詳細の一覧検索が実行できて空のリストが返ってくること() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/studentList"))
        .andExpect(status().isOk());

    verify(service, times(1)).searchStudentList();
  }

  @Test
  void 受講生詳細検索が実行できてIDに紐づく受講生情報が取得できること() throws Exception {
    Student student = new Student();
    student.setStudentId("1");
    student.setFullName("Tesuto Taro");
    student.setFurigana("Tesuto Taro");
    student.setNickname("Testuto");
    student.setEmailAddress("test@example.com");
    student.setCityAddress("Tokyo");
    student.setGender("male");

    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentCourseList(List.of());

    when(service.searchStudent("1")).thenReturn(studentDetail);

    mockMvc.perform(MockMvcRequestBuilders.get("/student/1"))
        .andExpect(status().isOk());

    verify(service, times(1)).searchStudent("1");
  }

  @Test
  void 受講生詳細の登録が実行できて空の受講生詳細データが返ってくること() throws Exception {
    String requestJson = """
        {"student":
        {
         "studentId": "1",
         "fullName": "Tesuto Taro",
         "furigana": "Tesuto Taro",
         "emailAddress": "test@example.com",
         "cityAddress": "Tokyo",
         "gender": "male"
         },
         "studentCourseList": []
         }
        """;

    mockMvc.perform(MockMvcRequestBuilders.post("/registerStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
        .andExpect(status().isOk());

    verify(service, times(1)).registerStudent(any());
  }

  @Test
  void 受講生詳細の更新ができて更新が成功したとメッセージが返ってくること() throws Exception {
    String requestJson = """
        {"student":
        {
         "studentId": "1",
         "fullName": "Tesuto Taro",
         "furigana": "Tesuto Taro",
         "emailAddress": "test@example.com",
         "cityAddress": "Tokyo",
         "gender": "male"
         },
         "studentCourseList": []
         }
        """;

    mockMvc.perform(MockMvcRequestBuilders.put("/updateStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
        .andExpect(status().isOk())
        .andExpect(content().string("Update was successful."));

    verify(service, times(1)).updateStudent(any());
  }

  @Test
  void 受講生詳細の受講生で適切な値を入力したときに入力チェックに異常が発生しないこと() {
    Student student = new Student();
    student.setStudentId("1");
    student.setFullName("Tesuto Taro");
    student.setFurigana("Tesuto Taro");
    student.setNickname("Testuto");
    student.setEmailAddress("test@example.com");
    student.setCityAddress("Tokyo");
    student.setGender("male");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生詳細の受講生でIDに数字以外を用いたときに入力チェックに掛かること() {
    Student student = new Student();
    student.setStudentId("This is test");
    student.setFullName("Tesuto Taro");
    student.setFurigana("Tesuto Taro");
    student.setNickname("Testuto");
    student.setEmailAddress("test@example.com");
    student.setCityAddress("Tokyo");
    student.setGender("male");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations).extracting("message").containsOnly("Please enter only numbers.");
  }

  @Test
  void 受講生コース情報でコース名に適切な名前を入力したときに入力チェックに異常が発生しないこと() {
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setCourseName("Java course");

    Set<ConstraintViolation<StudentCourse>> violations = validator.validate(studentCourse);

    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生コース情報でコース名が未入力のときに入力チェックに掛かること() {
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setCourseName("");

    Set<ConstraintViolation<StudentCourse>> violations = validator.validate(studentCourse);

    assertThat(violations).extracting("message")
        .containsOnly("Course name is required.");
  }

  @Test
  void 受講生コース情報でコース名が100文字を超えたときに入力チェックに掛かること() {
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setCourseName("a".repeat(101));

    Set<ConstraintViolation<StudentCourse>> violations = validator.validate(studentCourse);

    assertThat(violations).extracting("message")
        .containsOnly("Please enter a course name of 100 characters or fewer.");
  }
}


