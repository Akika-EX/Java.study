package raisetech.student.management.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentsCourses;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  //受講生一覧取得
  public List<Student> searchStudentList() {
    return repository.search();
  }

  //受講生コースの一覧取得
  public List<StudentsCourses> searchStudentsCoursesList() {
    return repository.searchStudentsCourses();
  }
  //指定したIDの受講生情報を取得

  public StudentDetail searchStudent(String studentId) {
    Student student = repository.searchStudent(studentId);
    List<StudentsCourses> studentsCourses = repository.searchStudentsCoursesByStudentId(studentId);

    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentsCourses(studentsCourses);
    return studentDetail;
  }

  //受講生情報登録
  @Transactional
  public void registerStudent(StudentDetail studentDetail) {
    repository.registerStudent(studentDetail.getStudent());

    String newStudentId = studentDetail.getStudent().getStudentId();

    List<StudentsCourses> coursesList = studentDetail.getStudentsCourses();
    if (coursesList != null && !coursesList.isEmpty()) {
      for (StudentsCourses course : coursesList) {
        course.setStudentId(newStudentId);
        repository.registerStudentsCourses(course);
      }
    }
  }

  //受講生情報の更新
  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());

    List<StudentsCourses> coursesList = studentDetail.getStudentsCourses();
    if (coursesList != null && !coursesList.isEmpty()) {
      for (StudentsCourses studentsCourse : coursesList) {
        repository.updateStudentsCourses(studentsCourse);
      }
    }
  }
}