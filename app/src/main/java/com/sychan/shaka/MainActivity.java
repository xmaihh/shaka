package com.sychan.shaka;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import com.getbase.floatingactionbutton.FloatingActionButton;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.sychan.shaka.app.ui.activity.LauncherActivity;
import com.sychan.shaka.app.ui.activity.testActivity;
import com.sychan.shaka.app.ui.fragment.InvitationcodeFragment;
import com.sychan.shaka.app.ui.fragment.NewTestFragment;
import com.sychan.shaka.app.ui.fragment.WaterMarkFragment;
import com.sychan.shaka.app.ui.fragment.orderTakeFragment;
import com.sychan.shaka.project.config.SimpleBackHelper;
import com.sychan.shaka.project.config.SimpleBackPage;
import com.sychan.shaka.support.utils.ToastUtil;
import com.tencent.bugly.beta.Beta;
import com.wx.base.app.ui.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ViewPager.OnPageChangeListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    //    @BindView(R.id.fab)
//    FloatingActionButton fab;
    @BindView(R.id.fab_1)
    FloatingActionButton fab1;
    @BindView(R.id.fab_2)
    FloatingActionButton fab2;
    @BindView(R.id.fab_3)
    FloatingActionButton fab3;
    @BindView(R.id.fab_menu)
    FloatingActionsMenu fabMenu;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomnavigationView;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    private ActionBarDrawerToggle toggle;
    private int IMAGE_PICKER = 10086;
    private TextView currentuser;
    private TextView version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                if (item != null && item.getItemId() == android.R.id.home) {
                    if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                        drawer.closeDrawer(Gravity.RIGHT);
                    } else {
                        drawer.openDrawer(Gravity.RIGHT);
                    }
                }
                return false;
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new orderTakeFragment();
                    case 1:
                        return new InvitationcodeFragment();
                    case 2:
                        return new NewTestFragment();
//                    case 3:
//                        return new WaterMarkFragment();
                    default:
                        return new orderTakeFragment();
                }
            }
        });
        viewPager.addOnPageChangeListener(this);
        bottomnavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
            }
        });
        // TODO: 2017-11-30 20:30 
//        fab.setOnClickListener(new View.OnClickListener() {  
//            @Override
//            public void onClick(View view) {
//                SimpleBackHelper.showSimpleBack(mContext, SimpleBackPage.RELEASE_TASK);
//            }
//        });

        //headerLayout
//        View header = navigationView.inflateHeaderView(R.activity_layout_test.nav_header_main);
        View header = navigationView.getHeaderView(0);
        currentuser = (TextView) header.findViewById(R.id.tv_current_user);
        version = (TextView) header.findViewById(R.id.tv_version);
        version.setText(getVersion());
        BmobUser currentUser = BmobUser.getCurrentUser();
        if (currentUser != null) {
            currentuser.setText(currentUser.getUsername());
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is Contract.
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            fabMenu.setVisibility(View.GONE);
            ToastUtil.show("11");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_manage:
                startActivity(new Intent(this, testActivity.class));
                ToastUtil.show("开发中...");
                break;
            case R.id.nav_update:
                Beta.checkUpgrade(true, true);
                break;
            case R.id.nav_share:
                ToastUtil.show("开发中...");
                break;
            case R.id.nav_send:
                SimpleBackHelper.showSimpleBack(mContext, SimpleBackPage.FEED_BACK);
                break;
            case R.id.nav_logout:
                BmobUser.logOut();
                BmobUser currentUser = BmobUser.getCurrentUser();
                if (currentUser == null) {
                    ToastUtil.show(getString(R.string.toast_success));
                    App.getLoginActivity();
                    startActivity(new Intent(MainActivity.this, LauncherActivity.class));
                }
                break;
            case R.id.nav_power_off:
                App.exit();
                break;
            default:
                break;
        }

//        if (id == R.id.nav_camera) {
//            Intent intent = new Intent(this, ImageGridActivity.class);
//            startActivityForResult(intent, IMAGE_PICKER);
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//            startActivity(new Intent(this, testActivity.class));
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//            startActivity(new Intent(this, NewTaskActivity.class));
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        fabMenu.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
        if (position != 0 && fabMenu.isExpanded()) {
            fabMenu.collapse();
        }
        //页面滑动的时候，设置BottomNavigationView的Item选中高亮
        bottomnavigationView.getMenu().getItem(position).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            fabMenu.setVisibility(item.getOrder() == 0 ? View.VISIBLE : View.GONE);
            if (item.getOrder() != 0 && fabMenu.isExpanded()) {
                fabMenu.collapse();
            }
            viewPager.setCurrentItem(item.getOrder());
            return true;
        }
    };

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @OnClick({R.id.fab_1, R.id.fab_2, R.id.fab_3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fab_1:
                SimpleBackHelper.showSimpleBack(mContext, SimpleBackPage.ORDER_COMPLETE);
                break;
            case R.id.fab_2:
                SimpleBackHelper.showSimpleBack(mContext, SimpleBackPage.ORDER_REVIEW);
                break;
            case R.id.fab_3:
                SimpleBackHelper.showSimpleBack(mContext, SimpleBackPage.RELEASE_TASK);
                break;
        }
    }
}
