package com.example.uec_app.ui.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.example.uec_app.R;
import com.example.uec_app.model.DssConfig;
import com.example.uec_app.model.DssData;
import com.example.uec_app.ui.config.dss.DssConfigViewModel;
import com.example.uec_app.ui.config.dss.EditDssConfigDialog;

public class DashboardAdapter extends BaseAdapter{

    private Context context;                        //运行上下文
    private FragmentManager fragmentManager;
    private DashboardViewModel viewModel;
    private LayoutInflater listContainer;            //视图容器



    public final class ListItemView{                //自定义控件集合
        public TextView dss_id;
        public TextView dss_name;
        public TextView dss_region;
        public TextView dss_status;
        public TextView dss_value;
        public TextView dss_time;
        public Button check_button;
    }

    public DashboardAdapter(Context context, FragmentManager fragmentManager, ViewModel viewModel) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        listContainer = LayoutInflater.from(context);
        this.viewModel = (DashboardViewModel) viewModel;
    }

    @Override
    public int getCount() {
        return viewModel.getLatestDataList().getValue().size();
    }

    @Override
    public Object getItem(int position) {
        return viewModel.getLatestDataList().getValue().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void removeItem(int position){
        if (viewModel.removeDssData(position))
            Toast.makeText(context,"删除成功",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context,"删除失败",Toast.LENGTH_SHORT).show();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ListItemView  listItemView = null;
        if (convertView != null){
            listItemView = (ListItemView)convertView.getTag();
        }
        else {
            listItemView = new ListItemView();
            convertView = listContainer.inflate(R.layout.dashboard_item_dss, null);
            //获取控件对象
            listItemView.dss_id = (TextView) convertView.findViewById(R.id.dss_id);
            listItemView.dss_name = (TextView) convertView.findViewById(R.id.dss_name);
            listItemView.dss_region = (TextView) convertView.findViewById(R.id.dss_region);
            listItemView.dss_status = (TextView) convertView.findViewById(R.id.dss_status);
            listItemView.dss_value = (TextView) convertView.findViewById(R.id.dss_value);
            listItemView.dss_time = (TextView) convertView.findViewById(R.id.dss_time);
            listItemView.check_button = (Button) convertView.findViewById(R.id.check_button);
            //设置控件集到convertView
            convertView.setTag(listItemView);
        }

        DssData dssData = viewModel.getLatestDataList().getValue().get(position);
        listItemView.dss_id.setText(String.valueOf(position));
        listItemView.dss_name.setText(dssData.getDssConfig().getName());
        listItemView.dss_status.setText("正常");
        listItemView.dss_region.setText(dssData.getDssConfig().getRegion().getName());
        listItemView.dss_value.setText(dssData.getValue());
        listItemView.dss_time.setText(dssData.getTimeString());
        listItemView.check_button.setOnClickListener(new CheckButtonListener(position));

        return convertView;
    }


    class CheckButtonListener implements View.OnClickListener {
        private int position;
        CheckButtonListener(int pos) {
            position = pos;
        }
        @Override
        public void onClick(View v) {
            //Todo navigation to historydata
            viewModel.setCurrentDss(viewModel.getLatestDataList().getValue().get(position).getDssConfig());
            Navigation.findNavController(v).navigate(R.id.action_dashboardFragment_to_dataHistoryFragment);
        }
    }

}