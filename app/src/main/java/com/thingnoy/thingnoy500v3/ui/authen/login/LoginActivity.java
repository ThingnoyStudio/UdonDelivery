package com.thingnoy.thingnoy500v3.ui.authen.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.api.UdonFoodServiceManager;
import com.thingnoy.thingnoy500v3.api.request.login.LoginBody;
import com.thingnoy.thingnoy500v3.api.request.register.RegisterBody;
import com.thingnoy.thingnoy500v3.api.result.login.LoginResultGroup;
import com.thingnoy.thingnoy500v3.api.result.status.StatusResult;
import com.thingnoy.thingnoy500v3.manager.CacheManager;
import com.thingnoy.thingnoy500v3.ui.employee.MainEmpActivity;
import com.thingnoy.thingnoy500v3.ui.main.home.MainActivity;
import com.thingnoy.thingnoy500v3.ui.authen.register.RegisterActivity;
import com.thingnoy.thingnoy500v3.util.DownloadImage;
import com.thingnoy.thingnoy500v3.util.EncodeImageToBase64;
import com.thingnoy.thingnoy500v3.util.GetPrettyPrintJson;

import java.util.Arrays;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.thingnoy.thingnoy500v3.util.Constant.USERINFO;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();

    private final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static int REQUEST_CODE_REGISTER = 2000;
    private FloatingActionButton fab;
    private TextView tvResetPassword;
    private Button btnLogin;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private AccessTokenTracker accessTokenTracker;
    private AccessToken accessToken;
    private ProfileTracker profileTracker;
    private Profile profile;
    private UdonFoodServiceManager serviceManager;
    private CircleImageView ivLogoLogin;
    private SweetAlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_login);

        serviceManager = UdonFoodServiceManager.getInstance();

        callbackManager = CallbackManager.Factory.create();

        tokenTracker();
        profileTracker();
        bindView();
        setupView();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.e(TAG, "onBackPressed: worked");
//        new AlertDialog.Builder(this)
//                .setTitle("ยืนยันการออก?")
//                .setMessage("ต้องการออกจาก Udon Delivery หรือไม่?")
//                .setNegativeButton(R.string.no, null)
//                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        LoginActivity.super.onBackPressed();
//                        setResult(RESULT_CANCELED, null);
//                    }
//                }).create().show();

//        setResult(RESULT_CANCELED, null);

//        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
        profile = Profile.getCurrentProfile();
        accessToken = AccessToken.getCurrentAccessToken();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }

    private void bindView() {
        fab = findViewById(R.id.fab_register);
        tvResetPassword = findViewById(R.id.tv_reset_password);
        btnLogin = findViewById(R.id.btn_login);
        editTextUsername = findViewById(R.id.et_username);
        editTextPassword = findViewById(R.id.et_password);
        ivLogoLogin = findViewById(R.id.iv_logo_login);

        loginButton = (LoginButton) findViewById(R.id.login_button);

        // If using in a fragment
//        loginButton.setFragment(this);
    }

    private void setupView() {
        fab.setOnClickListener(clickRegisterLayout());
        tvResetPassword.setOnClickListener(clickResetPassword());
        btnLogin.setOnClickListener(clickLogin());

        // Callback registration
        loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));
        loginButton.registerCallback(callbackManager, callback);

    }

    FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            Log.e(TAG, "Facebook Register>> onSuccess" + new GetPrettyPrintJson().getJson(loginResult));

            profile = Profile.getCurrentProfile();

            DownloadImage downloadImage = new DownloadImage();
            downloadImage.setOnDownloadImageListener(new DownloadImage.onDownloadListener() {
                @Override
                public void onDownloadSuccess(Bitmap bmResult) {
                    ivLogoLogin.setImageBitmap(bmResult);

                    RegisterBody body = new RegisterBody();
                    body.setFname(profile.getFirstName());
                    body.setLname(profile.getLastName());
                    body.setIduserface(profile.getId());
                    body.setToken(accessToken.getToken());
                    body.setImg(new EncodeImageToBase64().encodeImage(bmResult));

                    Log.e(TAG, "body: " + new GetPrettyPrintJson().getJson(body));

                    requestAddUser(body);
                }
            });
            downloadImage.execute(profile.getProfilePictureUri(450, 450));
        }

        @Override
        public void onCancel() {
            Log.e(TAG, "Facebook Register>> onCancel");
        }

        @Override
        public void onError(FacebookException error) {
            Log.e(TAG, "Facebook Register>> onError: " + error);
        }
    };


    private void showProgressDialog(boolean isShow) {
        if (isShow) {
            mDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.PROGRESS_TYPE);
            mDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            mDialog.setTitleText("กำลังเข้าสู่ระบบ");
            mDialog.setContentText("กรุณารอสักครู่...");
//        mDialog.setConfirmText("ตกลง");
            mDialog.setCancelable(false);
//        mDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//            @Override
//            public void onClick(SweetAlertDialog sweetAlertDialog) {
//                sweetAlertDialog.dismissWithAnimation();
//                goToLogin(body);
//            }
//        });

            mDialog.show();
        } else {
            mDialog.dismissWithAnimation();
        }

    }

    private void showErrorDialog() {
        showProgressDialog(false);

        mDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE);
        mDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        mDialog.setTitleText("เกิดข้อผิดพลาด");
//        mDialog.setContentText("กรุณารอสักครู่");
        mDialog.setCancelable(false);

        mDialog.show();
    }

    private void showSuccessDialog() {
        showProgressDialog(false);

        mDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.SUCCESS_TYPE);
        mDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        mDialog.setTitleText("ตรวจสอบบัญชีสำเร็จ");
        mDialog.setContentText("กำลังเข้าสู่ระบบ");
//        mDialog.setConfirmText("ไปหน้า เข้าสู่ระบบ");
        mDialog.setCancelable(false);
//        mDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//            @Override
//            public void onClick(SweetAlertDialog sweetAlertDialog) {
//                sweetAlertDialog.dismissWithAnimation();
//
//
//            }
//        });

        mDialog.show();

    }

    private void goToMainActivity() {
        Intent intent = new Intent(LoginActivity.this,
                MainActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean validate(String emailStr, String password) {
//        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        if (emailStr != null && password != null) {
            if (emailStr.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "กรุณาป้อนข้อมูลให้ครบ", Toast.LENGTH_SHORT).show();
            }
            if (password.length() < 5) {
                Toast.makeText(this, "กรุณาป้อนรหัสผ่านมากกว่า 6 ตัว", Toast.LENGTH_SHORT).show();
            }

            return password.length() > 5 && emailStr.length() > 0;

        } else {
            Toast.makeText(this, "กรุณาป้อนข้อมูลให้ครบ", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    private void tokenTracker() {
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                // Set the access token using
                // currentAccessToken when it's loaded or set.
                Log.e(TAG, "tokenTracker>> oldAccessToken: " + new GetPrettyPrintJson().getJson(oldAccessToken));
                Log.e(TAG, "tokenTracker>> currentAccessToken: " + new GetPrettyPrintJson().getJson(currentAccessToken));


            }
        };
        // If the access token is available already assign it.
        accessToken = AccessToken.getCurrentAccessToken();
    }

    private void profileTracker() {
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(
                    Profile oldProfile,
                    Profile currentProfile) {
                // App code
                Log.e(TAG, "profileTracker>> oldProfile:" + new GetPrettyPrintJson().getJson(oldProfile));
                Log.e(TAG, "profileTracker>> currentProfile:" + new GetPrettyPrintJson().getJson(currentProfile));
                profile = currentProfile;

//                RegisterBody body = new RegisterBody();
//                body.setFname(profile.getFirstName());
//                body.setLname(profile.getLastName());
//                body.setIduserface(profile.getId());
//                body.setImg(""+profile.getProfilePictureUri(450,450));
//
//                requestAddUser(body);
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_REGISTER) {
            Log.e(TAG, "onActivityResult requestCode>> ok");
            if (resultCode == RESULT_OK) {
                try {
                    String username = data.getStringExtra("username");
                    String password = data.getStringExtra("password");
//                String baseImage = data.getStringExtra("image");
//                String phone = data.getStringExtra("phone");

                    Log.e(TAG, "onActivityResult resultCode>>  ok --> username: " + username + "password: " + password);

                    LoginBody body = new LoginBody();
                    body.setUsername(username);
                    body.setPass(password);
                    requestLogin(body);

//                    Toast.makeText(this, "Register>> username:" + username + " password: " + password, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e(TAG, "onActivityResult>> in process resultCode ok has Error: " + e.getMessage());
                    e.printStackTrace();
                }

            } else if (resultCode == RESULT_CANCELED) {
                //TODO Handle Result Cancel
                Log.e(TAG, "onActivityResult resultCode>> cancel");
            }

        } else {
            Log.e(TAG, "onActivityResult other...");
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void requestAddUser(RegisterBody body) {
        serviceManager.requestAddUser(body, new UdonFoodServiceManager.UdonFoodManagerCallback<StatusResult>() {
            @Override
            public void onSuccess(StatusResult result) {
                Log.e(TAG, "requestAddUser>> onSuccess: " + new GetPrettyPrintJson().getJson(result));

                LoginBody loginBody = new LoginBody();
                loginBody.setIduserface(profile.getId());
                loginBody.setToken(accessToken.getToken());
                requestLogin(loginBody);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "requestAddUser>> onFailure: " + t.getMessage());

                LoginBody loginBody = new LoginBody();
                loginBody.setIduserface(profile.getId());
                loginBody.setToken(accessToken.getToken());
                requestLogin(loginBody);

            }
        });

    }

    private void requestLogin(LoginBody body) {
        showProgressDialog(true);
        serviceManager.requestLogin(body, new UdonFoodServiceManager.UdonFoodManagerCallback<LoginResultGroup>() {
            @Override
            public void onSuccess(LoginResultGroup result) {
                if (mDialog != null){
                    showSuccessDialog();
                }

                Log.e(TAG, "requestLogin>> onSuccess: " + new GetPrettyPrintJson().getJson(result));

                //                Toast.makeText(LoginActivity.this, "login success", Toast.LENGTH_SHORT).show();
                new CacheManager<LoginResultGroup>().saveCache(result, LoginResultGroup.class, "" + USERINFO);

                if (result.getData().get(0).getName().getLoginType().equals("พนักงานจัดส่ง")) {
                    Log.e(TAG, "login by messenger " + result.getData().get(0).getName().getLoginType());
                    goToMainEmployeeActivity();
                } else {
                    Log.e(TAG, "login by messenger");
                    goToMainActivity();
                }

            }

            @Override
            public void onFailure(Throwable t) {
//                Toast.makeText(LoginActivity.this, "login failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                showErrorDialog();
                mDialog.setContentText("เข้าสู่ระบบไม่สำเร็จ: " + t.getMessage());
                mDialog.showConfirmButton(true);
                Log.e(TAG, "requestLogin>> onFailure: " + t.getMessage());
            }
        });

    }

    private void goToMainEmployeeActivity() {
        Intent intent = new Intent(LoginActivity.this,
                MainEmpActivity.class);
        startActivity(intent);
        finish();
    }

    private View.OnClickListener clickLogin() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "loginClick");
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                if (validate(username, password)) {
//                    todo:login

                    LoginBody body = new LoginBody();
                    body.setUsername(username);
                    body.setPass(password);

                    requestLogin(body);
                }
            }
        };
    }

    private View.OnClickListener clickResetPassword() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
    }

    private View.OnClickListener clickRegisterLayout() {
        return new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setExitTransition(null);
                    getWindow().setEnterTransition(null);

                    ActivityOptionsCompat options =
                            ActivityOptionsCompat.makeSceneTransitionAnimation(LoginActivity.this, fab, fab.getTransitionName());

                    Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                    startActivityForResult(i, REQUEST_CODE_REGISTER, options.toBundle());
//                    startActivityForResult(new Intent(getApplicationContext(), RegisterActivity.class),
//                            REQUEST_CODE_REGISTER, options.toBundle());

                } else {
                    Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                    startActivityForResult(i, REQUEST_CODE_REGISTER);
                }
            }
        };
    }
}
