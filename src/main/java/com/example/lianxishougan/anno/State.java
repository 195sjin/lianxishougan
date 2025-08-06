package com.example.lianxishougan.anno;

import com.example.lianxishougan.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(
        validatedBy = {StateValidation.class}
)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface State {
    String message() default "state参数的值是草稿、待审批、审批成功、审批失败";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
