package com.example.uec_app.ui.alarm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.uec_app.R;
import com.example.uec_app.model.Alarm;
import com.example.uec_app.model.DssData;
import com.example.uec_app.ui.dashboard.DashboardAdapter;
import com.example.uec_app.ui.dashboard.DashboardViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class AlarmFragment extends Fragment {

    @Inject
    AlarmViewModel alarmViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //获取视图
        View root = inflater.inflate(R.layout.fragment_alarm, container, false);
        Spinner resSpinner = root.findViewById(R.id.res_spinner);
        Spinner regionSpinner = root.findViewById(R.id.region_spinner);
        Button refreshButton = root.findViewById(R.id.refresh_button);
        ListView listView = root.findViewById(R.id.alarm_list_view);

        //获取数据
        List<Alarm> alarmList = alarmViewModel.getAlarmList().getValue();


        //绘图
        AlarmAdapter adapter = new AlarmAdapter(getContext(),getParentFragmentManager(),alarmViewModel);
        listView.setAdapter(adapter);


        //绑定事件
        alarmViewModel.getAlarmList().observe(getViewLifecycleOwner(), new Observer<List<Alarm>>() {
            @Override
            public void onChanged(List<Alarm> alarms) {
                adapter.notifyDataSetChanged();
            }
        });
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmViewModel.refresh();
            }
        });

        //刷新数据
        alarmViewModel.refresh();
        return root;
    }

}