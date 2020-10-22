package com.example.uec_app.ui.alarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.example.uec_app.R;
import com.example.uec_app.model.Alarm;
import com.example.uec_app.model.DssData;
import com.example.uec_app.ui.dashboard.DashboardViewModel;

public class AlarmAdapter extends BaseAdapter{

    private Context context;                        //运行上下文
    private FragmentManager fragmentManager;
    private AlarmViewModel viewModel;
    private LayoutInflater listContainer;            //视图容器



    public final class ListItemView{                //自定义控件集合
        public TextView alarm_value;
        public TextView alarm_type;
        public TextView alarm_location;
        public TextView alarm_time;
        public Button rm_button;
    }

    public AlarmAdapter(Context context, FragmentManager fragmentManager, ViewModel viewModel) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        listContainer = LayoutInflater.from(context);
        this.viewModel = (AlarmViewModel) viewModel;
    }

    @Override
    public int getCount() {
        return viewModel.getAlarmList().getValue().size();
    }

    @Override
    public Object getItem(int position) {
        return viewModel.getAlarmList().getValue().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void removeItem(int position){
        if (viewModel.getAlarmList().getValue().get(position).getResolved().equals("已解除")){
            Toast.makeText(context,"已经解除",Toast.LENGTH_SHORT).show();
            return;
        }
        if (viewModel.resolveAlarm(position))
            Toast.makeText(context,"解除成功",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context,"解除失败",Toast.LENGTH_SHORT).show();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ListItemView  listItemView = null;
        if (convertView != null){
            listItemView = (ListItemView)convertView.getTag();
        }
        else {
            listItemView = new ListItemView();
            convertView = listContainer.inflate(R.layout.alarm_item, null);
            //获取控件对象
            listItemView.alarm_value = (TextView) convertView.findViewById(R.id.alarm_value);
            listItemView.alarm_type = (TextView) convertView.findViewById(R.id.alarm_time);
            listItemView.alarm_location = (TextView) convertView.findViewById(R.id.alarm_location);
            listItemView.alarm_type = (TextView) convertView.findViewById(R.id.alarm_type);
            listItemView.alarm_time = (TextView) convertView.findViewById(R.id.alarm_time);
            listItemView.rm_button = (Button) convertView.findViewById(R.id.remove_button);
            //设置控件集到convertView
            convertView.setTag(listItemView);
        }

        Alarm alarm = viewModel.getAlarmList().getValue().get(position);
        listItemView.alarm_value.setText(alarm.getValue());
        listItemView.alarm_type.setText(alarm.getType());
        listItemView.alarm_location.setText(alarm.getRegion() + alarm.getDss());
        listItemView.alarm_time.setText(alarm.getTimeString());
        listItemView.rm_button.setText(alarm.getResolved());
        listItemView.rm_button.setOnClickListener(new RmButtonListener(position));
        return convertView;
    }


    class RmButtonListener implements View.OnClickListener {
        private int position;
        RmButtonListener(int pos) {
            position = pos;
        }
        @Override
        public void onClick(View v) {
            removeItem(position);
        }
    }

}