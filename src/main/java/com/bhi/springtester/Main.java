package com.bhi.springtester;

import com.bhi.springtester.config.MainConfig;
import com.bhi.springtester.entity.Student;
import com.bhi.springtester.repository.StudentRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(MainConfig.class);
        DataSource ds = ac.getBean(DataSource.class);
        System.out.println(ds);

        StudentRepository repo = ac.getBean(StudentRepository.class);
        List<Student> students = repo.findAll();
        System.out.println(students);
    }
}
