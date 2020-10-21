package com.example.uec_app.ui.notifications;

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
import com.example.uec_app.R;
import com.example.uec_app.model.DssData;

import java.util.List;
import java.util.stream.Collectors;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private AAChartView notifyChart;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        Button button = root.findViewById(R.id.dataButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationsViewModel.getList();
            }
        });

        notifyChart = root.findViewById(R.id.notify_chart);

        notificationsViewModel.getList().observe(getViewLifecycleOwner(), new Observer<List<DssData>>() {
            @Override
            public void onChanged(List<DssData> dssData) {

                String[] categories = new String[dssData.size()];
                dssData.stream().map(DssData->DssData.getTimeString()).collect(Collectors.toList()).toArray(categories);

                AAChartModel aaChartModel = new AAChartModel()
                        .chartType(AAChartType.Line)
                        .title("Test data show hahahaha")
                        .titleFontColor("#FFFFFF")
                        .subtitle("Virtual Data")
                        .subtitleFontColor("#FFFFFF")
                        .backgroundColor("#4b2b7f")
                        .categories(categories)
                        .axesTextColor("#FFFFFF")
                        .dataLabelsEnabled(false)
                        .yAxisGridLineWidth(1f)
                        .series(new AASeriesElement[]{
                                new AASeriesElement()
                                        .name("dss_data")
                                        .data(dssData.stream().map(DssData->DssData.getValue()).toArray())
                        });

                notifyChart.aa_drawChartWithChartModel(aaChartModel);
            }
        });


        return root;
    }
}