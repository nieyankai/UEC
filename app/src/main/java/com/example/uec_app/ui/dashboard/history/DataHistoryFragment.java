package com.example.uec_app.ui.dashboard.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import com.example.uec_app.AAChartCoreLib.AAChartCreator.AAChartModel;
import com.example.uec_app.AAChartCoreLib.AAChartCreator.AAChartView;
import com.example.uec_app.AAChartCoreLib.AAChartCreator.AASeriesElement;
import com.example.uec_app.AAChartCoreLib.AAChartEnum.AAChartType;
import com.example.uec_app.R;
import com.example.uec_app.model.DssData;
import com.example.uec_app.ui.SharedViewModel;
import com.example.uec_app.ui.dashboard.DashboardViewModel;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class DataHistoryFragment extends Fragment {

    @Inject
    DataHistoryViewModel dataHistoryViewModel;
    @Inject
    DashboardViewModel dashboardViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //获取视图
        View root = inflater.inflate(R.layout.fragment_dashboard_history,container,false);
        Button refreshButton = root.findViewById(R.id.refresh_button);
        Button controlButton = root.findViewById(R.id.control_button);
        TextView backText = root.findViewById(R.id.back_text);
        AAChartView chartView = root.findViewById(R.id.history_data_chart);


        //获取数据


        //绘制图像
        AAChartModel aaChartModel = new AAChartModel()
                .chartType(AAChartType.Line)
                .title(dashboardViewModel.getCurrentDss().getValue().getName())
                .titleFontSize(18f)
                .titleFontColor("#FFFFFF")
                .backgroundColor("#4b2b7f")
                .categories(dataHistoryViewModel.getDssDataList().getValue().stream().map(DssData->DssData.getTimeString()).collect(Collectors.toList()).toArray(new String[dataHistoryViewModel.getDssDataList().getValue().size()]))
                .axesTextColor("#FFFFFF")
                .dataLabelsEnabled(true)
                .yAxisGridLineWidth(1f)
                .yAxisTitle("单位:"+dashboardViewModel.getCurrentDss().getValue().getResType().getUnit())
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .name(dashboardViewModel.getCurrentDss().getValue().getName())
                                .data(dataHistoryViewModel.getDssDataList().getValue().stream().map(DssData->Float.valueOf(DssData.getValue())).toArray())
                });
        chartView.aa_drawChartWithChartModel(aaChartModel);



        //绑定事件
        dataHistoryViewModel.getDssDataList().observe(getViewLifecycleOwner(), new Observer<List<DssData>>() {
            @Override
            public void onChanged(List<DssData> dssData) {
                AAChartModel model = new AAChartModel()
                        .chartType(AAChartType.Line)
                        .title(dashboardViewModel.getCurrentDss().getValue().getName())
                        .titleFontSize(18f)
                        .titleFontColor("#FFFFFF")
                        .backgroundColor("#4b2b7f")
                        .categories(dataHistoryViewModel.getDssDataList().getValue().stream().map(DssData->DssData.getTimeString()).collect(Collectors.toList()).toArray(new String[dataHistoryViewModel.getDssDataList().getValue().size()]))
                        .axesTextColor("#FFFFFF")
                        .dataLabelsEnabled(true)
                        .yAxisGridLineWidth(1f)
                        .yAxisTitle("单位:"+dashboardViewModel.getCurrentDss().getValue().getResType().getUnit())
                        .series(new AASeriesElement[]{
                                new AASeriesElement()
                                        .name(dashboardViewModel.getCurrentDss().getValue().getName())
                                        .data(dataHistoryViewModel.getDssDataList().getValue().stream().map(DssData->Float.valueOf(DssData.getValue())).toArray())
                        });
                chartView.aa_drawChartWithChartModel(model);
            }
        });

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataHistoryViewModel.refresh(dashboardViewModel.getCurrentDss().getValue());
            }
        });

        controlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new ControlDialog(dashboardViewModel.getCurrentDss().getValue());
                dialogFragment.show(getParentFragmentManager(),"control_dialog");
            }
        });



        backText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_dataHistoryFragment_to_dashboardFragment);
            }
        });

        //更新数据
        dataHistoryViewModel.refresh(dashboardViewModel.getCurrentDss().getValue());
        return root;
    }

}
