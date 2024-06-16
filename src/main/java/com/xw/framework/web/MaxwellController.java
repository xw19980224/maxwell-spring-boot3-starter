package com.xw.framework.web;

import com.xw.framework.annotation.RawResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试类
 * Created by MaxWell on 2023/12/7 10:12
 */
@Tag(name = "测试类")
@RestController
@RequestMapping("maxwell")
public class MaxwellController {

    @RawResponse
    @GetMapping("test")
    @Operation(summary = "测试接口")
    public String test() {
        return "TEST";
    }

    @GetMapping("testGet")
    @Operation(summary = "get请求测试接口")
    public String testGet(@Parameter(description = "test") @RequestParam("test") String test) {
        return test;
    }
}
