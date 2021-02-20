package spring03;


import aop.Student;
import config.ScanConfig03;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class SpringDemoTask03 {

    //注解
    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(ScanConfig03.class); //要开启aop
        Student schoolAnnotation = context.getBean(Student.class);

        System.out.println(schoolAnnotation.getClass());
        schoolAnnotation.read();
        System.out.println("   context.getBeanDefinitionNames() ===>> "+ String.join(",", context.getBeanDefinitionNames()));



    }
}
