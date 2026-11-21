package in.strikes.crudSpringBootDemo.repository;

import in.strikes.crudSpringBootDemo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//@Component
//public class StudentRepository {
//
//
//    public Student saveStudent(Student studentReq){
//        System.out.println("Inside Student Repository");
//        System.out.println("Exiting Student Repository");
//
//        Student s1 = new Student();
//        s1.setName("Vishal");
//        s1.setAge(23);
//        s1.setEmail("Vishal@gamil.com");
//        s1.setRollNo(102);
//        s1.setSubject("Spring Framework");
//
//        return s1;
//    }
//}

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByIdAndDeletedIsFalse(Long id);

    List<Student> findByDeletedIsFalse();
}