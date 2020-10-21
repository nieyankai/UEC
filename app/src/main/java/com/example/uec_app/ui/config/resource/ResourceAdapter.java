package com.example.uec_app.ui.config.resource;

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
import com.example.uec_app.model.Region;
import com.example.uec_app.model.ResType;
import com.example.uec_app.ui.config.region.EditRegionDialog;
import com.example.uec_app.ui.config.region.RegionConfigViewModel;

public class ResourceAdapter extends BaseAdapter{

    private Context context;                        //运行上下文
    private FragmentManager fragmentManager;
//    private List<Region> listItems;                 //区域集合
    private ResourceConfigViewModel viewModel;
    private LayoutInflater listContainer;            //视图容器

    public final class ListItemView{                //自定义控件集合
        public TextView res_id;
        public TextView res_name;
        public TextView res_comment;
        public TextView res_unit;
        public TextView res_time;
        public Button edit_button;
        public Button delete_button;
    }

    public ResourceAdapter(Context context, FragmentManager fragmentManager, ViewModel viewModel) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        listContainer = LayoutInflater.from(context);
        this.viewModel = (ResourceConfigViewModel)viewModel;
    }

    @Override
    public int getCount() {
        return viewModel.getResTypeList().getValue().size();
    }

    @Override
    public Object getItem(int position) {
        return viewModel.getResTypeList().getValue().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void removeItem(int position){
        if (viewModel.removeResType(position))
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
            convertView = listContainer.inflate(R.layout.config_item_resource, null);
            //获取控件对象
            listItemView.res_id = (TextView) convertView.findViewById(R.id.res_id);
            listItemView.res_comment = (TextView) convertView.findViewById(R.id.res_comment);
            listItemView.res_name = (TextView) convertView.findViewById(R.id.res_name);
            listItemView.res_unit = (TextView) convertView.findViewById(R.id.res_unit);
            listItemView.res_time = (TextView) convertView.findViewById(R.id.res_time);
            listItemView.edit_button = (Button) convertView.findViewById(R.id.edit_button);
            listItemView.delete_button = (Button) convertView.findViewById(R.id.delete_button);
            //设置控件集到convertView
            convertView.setTag(listItemView);
        }

        listItemView.res_id.setText(String.valueOf(position));
        listItemView.res_name.setText(viewModel.getResTypeList().getValue().get(position).getName());
        listItemView.res_comment.setText(viewModel.getResTypeList().getValue().get(position).getComment());
        listItemView.res_time.setText(viewModel.getResTypeList().getValue().get(position).getTimeString());
        listItemView.res_unit.setText(String.valueOf(viewModel.getResTypeList().getValue().get(position).getUnit()));
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
            DialogFragment dialogFragment = new EditResourceDialog((ResType) getItem(position));
            dialogFragment.show(fragmentManager,"edit_resource_dialog");
        }
    }

}