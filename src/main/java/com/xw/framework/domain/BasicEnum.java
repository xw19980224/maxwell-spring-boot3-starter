package com.xw.framework.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import com.mybatisflex.annotation.EnumValue;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * 通用枚举接口
 *
 * @param <V> 枚举值的类型
 * @param <E> 子枚举类型
 * @author vains
 */
public interface BasicEnum<V extends Serializable, E extends Enum<E>> {

    /**
     * 根据子枚举和子枚举对应的入参值找到对应的枚举类型
     *
     * @param code  子枚举中对应的值
     * @param clazz 子枚举类型
     * @param <B>   {@link BasicEnum} 的子类类型
     * @param <V>   子枚举值的类型
     * @param <E>   子枚举的类型
     * @return 返回 {@link BasicEnum} 对应的子类实例
     */
    static <B extends BasicEnum<V, E>, V extends Serializable, E extends Enum<E>> B fromCode(V code, Class<B> clazz) {
        return Arrays.stream(clazz.getEnumConstants())
                .filter(e -> Objects.equals(e.getCode(), code))
                .findFirst().orElse(null);
    }

    @EnumValue
    @JsonValue
    V getCode();

}