package com.example.uec_app.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.uec_app.AAChartCoreLib.AAChartCreator.AAChartModel;
import com.example.uec_app.AAChartCoreLib.AAChartCreator.AAChartView;
import com.example.uec_app.AAChartCoreLib.AAChartCreator.AASeriesElement;
import com.example.uec_app.R;
import com.example.uec_app.model.DssData;

import java.util.List;

public class DataCard extends ConstraintLayout {

    private ImageView typeImage;
    private TextView typeText;
    private TextView deviceText;
    private AAChartView chartView;

    public DataCard(@NonNull Context context) {
        super(context);
    }

    public DataCard(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.data_card,this,true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        typeImage = findViewById(R.id.type_image);
        typeText = findViewById(R.id.type_text);
        deviceText = findViewById(R.id.device);
        chartView = findViewById(R.id.chart);
    }

    public void  setTypeImage(int res){
        typeImage.setImageResource(res);
    }

    public void setTypeText(String text){
        typeText.setText(text);
    }

    public void setDeviceText(String text){
        deviceText.setText(text);
    }

    public void drawChart(AAChartModel aaChartModel){
        chartView.aa_drawChartWithChartModel(aaChartModel);
    }

    public void refreshChart(AASeriesElement[] aaSeriesElements){
        chartView.aa_onlyRefreshTheChartDataWithChartOptionsSeriesArray(aaSeriesElements);
    }


}
