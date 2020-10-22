package com.example.uec_app.ui.config.dss;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.uec_app.R;
import com.example.uec_app.model.DssConfig;
import com.example.uec_app.model.Region;
import com.example.uec_app.model.ResType;
import com.example.uec_app.repository.DssRepository;
import com.example.uec_app.ui.config.region.RegionConfigViewModel;
import com.example.uec_app.ui.config.resource.ResourceConfigViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddDssConfigDialog extends DialogFragment {

    @Inject
    DssRepository dssRepository;

    @Inject
    DssConfigViewModel dssConfigViewModel;
    @Inject
    ResourceConfigViewModel resourceConfigViewModel;
    @Inject
    RegionConfigViewModel regionConfigViewModel;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View root = inflater.inflate(R.layout.dialog_dss,null);
        EditText nameText = root.findViewById(R.id.dss_name);
        EditText ipText = root.findViewById(R.id.dss_ip);
        EditText dssText = root.findViewById(R.id.dss_dss);
        EditText groupText = root.findViewById(R.id.dss_group);
        EditText upperText = root.findViewById(R.id.dss_upper);
        EditText lowerText = root.findViewById(R.id.dss_lower);
        Spinner resSpinner = root.findViewById(R.id.dss_res);
        Spinner regionSpinner = root.findViewById(R.id.dss_region);


        resourceConfigViewModel.refresh();
        List<String> resList = new ArrayList<>();
        resList.addAll(resourceConfigViewModel.getResTypeList().getValue().stream().map(ResType->ResType.getName()).collect(Collectors.toList()));
        ArrayAdapter resAdapter = new ArrayAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item,resList);
        resSpinner.setAdapter(resAdapter);


        List<String> regionList = new ArrayList<>();
        regionList.addAll(regionConfigViewModel.getRegionList().getValue().stream().map(Region->Region.getName()).collect(Collectors.toList()));
        ArrayAdapter regionAdapter = new ArrayAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item,regionList);
        regionSpinner.setAdapter(regionAdapter);

        builder.setView(root).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(dssRepository.addDssConfig(new DssConfig(nameText.getText().toString(),regionConfigViewModel.getRegionList().getValue().get(regionSpinner.getSelectedItemPosition()),ipText.getText().toString(),groupText.getText().toString(),dssText.getText().toString(),resourceConfigViewModel.getResTypeList().getValue().get(resSpinner.getSelectedItemPosition()),upperText.getText().toString(),lowerText.getText().toString()))) {
                    Toast.makeText(getContext(), "添加成功", Toast.LENGTH_SHORT).show();
                    dssConfigViewModel.refresh();
                }
                else
                    Toast.makeText(getContext(),"添加失败",Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AddDssConfigDialog.this.getDialog().cancel();
            }
        });


        return builder.create();
    }
}
