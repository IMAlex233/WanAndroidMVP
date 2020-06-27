package com.xlu.wanandroidmvp.module.login;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.EventBusManager;
import com.xlu.wanandroidmvp.R;
import com.xlu.wanandroidmvp.common.Const;
import com.xlu.wanandroidmvp.di.component.DaggerLoginComponent;
import com.xlu.wanandroidmvp.event.Event;
import com.xlu.wanandroidmvp.utils.StringUtils;
import com.xlu.wanandroidmvp.utils.ToastUtils;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {


    @BindView(R.id.username)
    EditText eusername;
    @BindView(R.id.password)
    EditText epassword;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.loading)
    ProgressBar loading;

    @Inject
    LoginPresenter loginPresenter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLoginComponent.builder().appComponent(appComponent).view(this).build().inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_login;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        //ButterKnife.bind(this);
    }

    @OnClick(R.id.login)
    public void login(View view) {

        String userName = eusername.getText().toString();
        String password = epassword.getText().toString();
/*        if (StringUtils.isEmpty(userName)) {
            tilUser.setError("请输入账号");
            tilUser.setErrorEnabled(true);
            return;
        } else {
            tilUser.setError("");
            tilUser.setErrorEnabled(false);
        }
        if (StringUtils.isEmpty(password)) {
            tilPassword.setError("请输入密码");
            tilPassword.setErrorEnabled(true);
            return;
        }
        else {
            tilPassword.setError("");
            tilPassword.setErrorEnabled(false);
        }*/
        //Toast.makeText(this,"??",Toast.LENGTH_SHORT).show();
        loginPresenter.login("xlu233", "as13677291420as");
    }

    @Override
    public void loginSuccess() {

        EventBusManager.getInstance().post(new Event<>(Const.EventCode.LOGIN_SUCCESS, null));
        //Toast.makeText(this,"success",Toast.LENGTH_LONG).show();
        onBackPressed();
    }

    @Override
    public void loginFailed() {
        Toast.makeText(this,"Failed",Toast.LENGTH_LONG).show();
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ToastUtils.showShort(message);
    }
}