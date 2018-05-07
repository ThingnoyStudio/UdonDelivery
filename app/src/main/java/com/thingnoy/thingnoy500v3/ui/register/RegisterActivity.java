package com.thingnoy.thingnoy500v3.ui.register;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.bumptech.glide.Glide;
import com.cazaea.sweetalert.SweetAlertDialog;
import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.api.UdonFoodServiceManager;
import com.thingnoy.thingnoy500v3.api.request.register.RegisterBody;
import com.thingnoy.thingnoy500v3.api.result.status.StatusResult;
import com.thingnoy.thingnoy500v3.manager.http.bus.Contextor;
import com.thingnoy.thingnoy500v3.ui.login.LoginActivity;
import com.thingnoy.thingnoy500v3.util.DownloadImage;
import com.thingnoy.thingnoy500v3.util.EncodeImageToBase64;
import com.thingnoy.thingnoy500v3.util.GetPrettyPrintJson;
import com.thingnoy.thingnoy500v3.util.GlideApp;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static String STR_EXTRA_ACTION_REGISTER = "register";

    private FloatingActionButton fabClose;
    private Button btnRegister;
    private CardView cvAdd;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextRepeatPassword;
    private CircleImageView civUser;
    private Bitmap bitImageUser;
    private MaterialRippleLayout mrChooseImage;
    private View civPlus;
    private EditText edtPhone;
    private SweetAlertDialog mDialog;
    private UdonFoodServiceManager serviceManager;
    private Uri uriImage;
    private RegisterBody registerBody;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        serviceManager = UdonFoodServiceManager.getInstance();

        bindView();
        setupView();

    }

    private void bindView() {
        fabClose = findViewById(R.id.fab_go_to_login);
        btnRegister = findViewById(R.id.btn_register);

        civPlus = findViewById(R.id.civ_plus);
        civUser = findViewById(R.id.civ_register);
        mrChooseImage = findViewById(R.id.mr_choose_image_profile);

        cvAdd = findViewById(R.id.cv_add);
        editTextUsername = findViewById(R.id.et_username);
        editTextPassword = findViewById(R.id.et_password);
        editTextRepeatPassword = findViewById(R.id.et_repeatpassword);
        edtPhone = findViewById(R.id.edt_phone);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ShowEnterAnimation();
        }
        fabClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    animateRevealClose();
                }
            }
        });
    }

    private void setupView() {
        fabClose.setOnClickListener(clickBackLogin());
        btnRegister.setOnClickListener(clickRegister());

//        civUser.setOnClickListener(onClickImage());
        mrChooseImage.setOnClickListener(onClickImage());
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void animateRevealClose() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(
                cvAdd, cvAdd.getWidth() / 2, 0,
                cvAdd.getHeight(), fabClose.getWidth() / 2);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                cvAdd.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                fabClose.setImageResource(R.drawable.ic_signup);
                RegisterActivity.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void ShowEnterAnimation() {
        Transition transition = null;

        transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setSharedElementEnterTransition(transition);
        }


        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                cvAdd.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    animateRevealShow();
                }
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }


        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void animateRevealShow() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd,
                cvAdd.getWidth() / 2, 0,
                fabClose.getWidth() / 2, cvAdd.getHeight());
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                cvAdd.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            animateRevealClose();
        } else {
            finish();
        }
    }

    private void showSuccessDialog(final RegisterBody body) {
        showProgressDialog(false);

        mDialog = new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.SUCCESS_TYPE);
        mDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        mDialog.setTitleText("ลงทะเบียนสำเร็จ");
        mDialog.setContentText("ลงทะเบียนผู้ใช้งานสำเร็จ");
        mDialog.setConfirmText("ไปหน้า เข้าสู่ระบบ");
        mDialog.setCancelable(false);
        mDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismissWithAnimation();

                goToLogin(body);
//                sweetAlertDialog.dismissWithAnimation();
            }
        });

        mDialog.show();
    }

    private void showProgressDialog(boolean isShow) {
        if (isShow) {
            mDialog = new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.PROGRESS_TYPE);
            mDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            mDialog.setTitleText("กำลังลงทะเบียน");
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

    private void goToLogin(RegisterBody body) {

//        Intent i = new Intent( this, LoginActivity.class );
        Intent data = new Intent();
        data.putExtra("username", body.getUsername());
        data.putExtra("password", body.getPassword());
//        data.putExtra("image", body.getImg());
//        data.putExtra("phone", body.getPhone());
//        data.putExtra("action", STR_EXTRA_ACTION_REGISTER);
//
//
//        setResult( RESULT_OK ,data);
        setResult( RESULT_OK,data);

        onBackPressed();

//        startActivity( data );
//        startActivity( data );

//        Intent data = new Intent();
//        data.putExtra("username", body.getUsername());
//        data.putExtra("password", body.getPassword());
//        data.putExtra("image", body.getImg());
//        data.putExtra("phone", body.getPhone());
//        data.putExtra("action", STR_EXTRA_ACTION_REGISTER);
//        setResult(RESULT_OK, data);
//        onBackPressed();
    }

    private void showErrorDialog() {
        showProgressDialog(false);

        mDialog = new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE);
        mDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        mDialog.setTitleText("เกิดข้อผิดพลาด");
        mDialog.setContentText("กรุณารอสักครู่");
        mDialog.setCancelable(false);

        mDialog.show();
    }

    private void chooseImage() {
        PickSetup setup = new PickSetup();
        PickImageDialog.build(setup, new IPickResult() {
            @Override
            public void onPickResult(PickResult r) {
                r.getBitmap();
                r.getError();
                r.getUri();


                if (r.getError() == null) {
                    //If you want the Uri.
                    //Mandatory to refresh image from Uri.
//            ivReview.setImageURI(null);

                    //Setting the real returned image.
//                        ivReview.setImageURI(r.getUri());

                    //If you want the Bitmap.
                    civUser.setImageBitmap(r.getBitmap());
                    civPlus.setVisibility(View.GONE);
                    bitImageUser = r.getBitmap();
                    uriImage = r.getUri();

                    //r.getPath();

                } else {
                    //Handle possible errors
                    Toast.makeText(getApplicationContext(), "การเลือกรูปเกิดข้อผิดพลาด :" + r.getError().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }).show(getSupportFragmentManager());
    }

    private void requestAddUser(final RegisterBody body) {
        showProgressDialog(true);
        serviceManager.requestAddUser(body, new UdonFoodServiceManager.UdonFoodManagerCallback<StatusResult>() {
            @Override
            public void onSuccess(StatusResult result) {
                showSuccessDialog(body);
                Log.e(TAG, "requestAddUser>> onSuccess: " + new GetPrettyPrintJson().getJson(result));
            }

            @Override
            public void onFailure(Throwable t) {
                showErrorDialog();
                mDialog.setContentText("ลงทะเบียนผู้ใช้งานไม่สำเร็จ: " + t.getMessage());
                mDialog.showConfirmButton(true);
                Log.e(TAG, "requestAddUser>> onFailure: " + t.getMessage());
            }
        });

    }

    private boolean validate(String emailStr, String password, String repeatPassword, String phone, String baseImage) {
//        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
//        return password.length() > 0 && repeatPassword.equals(password) && matcher.find();
        if (emailStr != null && phone != null && baseImage != null && password != null && repeatPassword != null) {
            if (emailStr.isEmpty() || phone.isEmpty() || baseImage.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
                Toast.makeText(this, "กรุณาป้อนข้อมูลให้ครบ", Toast.LENGTH_SHORT).show();
            }
            if (phone.length() < 10) {
                Toast.makeText(this, "เบอร์โทรไม่ถูกต้อง", Toast.LENGTH_SHORT).show();
            }
            if (password.length() > 5) {
                if (!repeatPassword.equals(password)) {
                    Toast.makeText(this, "รหัสผ่านไม่ตรงกัน", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "รหัสผ่านต้องมีอย่างน้อย 6 ตัวขึ้นไป", Toast.LENGTH_SHORT).show();
            }

            return emailStr.length() > 0
                    && password.length() > 5
                    && repeatPassword.equals(password)
                    && phone.length() == 10
                    && baseImage.length() > 0;
        }
        Toast.makeText(this, "กรุณาป้อนข้อมูลให้ครบ", Toast.LENGTH_SHORT).show();
        return false;
    }

    private View.OnClickListener clickRegister() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = editTextUsername.getText().toString().trim();
                final String password = editTextPassword.getText().toString().trim();
                String repeatPassword = editTextRepeatPassword.getText().toString().trim();
                final String phone = edtPhone.getText().toString().trim();
                final String baseImage = new EncodeImageToBase64().encodeImage(bitImageUser);
                if (validate(username, password, repeatPassword, phone, baseImage)) {

                    DownloadImage downloadImage = new DownloadImage();
                    downloadImage.setOnDownloadImageListener(new DownloadImage.onDownloadListener() {
                        @Override
                        public void onDownloadSuccess(Bitmap bmResult) {
                            RegisterBody body = new RegisterBody();
                            body.setUsername(username);
                            body.setPassword(password);
                            body.setImg(new EncodeImageToBase64().encodeImage(bmResult));
                            body.setPhone(phone);

                            registerBody = body;

                            requestAddUser(body);
                        }
                    });
                    downloadImage.execute(uriImage);


                }
//                else {

//                    Toast.makeText(getApplicationContext(), "ชื่อผู้ใช้งานไม่ถูกต้อง หรือ รหัสผ่าน ไม่ตรงกัน", Toast.LENGTH_SHORT).show();
//                }
            }
        };
    }

    private View.OnClickListener clickBackLogin() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
//                if (registerBody != null){
//                    goToLogin(registerBody);
//                }

            }
        };
    }

    private View.OnClickListener onClickImage() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        };
    }
}
