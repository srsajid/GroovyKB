package mailchimp.anotation;

import java.lang.annotation.*;

@Documented
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface Field {
    /**
     * Property name in JSON mapping.
     * If not specified, the field name will be used.
     */
    public String name() default "";
}