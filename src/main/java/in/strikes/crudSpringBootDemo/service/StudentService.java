package in.strikes.crudSpringBootDemo.service;

import in.strikes.crudSpringBootDemo.dto.createStudentRequestDTO;
import in.strikes.crudSpringBootDemo.dto.createStudentResponseDto;
import in.strikes.crudSpringBootDemo.dto.updateStudentRequestDto;
import in.strikes.crudSpringBootDemo.dto.updateStudentResponseDto;
import in.strikes.crudSpringBootDemo.entity.Student;
import in.strikes.crudSpringBootDemo.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    public StudentService (StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public createStudentResponseDto createStudent(createStudentRequestDTO createStudentRequestDTO){
        Student student = mapToEntity(createStudentRequestDTO);

        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());
        Student studentResp = studentRepository.save(student);
        return mapToDto(studentResp);
//        studentReq.setDeleted(false);
//        Student studentResp = studentRepository.save(studentReq);
//        return studentResp;
    }

    public createStudentResponseDto getStudent(Long id){
        Optional<Student> studentResp = studentRepository.findByIdAndDeletedIsFalse(id);
        if (studentResp.isPresent()){
            return mapToDto(studentResp.get());
        }
        return null;
    }

    public List<createStudentResponseDto> getAllStudent(){
        List<Student> studentList = studentRepository.findByDeletedIsFalse();

        return studentList.stream().map(this::mapToDto).toList();
    }

    public updateStudentResponseDto updateStudent(Long id , updateStudentRequestDto updateStudentRequestDto){
        Optional<Student> existingStudent = studentRepository.findByIdAndDeletedIsFalse(id);

        if (existingStudent.isEmpty()){
            return null;
        }

        Student studentToSave = existingStudent.get();

        studentToSave.setName(updateStudentRequestDto.getName());
        studentToSave.setRollNo(updateStudentRequestDto.getRollNo());
        studentToSave.setSubject(updateStudentRequestDto.getSubject());
        studentToSave.setAge(updateStudentRequestDto.getAge());
        studentToSave.setDeleted(false);
        studentToSave.setUpdatedAt(LocalDateTime.now());
        Student savedStudent = studentRepository.save(studentToSave);
        return mapToUpdateDto(studentToSave);
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

    private Student mapToEntity(createStudentRequestDTO createStudentRequestDTO){
        Student student = new Student();
        student.setName(createStudentRequestDTO.getName());
        student.setAge(createStudentRequestDTO.getAge());
        student.setEmail(createStudentRequestDTO.getEmail());
        student.setRollNo(createStudentRequestDTO.getRollNo());
        student.setSubject(createStudentRequestDTO.getSubject());

        student.setDeleted(false);
        return student;
    }

    public createStudentResponseDto mapToDto(Student student){
        createStudentResponseDto createStudentResponseDto = new createStudentResponseDto();
        createStudentResponseDto.setId(student.getId());
        createStudentResponseDto.setName(student.getName());
        createStudentResponseDto.setAge(student.getAge());
        createStudentResponseDto.setEmail(student.getEmail());
        createStudentResponseDto.setRollNo(student.getRollNo());
        createStudentResponseDto.setSubject(student.getSubject());
        createStudentResponseDto.setMessage("Student Saved Successfully");
        createStudentResponseDto.setCreatedAt(student.getCreatedAt());
        createStudentResponseDto.setUpdatedAt(student.getUpdatedAt());

        return createStudentResponseDto;
    }

    public updateStudentResponseDto mapToUpdateDto(Student student){
        updateStudentResponseDto updateStudentResponseDto = new updateStudentResponseDto();
        updateStudentResponseDto.setId(student.getId());
        updateStudentResponseDto.setName(student.getName());
        updateStudentResponseDto.setAge(student.getAge());
        updateStudentResponseDto.setEmail(student.getEmail());
        updateStudentResponseDto.setRollNo(student.getRollNo());
        updateStudentResponseDto.setSubject(student.getSubject());
        updateStudentResponseDto.setMessage("Student Update Successfully");
        updateStudentResponseDto.setUpdatedAt(student.getUpdatedAt());

        return updateStudentResponseDto;
    }
}
