package com.example.uec_app.ui.config.region;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModel;

import com.example.uec_app.R;
import com.example.uec_app.model.Region;
import com.example.uec_app.repository.DssRepository;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class RegionAdapter extends BaseAdapter{

    private Context context;                        //运行上下文
    private FragmentManager fragmentManager;
//    private List<Region> listItems;                 //区域集合
    private RegionConfigViewModel viewModel;
    private LayoutInflater listContainer;            //视图容器



    public final class ListItemView{                //自定义控件集合
        public TextView region_id;
        public TextView region_name;
        public TextView region_comment;
        public TextView region_dev_num;
        public TextView region_time;
        public Button edit_button;
        public Button delete_button;
    }

    public RegionAdapter(Context context,FragmentManager fragmentManager, ViewModel viewModel) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        listContainer = LayoutInflater.from(context);
        this.viewModel = (RegionConfigViewModel)viewModel;
    }

    @Override
    public int getCount() {
        return viewModel.getRegionList().getValue().size();
    }

    @Override
    public Object getItem(int position) {
        return viewModel.getRegionList().getValue().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void removeItem(int position){
        if (viewModel.removeRegion(position))
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
            convertView = listContainer.inflate(R.layout.config_item_region, null);
            //获取控件对象
            listItemView.region_id = (TextView) convertView.findViewById(R.id.region_id);
            listItemView.region_comment = (TextView) convertView.findViewById(R.id.region_comment);
            listItemView.region_name = (TextView) convertView.findViewById(R.id.region_name);
            listItemView.region_dev_num = (TextView) convertView.findViewById(R.id.region_dev_num);
            listItemView.region_time = (TextView) convertView.findViewById(R.id.region_time);
            listItemView.edit_button = (Button) convertView.findViewById(R.id.edit_button);
            listItemView.delete_button = (Button) convertView.findViewById(R.id.delete_button);
            //设置控件集到convertView
            convertView.setTag(listItemView);
        }

        listItemView.region_id.setText(String.valueOf(position));
        listItemView.region_name.setText(viewModel.getRegionList().getValue().get(position).getName());
        listItemView.region_comment.setText(viewModel.getRegionList().getValue().get(position).getComment());
        listItemView.region_time.setText(viewModel.getRegionList().getValue().get(position).getTimeString());
        listItemView.region_dev_num.setText(String.valueOf(viewModel.getRegionList().getValue().get(position).getDev_num()));
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
            DialogFragment dialogFragment = new EditRegionDialog((Region) getItem(position));
            dialogFragment.show(fragmentManager,"edit_region_dialog");
        }
    }

}