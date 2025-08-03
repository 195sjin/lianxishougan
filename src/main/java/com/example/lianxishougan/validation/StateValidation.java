package com.example.lianxishougan.validation;

import com.example.lianxishougan.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StateValidation implements ConstraintValidator<State,String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return false;
        }
        if (s.equals("已发布")||s.equals("草稿")||s.equals("待审批")||s.equals("审批成功")||s.equals("审批失败")) {
            return true;
        }
        return false;
    }
}
