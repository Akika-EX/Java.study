package raisetech.student.management;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StudentManagementApplication {


  public static void main(String[] args) {
    SpringApplication.run(StudentManagementApplication.class, args);
  }

  private Map<String, Integer> studentInfoMap = new HashMap<>(Map.of(
      "Aki Yoshi", 37
  ));

  //show studentInfo
  @GetMapping("/studentInfo")
  public Map<String, Integer> getStudentInfo() {
    return studentInfoMap;
  }

  //add name & age
  @PostMapping("/addStudent")
  public void setUpdateStudentInfo(String name, Integer age) {
    studentInfoMap.put(name, age);
  }
}