package eu.lukasschoerghuber.publictransporthero.telegram.context;

import javax.inject.Qualifier;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Qualifier
public @interface ChatbotContextProviderQualifier {
    public enum Type { IN_MEMORY, REDIS, MONGODB, COUCHDB };

    Type type() default Type.IN_MEMORY;
}
