package mailchimp.anotation;

import mailchimp.MailChimpAPIVersion;

import java.lang.annotation.*;

@Documented
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
public @interface Method {
    /**
     * MailChimp API version to be used to execute the annotated method.
     */
    public MailChimpAPIVersion version();

    /**
     * The MailChimp API method name.
     */
    public String name();
}
