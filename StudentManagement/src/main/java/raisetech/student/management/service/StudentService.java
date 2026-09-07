package raisetech.student.management.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentsCourses;
import raisetech.student.management.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }
  public List<Student> searchStudentList() {
    //*課題には不要（あとで使うから残す！）repository.search();

    //絞り込みをする。年齢が30代の人のみ抽出する。
    //抽出したリストをコントローラーに返す。
    return repository.search().stream()
        .filter(student -> student.getAge() >= 30 && student.getAge() < 40 )
        .toList();
  }

  public List<StudentsCourses> searchStudentsCoursesList() {
    //絞り込み検索で「Javaコース」のコース情報のみを抽出する。
    //抽出したリストをコントローラーに返す。
    return repository.searchStudentsCourses().stream()
        .filter(studentsCourses -> "Java Programming Course".equals(studentsCourses.getCourseName()))
        .toList();
  }
}
