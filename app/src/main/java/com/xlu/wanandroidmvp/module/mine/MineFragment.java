package com.xlu.wanandroidmvp.module.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.jess.arms.base.BaseLazyLoadFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.EventBusManager;
import com.makeramen.roundedimageview.RoundedImageView;
import com.xlu.wanandroidmvp.R;
import com.xlu.wanandroidmvp.common.AppConfig;
import com.xlu.wanandroidmvp.common.Const;
import com.xlu.wanandroidmvp.di.component.DaggerMineComponent;
import com.xlu.wanandroidmvp.event.Event;
import com.xlu.wanandroidmvp.http.bean.Coin;
import com.xlu.wanandroidmvp.module.login.LoginActivity;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.OnClick;


public class MineFragment extends BaseLazyLoadFragment<MinePresenter> implements MineContract.View{


    @BindView(R.id.user_icon)
    RoundedImageView userIcon;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_id)
    TextView tvUserId;
    @BindView(R.id.tv_user_level)
    TextView tvUserLevel;
    @BindView(R.id.tv_user_ranking)
    TextView tvUserRanking;

    @Inject
    AppConfig appConfig;

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    protected void lazyLoadData() {
        if (appConfig.isLogin()) {
            mPresenter.loadCoin();
        }
    }


    @OnClick(R.id.user_icon)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_icon:
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 登录成功
     */
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onLoginSuccess(Event event) {
        if (null != event && (event.getEventCode() == Const.EventCode.LOGIN_SUCCESS || event.getEventCode() == Const.EventCode.LOGIN_RETURN)) {
            //Toast.makeText(getContext(),"success",Toast.LENGTH_LONG).show();
            mPresenter.loadCoin();
        }
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMineComponent.builder().appComponent(appComponent).build().inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void showCoin(Coin data) {
        tvUserName.setText(appConfig.getUserName());
        tvUserLevel.setText(data.getLevelStr());
        tvUserId.setText(data.getIdStr());
        tvUserRanking.setText(data.getFormatRank());
        Toast.makeText(getContext(),"啥玩意啊",Toast.LENGTH_LONG).show();
    }


    public void showLogoutSuccess() {
        tvUserName.setText("");
        tvUserLevel.setText("");
        tvUserId.setText("");
        tvUserRanking.setText("");
        appConfig.clear();
        EventBusManager.getInstance().post(new Event<>(Const.EventCode.LOG_OUT, null));
    }

}