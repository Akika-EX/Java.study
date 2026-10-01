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

  public List<Student> searchStudentList() {
    return repository.search();
  }

  public List<StudentsCourses> searchStudentsCoursesList() {
    return repository.searchStudentsCourses();
  }

  @Transactional
  public void registerStudent(StudentDetail studentDetail) {
    repository.registerStudent(studentDetail.getStudent());

    String newStudentId = studentDetail.getStudent().getStudentId();

    List<StudentsCourses> coursesList = studentDetail.getStudentsCourses();
    if (coursesList != null && !coursesList.isEmpty()) {
    }
    for (StudentsCourses course : coursesList) {
      course.setStudentId(newStudentId);
      repository.registerStudentsCourses(course);
    }
  }
  //第16回演習課題　受講生一覧の豹で名前をクリックすると、その受講生のID情報に基づいたデータを表示する　

  public StudentDetail searchStudent(String studentId) {
    //受講生情報をリポジトリからとってくる
    Student student = repository.searchStudent(studentId);

    //IDに紐づいたコースをコースリポジトリからとってくる
    List<StudentsCourses> studentsCourses = repository.searchStudentsCoursesByStudentId(studentId);

    //取得した受講生情報とコース情報をStudentDetailにセット
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentsCourses(studentsCourses);

    return studentDetail;
  }
}
