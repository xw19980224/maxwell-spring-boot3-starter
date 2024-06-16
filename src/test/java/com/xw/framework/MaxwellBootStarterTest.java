package com.xw.framework;

import com.google.common.collect.Maps;
import com.xw.framework.config.JwtConfig;
import com.xw.framework.utils.bean.JwtTool;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

/**
 * Created by MaxWell on 2023/12/11 10:36
 */
@SpringBootTest(classes = MaxwellBootStarter.class)
public class MaxwellBootStarterTest {

    @Resource
    private JwtConfig jwtConfig;


    @Test
    public void jwtTest() {
        Map<String, String> map = Maps.newHashMap();
        map.put("uid", "1");
        String token = JwtTool.createToken(jwtConfig.getKey(), jwtConfig.getSecret(), jwtConfig.getExpireTime(), map);
        System.out.println(token);
    }
}
