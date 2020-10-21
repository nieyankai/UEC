package com.example.uec_app.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.uec_app.AAChartCoreLib.AAChartCreator.AAChartModel;
import com.example.uec_app.AAChartCoreLib.AAChartCreator.AAChartView;
import com.example.uec_app.AAChartCoreLib.AAChartCreator.AASeriesElement;
import com.example.uec_app.AAChartCoreLib.AAChartEnum.AAChartType;
import com.example.uec_app.AAChartCoreLib.AAOptionsModel.AAChart;
import com.example.uec_app.R;
import com.example.uec_app.model.DssData;
import com.example.uec_app.ui.SharedViewModel;
import com.example.uec_app.ui.notifications.NotificationsViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HistoryFragment extends Fragment {

    @Inject
    HistoryViewModel historyViewModel;

    private AAChartView historyChartView;
    private List<DssData> dssDataList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
 //       historyViewModel.refreshData(sharedViewModel.getCurrentDataName().getValue(),Long.valueOf(10));
        dssDataList = historyViewModel.getDssDataList().getValue();

        View root = inflater.inflate(R.layout.fragment_history_data, container, false);
        historyChartView = root.findViewById(R.id.history_chart);

        String[] categories = new String[dssDataList.size()];
        dssDataList.stream().map(DssData->DssData.getTimeString()).collect(Collectors.toList()).toArray(categories);
        AAChartModel aaChartModel = new AAChartModel()
                .chartType(AAChartType.Line)
                .title("历史数据")
                .titleFontSize(Float.valueOf(18))
                .titleFontColor("#FFFFFF")
                .backgroundColor("#4b2b7f")
                .categories(categories)
                .axesTextColor("#FFFFFF")
                .dataLabelsEnabled(true)
                .yAxisGridLineWidth(1f)
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .name("111")
                                .data(dssDataList.stream().map(DssData->DssData.getValue()).toArray())
                });
        historyChartView.aa_drawChartWithChartModel(aaChartModel);


        historyViewModel.getDssDataList().observe(getViewLifecycleOwner(), new Observer<List<DssData>>() {
            @Override
            public void onChanged(List<DssData> dssData) {
                historyChartView.aa_onlyRefreshTheChartDataWithChartOptionsSeriesArray(new AASeriesElement[]{
                        new AASeriesElement()
                                .name("454")
                                .data(dssData.stream().map(DssData->DssData.getValue()).toArray())
                });

            }
        });



        return root;
    }



}
