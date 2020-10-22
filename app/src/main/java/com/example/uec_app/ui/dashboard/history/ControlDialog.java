package com.example.uec_app.ui.dashboard.history;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.uec_app.R;
import com.example.uec_app.model.DssConfig;
import com.example.uec_app.repository.DssRepository;
import com.example.uec_app.ui.config.dss.DssConfigViewModel;
import com.example.uec_app.ui.config.region.RegionConfigViewModel;
import com.example.uec_app.ui.config.resource.ResourceConfigViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ControlDialog extends DialogFragment {

    @Inject
    DssRepository dssRepository;
    DssConfig dssConfig;

    public ControlDialog(DssConfig dssConfig){
        this.dssConfig = dssConfig;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        //获取视图
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View root = inflater.inflate(R.layout.dialog_control,null);
        Spinner typeSpinner = root.findViewById(R.id.control_type);
        Spinner valueSpinner = root.findViewById(R.id.control_value);
        EditText minuteText = root.findViewById(R.id.control_minute);
        EditText secondText = root.findViewById(R.id.control_second);

        //获取数据
        List<String> typeList = new ArrayList<>();
        typeList.add("立即控制");
        typeList.add("定时控制");
        List<String> valueList = new ArrayList<>();
        valueList.add("关");
        valueList.add("开");

        //显示
        ArrayAdapter typeAdapter = new ArrayAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item,typeList);
        typeSpinner.setAdapter(typeAdapter);

        ArrayAdapter valueAdapter = new ArrayAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item,valueList);
        valueSpinner.setAdapter(valueAdapter);

        //事件绑定
        builder.setView(root).setPositiveButton("执行", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String type = typeSpinner.getSelectedItem().toString();
                String value = valueSpinner.getSelectedItem().toString();
                int time = Integer.parseInt(minuteText.getText().toString())*60+Integer.parseInt(secondText.getText().toString());

                if(dssRepository.control(dssConfig,type,value,time)) {
                    Toast.makeText(getContext(), "执行成功", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getContext(),"执行失败",Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ControlDialog.this.getDialog().cancel();
            }
        });

        return builder.create();
    }
}
