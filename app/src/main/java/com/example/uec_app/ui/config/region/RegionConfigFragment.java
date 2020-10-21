package com.example.uec_app.ui.config.region;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavAction;
import androidx.navigation.Navigation;

import com.example.uec_app.R;
import com.example.uec_app.model.Region;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class RegionConfigFragment extends Fragment {

    @Inject
    RegionConfigViewModel regionConfigViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_config_region,container,false);
        Button addButton = root.findViewById(R.id.add_button);
        TextView backText = root.findViewById(R.id.back_text);
        ListView listView = root.findViewById(R.id.config_list_view);


//        List<Map<String,Object>> regionList = regionConfigViewModel.getRegionList().getValue().stream().map(Region->Region.toMap()).collect(Collectors.toList());
        List<Region> regionList = regionConfigViewModel.getRegionList().getValue();
 //       SimpleAdapter adapter = new SimpleAdapter(getContext(), regionList, R.layout.config_item_region,new String[]{"id","name","dev_num","time","comment"},new int[]{R.id.region_id,R.id.region_name,R.id.region_dev_num,R.id.region_time,R.id.region_comment});
        RegionAdapter adapter = new RegionAdapter(getContext(),getParentFragmentManager(),regionConfigViewModel);
        listView.setAdapter(adapter);

        regionConfigViewModel.getRegionList().observe(getViewLifecycleOwner(), new Observer<List<Region>>() {
            @Override
            public void onChanged(List<Region> regions) {
                adapter.notifyDataSetChanged();
            }
        });

        backText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_regionConfigFragment_to_navigation_config);
            }
        });


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new AddRegionDialog();
                dialogFragment.show(getParentFragmentManager(),"add_region_dialog");
            }
        });

        regionConfigViewModel.refresh();
        return root;
    }
}
