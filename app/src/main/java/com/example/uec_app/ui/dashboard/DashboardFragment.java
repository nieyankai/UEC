package com.example.uec_app.ui.dashboard;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.uec_app.R;
import com.example.uec_app.model.DssConfig;
import com.example.uec_app.model.DssData;
import com.example.uec_app.ui.config.dss.DssConfigAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class DashboardFragment extends Fragment {

    @Inject
    DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //获取视图
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        Spinner resSpinner = root.findViewById(R.id.res_spinner);
        Spinner regionSpinner = root.findViewById(R.id.region_spinner);
        ListView listView = root.findViewById(R.id.dss_list_view);

        //获取数据
        List<DssData> dssDataList = dashboardViewModel.getLatestDataList().getValue();


        //绘图
        DashboardAdapter adapter = new DashboardAdapter(getContext(),getParentFragmentManager(),dashboardViewModel);
        listView.setAdapter(adapter);


        //绑定时间
        dashboardViewModel.getLatestDataList().observe(getViewLifecycleOwner(), new Observer<List<DssData>>() {
            @Override
            public void onChanged(List<DssData> dssData) {
                adapter.notifyDataSetChanged();
            }
        });




        //刷新数据
        dashboardViewModel.refresh();
        return root;
    }

}