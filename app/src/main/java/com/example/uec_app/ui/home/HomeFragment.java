package com.example.uec_app.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.uec_app.AAChartCoreLib.AAChartCreator.AAChartModel;
import com.example.uec_app.AAChartCoreLib.AAChartCreator.AAChartView;
import com.example.uec_app.AAChartCoreLib.AAChartCreator.AASeriesElement;
import com.example.uec_app.AAChartCoreLib.AAChartEnum.AAChartType;
import com.example.uec_app.R;
import com.example.uec_app.component.DataCard;
import com.example.uec_app.model.DssData;
import com.example.uec_app.ui.SharedViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class HomeFragment extends Fragment {

    @Inject
    HomeViewModel homeViewModel;
    @Inject
    SharedViewModel sharedViewModel;

    private List<DssData> voltageList;
    private DataCard voltageDataCard;
    private Button refreshButton;
    private Button clearButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        voltageList = homeViewModel.getVoltageDataList().getValue();
        Map<String,Object> map = sharedViewModel.getResTypeCount().getValue();

        voltageDataCard = root.findViewById(R.id.voltage_card);
        voltageDataCard.setTypeText("电压");
        voltageDataCard.setDeviceText("设备:" + voltageList.size() + "/" + map.getOrDefault("电压",0));
        String[] categories = new String[voltageList==null?0:voltageList.size()];
        voltageList.stream().map(DssData->DssData.getDssConfig().getName()).collect(Collectors.toList()).toArray(categories);
        AAChartModel aaChartModel = new AAChartModel()
                .chartType(AAChartType.Bar)
                .title("电压数据概览")
                .backgroundColor("#4b2b7f")
                .categories(categories)
                .axesTextColor("#FFFFFF")
                .dataLabelsEnabled(true)
                .xAxisGridLineWidth(1f)
                .series(new AASeriesElement[]{new AASeriesElement().name("电压").data(voltageList.stream().map(DssData->Float.valueOf(DssData.getValue())).toArray())
                });
        voltageDataCard.drawChart(aaChartModel);
        refreshButton = root.findViewById(R.id.refresh_button);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeViewModel.refreshVoltageData();
            }
        });
        clearButton = root.findViewById(R.id.clear_button);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeViewModel.clearDB();
            }
        });
        return root;
    }
}