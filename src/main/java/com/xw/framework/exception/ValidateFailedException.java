package com.xw.framework.exception;

import static com.xw.framework.domain.ResultCode.VALIDATE_FAILED;

/**
 * Created by maxwell on 2024/7/28 23:03
 */
public class ValidateFailedException extends BizException{
    public ValidateFailedException(String message) {
        super(VALIDATE_FAILED.getCode(),message);
    }
}
