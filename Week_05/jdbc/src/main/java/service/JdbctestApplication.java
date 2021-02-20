package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import service.demo.BatchStudentDao;
import service.demo.StudentDao;
import service.model.Student;

@SpringBootApplication
public class JdbctestApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(JdbctestApplication.class, args);
    }

    @Autowired
    private Student student;
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private BatchStudentDao batchStudentDao;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("自定义类加载"+student);

        studentDao.insert_jdbc();
        System.out.println("===========");
        studentDao.insertData();
        studentDao.listData();
        System.out.println("===========");
        batchStudentDao.batchInsert();
        studentDao.listData();
    }


}
