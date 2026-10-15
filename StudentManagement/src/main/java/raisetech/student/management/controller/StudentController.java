package raisetech.student.management.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentsCourses;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.service.StudentService;

@RestController
public class StudentController {

  private StudentService service;
  private StudentConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;
  }

  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    List<Student> students = service.searchStudentList();
    List<StudentsCourses> studentsCourses = service.searchStudentsCoursesList();
    return converter.convertStudentDetails(students, studentsCourses);
  }

  @GetMapping("/student/{studentId}")
  public String getStudent(@PathVariable String studentId, Model model) {
    StudentDetail studentDetail = service.searchStudent(studentId);
    model.addAttribute("studentDetail", studentDetail);
    return "updateStudent";
  }

  @GetMapping("/newStudent")
  public String newStudent(Model model) {
    StudentDetail studentDetail = new StudentDetail();

    List<StudentsCourses> courses = new ArrayList<>();
    courses.add(new StudentsCourses());
    studentDetail.setStudentsCourses(courses);
    model.addAttribute("studentDetail", studentDetail);
    return "registerStudent";
  }

  @PostMapping("/registerStudent")
  public ResponseEntity<String> registerStudent(@RequestBody @Validated StudentDetail
      studentDetail) {
    service.registerStudent(studentDetail);
    return ResponseEntity.ok("Registration was successful.");
  }

  @PostMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(
      @RequestBody @Validated StudentDetail studentDetail) {
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("Update was successful.");
  }
}
