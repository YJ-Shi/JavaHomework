package annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Author{
    String name() default "SY-jie";
    String source() default "NjuIcs-Java-2018f";
    int revision() default 1;
}
