package com.xlu.wanandroidmvp.base;

import android.app.Application;
import android.net.ParseException;
import android.text.TextUtils;
import com.google.gson.JsonParseException;
import com.jess.arms.mvp.IView;
import com.xlu.wanandroidmvp.R;
import com.xlu.wanandroidmvp.common.Const;
import com.xlu.wanandroidmvp.common.WApplication;
import com.xlu.wanandroidmvp.event.Event;
import com.xlu.wanandroidmvp.http.manger.ApiException;
import com.xlu.wanandroidmvp.result.BaseBean;
import com.xlu.wanandroidmvp.utils.StringUtils;

import org.json.JSONException;
import org.simple.eventbus.EventBus;
import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import io.reactivex.observers.ResourceObserver;
import retrofit2.HttpException;

public abstract class BaseObserver<T extends BaseBean> extends ResourceObserver<T> {

    protected IView mView;
    private String mErroMessage;
    private WApplication mApp = WApplication.getInstance();

    public BaseObserver(IView mView) {
        this.mView = mView;
    }

    @Override
    protected void onStart() {
        super.onStart();
//        if (!NetWorkManager.isNetWorkAvailable()) {
//            mView.showMessage(mApp.getString(R.string.network_unavailable_tip));
//            onComplete();
//        }
//        mView.showLoading();
    }

    @Override
    public void onNext(T t) {
        switch (t.getCode()) {
            case Const.HttpConst.HTTP_CODE_SUCCESS:
                onSuccess(t);
                break;
            case Const.HttpConst.HTTP_CODE_LOGIN_EXPIRED:
                EventBus.getDefault().post(new Event<>(Const.EventCode.LOGIN_EXPIRED, t));
                break;
            case Const.HttpConst.HTTP_CODE_ERROR:
            default:
                onError(new ApiException(StringUtils.isEmpty(t.getMsg()) ? mApp.getString(R.string.error_unknown) : t.getMsg(), t.getCode()));
                break;
        }
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof ConnectException ||  e instanceof UnknownHostException) {   //   连接错误
            onException(ExceptionReason.CONNECT_ERROR);
        } else if ( e instanceof InterruptedIOException) {  //  连接超时
            onException(ExceptionReason.CONNECT_TIMEOUT);
        } else if (e instanceof HttpException) {    // HTTP错误
            onException(ExceptionReason.BAD_NETWORK);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {   //  解析错误
            onException(ExceptionReason.PARSE_ERROR);
        } else if (e instanceof ApiException) {
            if (StringUtils.isEmpty(e.getMessage())) {
                onException(ExceptionReason.UNKNOWN_ERROR);
            } else {
                mErroMessage = e.getMessage();
                mView.showMessage(mErroMessage);
            }
        } else {    // 未知错误
            onException(ExceptionReason.UNKNOWN_ERROR);
        }
        onComplete();
    }

    protected void onException(ExceptionReason reason) {
        switch (reason) {
            case PARSE_ERROR:
                mErroMessage = mApp.getString(R.string.error_parse);
                break;
            case BAD_NETWORK:
                mErroMessage = mApp.getString(R.string.network_unavailable_tip);
                break;
            case CONNECT_ERROR:
                mErroMessage = mApp.getString(R.string.error_fail_to_connect);
                break;
            case CONNECT_TIMEOUT:
                mErroMessage = mApp.getString(R.string.error_connect_time_out);
                break;
            case UNKNOWN_ERROR:
            default:
                mErroMessage = mApp.getString(R.string.error_unknown);
                break;
        }
        mView.showMessage(mErroMessage);
        onComplete();
    }

    @Override
    public void onComplete() {
        if (mView != null) {
            mView.hideLoading();
        }
    }

    /**
     * 成功回调
     * @param t
     */
    public abstract void onSuccess(T t);

    /**
     * 服务端返回错误回调：服务器返回数据，但响应码不为200
     * @param t
     */
    public void onError(T t) {
        String msg = t.getMsg();
        if (TextUtils.isEmpty(msg)) {
            mView.showMessage(mApp.getString(R.string.error_server_error));
        } else {
            mView.showMessage(t.getMsg());
        }

    }

    /**
     * 请求网络失败原因
     */
    public enum ExceptionReason {
        /**
         * 解析数据失败
         */
        PARSE_ERROR,
        /**
         * 网络问题
         */
        BAD_NETWORK,
        /**
         * 连接错误
         */
        CONNECT_ERROR,
        /**
         * 连接超时
         */
        CONNECT_TIMEOUT,
        /**
         * 未知错误
         */
        UNKNOWN_ERROR,
    }
}
