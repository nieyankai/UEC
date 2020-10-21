package com.example.uec_app.ui.config;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.Navigation;

import com.example.uec_app.R;
import com.example.uec_app.ui.config.region.AddRegionDialog;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class ConfigFragment extends Fragment {

    @Inject
    ConfigViewModel configViewModel;

    List<Map<String,Object>> configList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_config_root,container,false);



        configViewModel.refreshData();

        ListView configListView = root.findViewById(R.id.config_list_view);
        configList = configViewModel.getConfigItems().getValue();
        SimpleAdapter adapter = new SimpleAdapter(requireContext(),configList,R.layout.config_item_root,new String[]{"name","button"},new int[]{R.id.config_item_name,R.id.config_item_button});
        configListView.setAdapter(adapter);

        configViewModel.getConfigItems().observe(getViewLifecycleOwner(), new Observer<List<Map<String, Object>>>() {
            @Override
            public void onChanged(List<Map<String, Object>> maps) {
                configList = maps;
                adapter.notifyDataSetChanged();
            }
        });

        configListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (configList.get(position).get("name").toString()){
                    case "区域配置":
                        Navigation.findNavController(view).navigate(R.id.action_navigation_config_to_regionConfigFragment);
                        break;
                    case "资源配置":
                        Navigation.findNavController(view).navigate(R.id.action_navigation_config_to_resConfigFragment);
                        break;
                    case "数据源配置":
                        Navigation.findNavController(view).navigate(R.id.action_navigation_config_to_dssConfigFragment);
                        break;
                    default:
                        Toast.makeText(getContext(),configList.get(position).get("name").toString(),Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });


        return root;
    }
}
