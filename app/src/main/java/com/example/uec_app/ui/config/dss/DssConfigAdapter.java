package com.example.uec_app.ui.config.dss;

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

import com.example.uec_app.R;
import com.example.uec_app.model.DssConfig;
import com.example.uec_app.model.Region;
import com.example.uec_app.ui.config.region.EditRegionDialog;
import com.example.uec_app.ui.config.region.RegionConfigViewModel;

public class DssConfigAdapter extends BaseAdapter{

    private Context context;                        //运行上下文
    private FragmentManager fragmentManager;
//    private List<Region> listItems;                 //区域集合
    private DssConfigViewModel viewModel;
    private LayoutInflater listContainer;            //视图容器



    public final class ListItemView{                //自定义控件集合
        public TextView dss_id;
        public TextView dss_name;
        public TextView dss_region;
        public TextView dss_ip;
        public TextView group;
        public TextView dss;
        public TextView dss_res;
        public TextView dss_time;
        public Button edit_button;
        public Button delete_button;
    }

    public DssConfigAdapter(Context context, FragmentManager fragmentManager, ViewModel viewModel) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        listContainer = LayoutInflater.from(context);
        this.viewModel = (DssConfigViewModel) viewModel;
    }

    @Override
    public int getCount() {
        return viewModel.getDssConfigList().getValue().size();
    }

    @Override
    public Object getItem(int position) {
        return viewModel.getDssConfigList().getValue().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void removeItem(int position){
        if (viewModel.removeDssConfig(position))
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
            convertView = listContainer.inflate(R.layout.config_item_dss, null);
            //获取控件对象
            listItemView.dss_id = (TextView) convertView.findViewById(R.id.dss_id);
            listItemView.dss_name = (TextView) convertView.findViewById(R.id.dss_name);
            listItemView.dss_region = (TextView) convertView.findViewById(R.id.dss_region);
            listItemView.dss_ip = (TextView) convertView.findViewById(R.id.ip);
            listItemView.dss = (TextView) convertView.findViewById(R.id.dss);
            listItemView.group = (TextView) convertView.findViewById(R.id.group);
            listItemView.dss_time = (TextView) convertView.findViewById(R.id.dss_time);
            listItemView.dss_res = (TextView) convertView.findViewById(R.id.dss_res);
            listItemView.edit_button = (Button) convertView.findViewById(R.id.edit_button);
            listItemView.delete_button = (Button) convertView.findViewById(R.id.delete_button);
            //设置控件集到convertView
            convertView.setTag(listItemView);
        }

        DssConfig config = viewModel.getDssConfigList().getValue().get(position);
        listItemView.dss_id.setText(String.valueOf(position));
        listItemView.dss_name.setText(config.getName());
        listItemView.dss_ip.setText(config.getIp());
        listItemView.dss.setText(config.getDss());
        listItemView.group.setText(config.getGroup());
        listItemView.dss_region.setText(config.getRegion().getName());
        listItemView.dss_res.setText(config.getResType().getName());
        listItemView.dss_time.setText(config.getTimeString());
        listItemView.delete_button.setOnClickListener(new RmButtonListener(position));
        listItemView.edit_button.setOnClickListener(new EditButtonListener(position));
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
    class EditButtonListener implements View.OnClickListener {
        private int position;

        EditButtonListener(int pos) {
            position = pos;
        }

        @Override
        public void onClick(View v) {
            DialogFragment dialogFragment = new EditDssConfigDialog((DssConfig) getItem(position));
            dialogFragment.show(fragmentManager,"edit_dss_dialog");
        }
    }

}