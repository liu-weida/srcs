package srcs.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// TME4 Exercice1 Question2
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SansEtat {

}
