package spring03;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AopStudent {
    //        @Pointcut("within(aop.Student+)")
//    @Pointcut(value = "execution(* spring03.IStudent.*read(..))")
    @Pointcut("@within(aop.Mart)") // Mart注解要在类上
    public void point() {

    }

    @Before(value = "point()")
    public void strongAfter() {
        System.out.println("前置方法===");
    }


    /*@Pointcut("@annotation(mart)")
    public void point(Mart mart) { //Mart注解用在 方法 上

    }
    @Before(value = "point(mart)")
    public void strongAfter(Mart mart) {
        System.out.println("前置方法===");
    }*/
}
