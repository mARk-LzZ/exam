package com.lzz.exam.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum ApiExceptionCode {

    /**
     * 无效签名
     */
    INVALID_SIGNATURE(403,"Invalid signature"),

    /**
     * token过期
     */
    TOKEN_EXPIRED(401,"token expired"),

    /**
     * 算法不匹配
     */
    ALGORITHM_MISMATCH(401 , "Algorithm mismatch"),

    /**
     * token不存在
     */
    MISSING_TOKEN(401,"missing token"),
    /**
     * 密码错误
     */
    INVALID_PASSWORD(403,"invalid password"),
    /**
     * 账号不存在
     */
    ACCOUNT_DOES_NOT_EXIST(401,"account does not exist");
    /**
     * 状态码
     */
    private Integer status;

    /**
     * 描述信息
     */
    private String msg;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status=status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg=msg;
    }
}
