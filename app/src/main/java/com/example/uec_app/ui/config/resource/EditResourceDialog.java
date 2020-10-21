package com.example.uec_app.ui.config.resource;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.uec_app.R;
import com.example.uec_app.model.Region;
import com.example.uec_app.model.ResType;
import com.example.uec_app.repository.DssRepository;
import com.example.uec_app.ui.config.region.RegionConfigViewModel;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class EditResourceDialog extends DialogFragment {

    @Inject
    DssRepository dssRepository;

    @Inject
    ResourceConfigViewModel resourceConfigViewModel;

    ResType resType;

    public EditResourceDialog(ResType resType){
        this.resType = resType;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View root = inflater.inflate(R.layout.dialog_resource,null);
        EditText nameText = root.findViewById(R.id.res_name);
        EditText commentText = root.findViewById(R.id.res_comment);
        EditText unitText = root.findViewById(R.id.res_unit);

        nameText.setText(resType.getName());
        commentText.setText(resType.getComment());
        unitText.setText(resType.getUnit());

        builder.setView(root).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resType.setName(nameText.getText().toString());
                resType.setComment(commentText.getText().toString());
                resType.setUnit(unitText.getText().toString());
                if(dssRepository.updateResType(resType)) {
                    Toast.makeText(getContext(), "修改成功", Toast.LENGTH_SHORT).show();
                    resourceConfigViewModel.refresh();
                }
                else
                    Toast.makeText(getContext(),"修改失败",Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditResourceDialog.this.getDialog().cancel();
            }
        });
        return builder.create();
    }
}
