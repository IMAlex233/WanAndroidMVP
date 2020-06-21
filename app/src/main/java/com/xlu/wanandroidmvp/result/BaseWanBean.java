package com.xlu.wanandroidmvp.result;

import com.xlu.wanandroidmvp.common.Const;

/**
 * @Author xlu
 * @Date 2020/6/20 15:15
 * @Description TODO
 */
public class BaseWanBean {

    private int errorCode;
    private String errorMsg;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public boolean isSuccess() {
        return errorCode == Const.HttpConst.HTTP_CODE_SUCCESS;
    }
}