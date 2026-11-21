package in.strikes.crudSpringBootDemo.controller;

import in.strikes.crudSpringBootDemo.entity.Student;
import in.strikes.crudSpringBootDemo.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @PostMapping("/create")
//    public String createStudent(@RequestBody Student student){
    public ResponseEntity<Student> createStudent(@RequestBody Student student){
//        System.out.println(student.getName());
//        System.out.println(student.getEmail());
        System.out.println("Inside Student Controller");
        Student createdStudent = studentService.createStudent(student);
        System.out.println("Exiting Student Controller");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }

//    @GetMapping("/get/{id}")
    @GetMapping("/get")
    public ResponseEntity<Student> getStudent(/*@PathVariable*/ @RequestParam Long id){
        Student studentResp = studentService.getStudent(id);

        if (studentResp == null){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(studentResp);
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<Student>> getAllStudent(){
        List<Student> studentList = studentService.getAllStudent();

        if (studentList == null){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(studentList);
    }

//    @PutMapping("/update/{id}")
//    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student){
    public ResponseEntity<Student> updateStudent(@RequestParam Long id, @RequestBody Student student){
        Student studentResp = studentService.updateStudent(id ,student);

        if (studentResp == null){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(studentResp);
    }

//    @DeleteMapping("/delete/{id}")
    @DeleteMapping("/delete")
//    public ResponseEntity<String> deleteStudent(@PathVariable Long id){
    public ResponseEntity<String> deleteStudent(@RequestParam Long id){
        Boolean isDeleted = studentService.deleteStudent(id);

        if (!isDeleted){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Record Deleted");
    }

//    @PatchMapping("/delete-soft/{id}")
    @PatchMapping("/delete-soft")
//    public ResponseEntity<String> deletedStudentSoftly(@PathVariable Long id){
    public ResponseEntity<String> deletedStudentSoftly(@RequestParam Long id){
        Boolean isDeleted = studentService.deleteStudentSoftly(id);

        if (!isDeleted){
            return ResponseEntity.notFound().build();
        }

        return  ResponseEntity.ok("Record Deleted Softly");
    }


}
