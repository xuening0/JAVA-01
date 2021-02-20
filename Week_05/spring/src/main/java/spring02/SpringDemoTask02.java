package spring02;


import interfaceTest.ISchool;
import config.ScanConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;


public class SpringDemoTask02 {

    //注解
    public static void main(String[] args) {

//        ApplicationContext context = new AnnotationConfigApplicationContext("spring02");
        ApplicationContext context = new AnnotationConfigApplicationContext(ScanConfig.class);
        ISchool schoolAnnotation = context.getBean(ISchool.class);

        System.out.println(schoolAnnotation.getClass()); // 没有被AOP切住
        schoolAnnotation.ding();
        System.out.println("   context.getBeanDefinitionNames() ===>> "+ String.join(",", context.getBeanDefinitionNames()));

        BigDecimal bigDecimal = BigDecimal.valueOf(0.2);
        BigDecimal bigDecimal2 = BigDecimal.valueOf(0.1);
        BigDecimal bigDecimal13 = new BigDecimal(0.2);
        BigDecimal bigDecimal14 = new BigDecimal(0.1);
        System.out.println(bigDecimal.subtract(bigDecimal2));
        System.out.println(bigDecimal13.subtract(bigDecimal14));

    }
}
