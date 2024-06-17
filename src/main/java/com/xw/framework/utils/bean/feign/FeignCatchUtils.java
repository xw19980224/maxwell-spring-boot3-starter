package com.xw.framework.utils.bean.feign;

import com.xw.framework.domain.CommonResult;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by MaxWell on 2024/6/17 21:52
 */
@Slf4j
public class FeignCatchUtils {

    /**
     * 调用feign服务
     *
     * @param execFunction    接口调用函数
     * @param successFunction 成功处理 响应结果
     * @param <RESPONSE>      响应结果泛型
     * @param <RESULT>        返回结果泛型
     * @return 返回结果
     */
    public static <RESPONSE, RESULT> RESULT feignCatch(
            Supplier<CommonResult<RESPONSE>> execFunction,
            Function<CommonResult<RESPONSE>, RESULT> successFunction) {
        try {
            CommonResult<RESPONSE> response = execFunction.get();
            if (!response.isSuccess()) {
                return null;
            }
            return successFunction.apply(response);
        } catch (Exception e) {
            log.error("第三方接口调用异常", e);
            return null;
        }
    }
}
