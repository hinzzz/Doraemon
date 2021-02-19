package com.hinz.jsr303.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author ：quanhz
 * @date ：Created in 2021/2/19 11:47
 * @Description : 校验性别 0-女 1-男 2-未知
 */
@Documented
@Constraint(validatedBy = { GenderValueConstraintValidator.class })
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
public @interface IntValue {

    String message() default "{com.hinz.jsr3.valid.GenderValue.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    int[] accessVals() default { };

}
