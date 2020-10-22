package com.example.uec_app.ui.login;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.uec_app.R;
import com.example.uec_app.model.Region;
import com.example.uec_app.model.UserInfo;
import com.example.uec_app.repository.DssRepository;
import com.example.uec_app.ui.SharedViewModel;
import com.example.uec_app.ui.config.region.RegionConfigViewModel;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginDialog extends DialogFragment {

    @Inject
    DssRepository dssRepository;

    @Inject
    SharedViewModel sharedViewModel;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        //获取视图
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View root = inflater.inflate(R.layout.dialog_login,null);
        EditText userText = root.findViewById(R.id.user);
        EditText passwordText = root.findViewById(R.id.password);
        TextView warnText = root.findViewById(R.id.warn);



        //绘制视图
        builder.setView(root).setPositiveButton("登录", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UserInfo userInfo = new UserInfo(userText.getText().toString(),passwordText.getText().toString());
                if(dssRepository.checkUser(userInfo)) {
                    Toast.makeText(getContext(), "登录成功", Toast.LENGTH_SHORT).show();
                    warnText.setText("");
                    sharedViewModel.login(userInfo);
                }
                else{
                    Toast.makeText(getContext(),"登录失败",Toast.LENGTH_SHORT).show();
                    warnText.setText("账号密码错误");
                }
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LoginDialog.this.getDialog().cancel();
            }
        });


        return builder.create();
    }
}
