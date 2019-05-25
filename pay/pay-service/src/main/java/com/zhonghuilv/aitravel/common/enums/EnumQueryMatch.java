package com.zhonghuilv.aitravel.common.enums;

import com.zhonghuilv.aitravel.common.excption.ParameterNotValidException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
@AllArgsConstructor
@Getter
public enum EnumQueryMatch {

    EQ("="),
    GT(">"),
    GTE(">="),
    LT("<"),
    LTE("<="),
    LIKE("like");

    private String value;

    public static EnumQueryMatch toEnum(String value) {

        return Stream.of(values()).filter(va -> va.getValue().equals(value))
                .findAny().orElseThrow(() -> new ParameterNotValidException("匹配方式不存在:" + value));
    }

    /**
     * Returns the name of this enum constant, as contained in the
     * declaration.  This method may be overridden, though it typically
     * isn't necessary or desirable.  An enum type should override this
     * method when a more "programmer-friendly" string form exists.
     *
     * @return the name of this enum constant
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
