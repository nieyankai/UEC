package com.example.uec_app.ui.config.region;

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
import com.example.uec_app.repository.DssRepository;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class EditRegionDialog extends DialogFragment {

    @Inject
    DssRepository dssRepository;

    @Inject
    RegionConfigViewModel regionConfigViewModel;

    Region region;

    public EditRegionDialog(Region region){
        this.region = region;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View root = inflater.inflate(R.layout.dialog_region,null);
        EditText nameText = root.findViewById(R.id.name);
        EditText commentText = root.findViewById(R.id.comment);

        nameText.setText(region.getName());
        commentText.setText(region.getComment());

        builder.setView(root).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                region.setName(nameText.getText().toString());
                region.setComment(commentText.getText().toString());
                if(dssRepository.updateRegion(region)) {
                    Toast.makeText(getContext(), "修改成功", Toast.LENGTH_SHORT).show();
                    regionConfigViewModel.refresh();
                }
                else
                    Toast.makeText(getContext(),"修改失败",Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditRegionDialog.this.getDialog().cancel();
            }
        });


        return builder.create();
    }
}
