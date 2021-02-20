学习笔记
xml配置 装配bean
spring容器中的被**AOP切面**切住的实现类都是被代理过的bean
如果通过接口注入了bean，那么它将被正确地找到。
但是，如果bean是通过实际的类注入的，那么Spring将找不到与该类匹配的bean定义-因为动态代理类实际上不是实现类的子类(CGLIB代理是实现类的子类)。
可以代理该bean的一个很常见的原因是 Spring事务支持 -即使用@Transactional注释的bean, 注入实现类,事物将不会生效.
```java
        @Service
        @Transactional
        public class ServiceA implements  IServiceA{
            @Autowired
            private  ServiceB serviceB;
        }

        @Service
        @Transactional
        public class ServiceB implements IServiceB{
            // ...
        }
```
```java
/**
* 所以在示例中, 
* @see spring01.SpringDemoTask01
**/
public class SpringDemo {
    public static void main(String[] args) {
        ISchool school = context.getBean(ISchool.class);
        ISchool school2 = context.getBean(School.class); // 报错,NoSuchBeanDefinitionException
    }
}
```
 ![avatar](CGLIB代理.png)       
示例中Klass class1 = context.getBean(Klass.class); 不报错,是因为Klass没有interface,spring使用CGLIB代理,是Klass的子类, 使用动态代理不是;
如果把klass实现一个接口也会报错, (// 注意,继承一个空接口,spring仍使用CGLIB代理Klass类, 接口中有方法时,才使用动态代理)

proxy-target-class="true" 代表强制所有类(除了接口,和代理类外), 都使用CGLIB代理;
默认为false, spring会自动判断使用jdk动态代理,还是CGLIB代理