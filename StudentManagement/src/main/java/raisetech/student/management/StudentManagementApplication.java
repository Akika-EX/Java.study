package raisetech.student.management;

import java.util.List;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StudentManagementApplication {

  @Autowired
   private StudentRepository repository;


  public static void main(String[] args) {
    SpringApplication.run(StudentManagementApplication.class, args);
  }

  //show All student
  @GetMapping("/studentList")
  public List<Student> getStudentList() {
    return repository.searchAll();
  }

  //show 1 student
  @GetMapping("/student")
  public String getStudent(@RequestParam("name") String name) {
    Student student = repository.searchByName(name);
    return  student.getName() + " " + student.getAge() + "years old";
  }

  //add name & age
  @PostMapping("/student")
  public void registerStudent(String name, int age) {
    repository.registerStudent(name, age);
  }

  //update info
  @PatchMapping("/student")
  public void updateStudentName(String name, int age) {
    repository.updateStudent(name, age);
  }

  //delete info
  @DeleteMapping("/student")
  public void deleteStudent(String name) {
    repository.deleteStudent(name);
  }
}