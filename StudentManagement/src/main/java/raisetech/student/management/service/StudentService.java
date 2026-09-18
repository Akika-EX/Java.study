package raisetech.student.management.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
}
