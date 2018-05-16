package com.thingnoy.thingnoy500v3.ui.employee;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.thingnoy.thingnoy500v3.R;

public class MainEmpActivity extends AppCompatActivity {

    private FrameLayout containerEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_emp);

        bindView();
    }

    private void bindView() {
        containerEmployee = findViewById(R.id.container_main_employee);
    }
}
