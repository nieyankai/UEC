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
import com.example.uec_app.ui.alarm.AlarmViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class HomeFragment extends Fragment {

    @Inject
    HomeViewModel homeViewModel;
    @Inject
    AlarmViewModel alarmViewModel;
    @Inject
    SharedViewModel sharedViewModel;

    private List<DssData> voltageList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //获取视图
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        Button refreshButton = root.findViewById(R.id.refresh_button);
        Button clearButton = root.findViewById(R.id.clear_button);
        TextView alarmAllText = root.findViewById(R.id.alarm_all);
        TextView alarmWaitText = root.findViewById(R.id.alarm_wait);
        TextView alarmResolvedText = root.findViewById(R.id.alarm_resolved);

        List<DataCard> dataCards = new ArrayList<>();
        dataCards.add(root.findViewById(R.id.card0));
        dataCards.add(root.findViewById(R.id.card1));
        dataCards.add(root.findViewById(R.id.card2));
        dataCards.add(root.findViewById(R.id.card3));

        //获取数据

        //绘图
        alarmAllText.setText(alarmViewModel.getAlarmAll().getValue().toString());
        alarmWaitText.setText(alarmViewModel.getAlarmWait().getValue().toString());
        alarmResolvedText.setText(alarmViewModel.getAlarmResolved().getValue().toString());

        ListIterator<DataCard> cardListIterator = dataCards.listIterator();
        for (Map.Entry<String,List<DssData>> entry: homeViewModel.getDataListMap().getValue().entrySet()) {
            if (!cardListIterator.hasNext())
                break;
            DataCard dataCard = cardListIterator.next();
            dataCard.setTypeText(entry.getKey());
            dataCard.setDeviceText("设备:" + entry.getValue().size() + "/" + sharedViewModel.getResTypeCount().getValue().getOrDefault(entry.getKey(),0));
            AAChartModel aaChartModel = new AAChartModel()
                    .chartType(AAChartType.Bar)
                    .title(entry.getKey() + "数据概览")
                    .backgroundColor("#4b2b7f")
                    .categories(entry.getValue().stream().map(DssData->DssData.getDssConfig().getName()).collect(Collectors.toList()).toArray(new String[entry.getValue().size()]))
                    .axesTextColor("#FFFFFF")
                    .dataLabelsEnabled(true)
                    .xAxisGridLineWidth(1f)
                    .series(new AASeriesElement[]{new AASeriesElement().name(entry.getKey()).data(entry.getValue().stream().map(DssData->Float.parseFloat(DssData.getValue())).toArray())
                    });
            dataCard.drawChart(aaChartModel);
        }
        //绑定事件
        homeViewModel.getDataListMap().observe(getViewLifecycleOwner(), new Observer<Map<String, List<DssData>>>() {
            @Override
            public void onChanged(Map<String, List<DssData>> stringListMap) {
                ListIterator<DataCard> cardListIterator = dataCards.listIterator();
                for (Map.Entry<String,List<DssData>> entry: stringListMap.entrySet()) {
                    if (!cardListIterator.hasNext())
                        break;
                    DataCard dataCard = cardListIterator.next();
                    dataCard.setTypeText(entry.getKey());
                    dataCard.setDeviceText("设备:" + entry.getValue().size() + "/" + sharedViewModel.getResTypeCount().getValue().getOrDefault(entry.getKey(),0));
                    AAChartModel aaChartModel = new AAChartModel()
                            .chartType(AAChartType.Bar)
                            .title(entry.getKey() + "数据概览")
                            .backgroundColor("#4b2b7f")
                            .categories(entry.getValue().stream().map(DssData->DssData.getDssConfig().getName()).collect(Collectors.toList()).toArray(new String[entry.getValue().size()]))
                            .axesTextColor("#FFFFFF")
                            .dataLabelsEnabled(true)
                            .xAxisGridLineWidth(1f)
                            .series(new AASeriesElement[]{new AASeriesElement().name(entry.getKey()).data(entry.getValue().stream().map(DssData->Float.parseFloat(DssData.getValue())).toArray())
                            });
                    dataCard.drawChart(aaChartModel);
                }
            }
        });

        alarmViewModel.getAlarmAll().observe(getViewLifecycleOwner(), new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                alarmAllText.setText("全部:" + aLong.toString());
            }
        });

        alarmViewModel.getAlarmWait().observe(getViewLifecycleOwner(), new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                alarmWaitText.setText("待解除:" + aLong.toString());
            }
        });

        alarmViewModel.getAlarmResolved().observe(getViewLifecycleOwner(), new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                alarmResolvedText.setText("已解除:" + aLong.toString());
            }
        });


        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeViewModel.refresh();
            }
        });
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeViewModel.clearDB();
            }
        });



        return root;
    }



}