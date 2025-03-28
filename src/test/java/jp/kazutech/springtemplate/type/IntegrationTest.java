package jp.kazutech.springtemplate.type;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.Tag;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Tag("integrationTest")
@Inherited
public @interface IntegrationTest {

}
