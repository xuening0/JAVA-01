package spring01;


import interfaceTest.ISchool;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class SpringDemoTask01 {
    
    public static void main(String[] args) {
        
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
//        Student student123 = context.getBean(Student.class);

        Student student123 = (Student) context.getBean("student123");
        System.out.println(student123.toString());
        System.out.println("Student对象AOP代理后的实际类型："+student123.getClass());
        Student student100 = (Student) context.getBean("student100");
        System.out.println(student100.toString());
    
        Klass class1 = context.getBean(Klass.class);
        System.out.println("Klass " + class1);
        System.out.println("Klass对象AOP代理后的实际类型："+class1.getClass()); // 被Aop2切面切住
        System.out.println("Klass对象AOP代理后的实际类型是否是Klass子类："+(class1 instanceof Klass));
    
        ISchool school = context.getBean(ISchool.class);
        System.out.println(school);
        System.out.println("ISchool接口的对象AOP代理后的实际类型："+(school instanceof ISchool)); // 被Aop1接口切面切住
        System.out.println("ISchool接口的对象AOP代理后的实际类型："+(school instanceof School));

/*        ISchool school2 = context.getBean(School.class);
        System.out.println("ISchool接口的对象AOP代理后的实际类型："+(school2 instanceof School));*/

        ISchool school1 = context.getBean(ISchool.class);
        System.out.println(school1);
        System.out.println("ISchool接口的对象AOP代理后的实际类型："+school1.getClass());
        
        school1.ding();
        
        class1.dong();
    
        System.out.println("   context.getBeanDefinitionNames() ===>> "+ String.join(",", context.getBeanDefinitionNames()));
        

    }
}
