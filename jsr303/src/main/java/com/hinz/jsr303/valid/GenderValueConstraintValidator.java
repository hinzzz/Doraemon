package com.hinz.jsr303.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

public class GenderValueConstraintValidator implements ConstraintValidator<IntValue,Integer> {

    private Set<Integer> set = new HashSet<>();
    //初始化方法
    @Override
    public void initialize(IntValue constraintAnnotation) {

        int[] vals = constraintAnnotation.accessVals();
        for (int val : vals) {
            set.add(val);
        }

    }

    //判断是否校验成功

    /**
     *
     * @param value 需要校验的值
     * @param context
     * @return
     */
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {

        return set.contains(value);
    }
}
