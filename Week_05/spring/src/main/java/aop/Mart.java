package aop;

import java.lang.annotation.*;

/**
 * 自定义注解
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
@Documented
public @interface Mart {

     String value() default "";

}