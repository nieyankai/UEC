package com.example.uec_app.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.uec_app.R;
import com.example.uec_app.ui.config.region.AddRegionDialog;
import com.example.uec_app.ui.login.LoginDialog;

import java.util.List;




public class MyToolBar extends Toolbar {
    private TextView timeText;
    private Spinner regionSpinner;
    private Button loginButton;

    public MyToolBar(@NonNull Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.tool_bar,this,true);
    }

    public MyToolBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.tool_bar,this,true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        timeText = findViewById(R.id.time);
        regionSpinner = findViewById(R.id.region);
        loginButton = findViewById(R.id.login_button);

    }

    public void setTimeText(String text){
        timeText.setText(text);
    }

    public void setLoginButtonText(String text){
        loginButton.setText(text);
    }

    public void setRegionSpinnerAdapter(SpinnerAdapter adapter){
       regionSpinner.setAdapter(adapter);
    }

    public void setRegionSpinnerOnItemSelectListener(Spinner.OnItemSelectedListener listener){
        regionSpinner.setOnItemSelectedListener(listener);
    }

    public void setLoginButtonOnClickListener(OnClickListener listener){
        loginButton.setOnClickListener(listener);
    }
}
