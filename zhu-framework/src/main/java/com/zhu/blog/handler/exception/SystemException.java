package com.zhu.blog.handler.exception;

import com.zhu.blog.enums.AppHttpCodeEnum;

/**
 * @author GH
 * @Description:
 * @date 2023/3/3 21:24
 */
public class SystemException extends RuntimeException{
    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }
}
