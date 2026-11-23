package in.strikes.crudSpringBootDemo.controller;

import in.strikes.crudSpringBootDemo.dto.createStudentRequestDTO;
import in.strikes.crudSpringBootDemo.dto.createStudentResponseDto;
import in.strikes.crudSpringBootDemo.dto.updateStudentRequestDto;
import in.strikes.crudSpringBootDemo.dto.updateStudentResponseDto;
import in.strikes.crudSpringBootDemo.entity.Student;
import in.strikes.crudSpringBootDemo.service.StudentService;
import jakarta.validation.Valid;
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
    public ResponseEntity<createStudentResponseDto> createStudent(@Valid @RequestBody createStudentRequestDTO createStudentRequestDTO){
//        System.out.println(student.getName());
//        System.out.println(student.getEmail());
        createStudentResponseDto createdStudent = studentService.createStudent(createStudentRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }

//    @GetMapping("/get/{id}")
    @GetMapping("/get")
    public ResponseEntity<createStudentResponseDto> getStudent(/*@PathVariable*/ @RequestParam Long id){
        createStudentResponseDto studentResp = studentService.getStudent(id);

        if (studentResp == null){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(studentResp);
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<createStudentResponseDto>> getAllStudent(){
        List<createStudentResponseDto> studentList = studentService.getAllStudent();

        if (studentList == null){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(studentList);
    }

    @PutMapping("/update")
//    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student){
    public ResponseEntity<updateStudentResponseDto> updateStudent(@RequestParam Long id, @RequestBody updateStudentRequestDto updateStudentRequestDto){
        updateStudentResponseDto studentResp = studentService.updateStudent(id ,updateStudentRequestDto);

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
