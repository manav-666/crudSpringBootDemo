package in.strikes.crudSpringBootDemo.service;

import in.strikes.crudSpringBootDemo.entity.Student;
import in.strikes.crudSpringBootDemo.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    public StudentService (StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student studentReq){
        studentReq.setDeleted(false);
        System.out.println("Inside Student Service");
        Student studentResp = studentRepository.save(studentReq);
        System.out.println("Exiting Student Service");
        return studentResp;
    }

    public Student getStudent(Long id){
        Optional<Student> studentResp = studentRepository.findByIdAndDeletedIsFalse(id);
        if (studentResp.isPresent()){
            return studentResp.get();
        }
        return null;
    }

    public List<Student> getAllStudent(){
        List<Student> studentList = studentRepository.findByDeletedIsFalse();

        return studentList;
    }

    public Student updateStudent(Long id , Student student){
        Optional<Student> existingStudent = studentRepository.findByIdAndDeletedIsFalse(id);

        if (existingStudent.isEmpty()){
            return null;
        }

        Student studentToSave = existingStudent.get();

        studentToSave.setName(student.getName());
        studentToSave.setRollNo(student.getRollNo());
        studentToSave.setSubject(student.getSubject());
        studentToSave.setEmail(student.getEmail());
        studentToSave.setAge(student.getAge());
        studentToSave.setDeleted(false);
        return studentRepository.save(studentToSave);
    }


    public Boolean deleteStudent(Long id){
        Boolean isStudent = studentRepository.existsById(id);

        if(!isStudent) return false;

        studentRepository.deleteById(id);

        return true;
    }

    public Boolean deleteStudentSoftly(Long id){
        Optional<Student> existingStudent = studentRepository.findByIdAndDeletedIsFalse(id);

        if(existingStudent.isEmpty()){
            return false;
        }

        Student studentToSave = existingStudent.get();

        studentToSave.setDeleted(true);

        studentRepository.save(studentToSave);

        return true;
    }
}
