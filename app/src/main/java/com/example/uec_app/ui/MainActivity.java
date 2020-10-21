package com.example.uec_app.ui;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import com.example.uec_app.R;
import com.example.uec_app.component.MyToolBar;
import com.example.uec_app.model.Region;
import com.example.uec_app.repository.DssRepository;
import com.example.uec_app.ui.config.region.RegionConfigViewModel;
import com.example.uec_app.util.LogUtil;
import com.example.uec_app.work.dataThread;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.slf4j.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private MyToolBar mToobar;
    private Logger logger;
    private List<Map<String,Object>> regionList = new ArrayList<>();
    @Inject
    RegionConfigViewModel regionConfigViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToobar = findViewById(R.id.toolbar_main);
        mToobar.setTimeText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
        mToobar.setLoginButtonText("登录");
        regionList.addAll(regionConfigViewModel.getRegionList().getValue().stream().map(Region->Region.toMap()).collect(Collectors.toList()));
        SimpleAdapter adapter = new SimpleAdapter(this,regionList,R.layout.region_item,new String[]{"name"},new int[]{R.id.name});
        mToobar.setRegionSpinnerAdapter(adapter);
        regionConfigViewModel.getRegionList().observe(this, new Observer<List<Region>>() {
            @Override
            public void onChanged(List<Region> regions) {
                regionList.clear();
                regionList.addAll(regions.stream().map(Region->Region.toMap()).collect(Collectors.toList()));
                adapter.notifyDataSetChanged();
            }
        });
        regionConfigViewModel.refresh();
        setSupportActionBar(mToobar);

        logger  = LogUtil.getLogger(this, MainActivity.class.getSimpleName());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_config)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);



    }
}