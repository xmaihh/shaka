package com.sychan.shaka;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.lzy.imagepicker.ui.ImageGridActivity;
import com.sychan.shaka.app.ui.activity.LauncherActivity;
import com.sychan.shaka.app.ui.activity.LoginActivity;
import com.sychan.shaka.app.ui.activity.NewTaskActivity;
import com.sychan.shaka.app.ui.activity.RegisterActivity;
import com.sychan.shaka.app.ui.activity.RetrieveActivity;
import com.sychan.shaka.app.ui.activity.testActivity;
import com.sychan.shaka.app.ui.fragment.NewTestFragment;
import com.sychan.shaka.app.ui.fragment.orderTakeFragment;
import com.sychan.shaka.project.config.SimpleBackHelper;
import com.sychan.shaka.project.config.SimpleBackPage;
import com.wx.base.app.ui.activity.BaseActivity;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ViewPager.OnPageChangeListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomnavigationView;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    private ActionBarDrawerToggle toggle;
    private int IMAGE_PICKER = 10086;

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
                return 5;
            }

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new orderTakeFragment();
                    case 1:
                        return new NewTestFragment();
                    case 2:
                        return new NewTestFragment();
                    case 3:
                        return new NewTestFragment();
                    case 4:
                        return new NewTestFragment();
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
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               SimpleBackHelper.showSimpleBack(mContext,SimpleBackPage.RELEASE_TASK);
            }
        });
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            BmobUser.logOut();
            BmobUser currentUser = BmobUser.getCurrentUser();
            if (currentUser == null) {
                Toast.makeText(MainActivity.this, "登出成功", Toast.LENGTH_SHORT)
                        .show();
                App.getLoginActivity();
                startActivity(new Intent(MainActivity.this, LauncherActivity.class));
            }
            return true;
        }
        if (id == R.id.action_download) {
            startActivity(new Intent(MainActivity.this, RetrieveActivity.class));
        }
        if (id == R.id.action_login) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        if (id == R.id.action_register) {
            startActivity(new Intent(MainActivity.this, RegisterActivity.class));
        }
        if (id == R.id.action_upload) {
            String picPath = "sdcard/image.jpg";
            final BmobFile file = new BmobFile(new File(picPath));
            file.upload(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        Toast.makeText(MainActivity.this, "上传成功", Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        Toast.makeText(MainActivity.this, "上传失败", Toast.LENGTH_SHORT)
                                .show();
                    }
                }
            });
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent = new Intent(this, ImageGridActivity.class);
            startActivityForResult(intent, IMAGE_PICKER);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            startActivity(new Intent(this, testActivity.class));

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            startActivity(new Intent(this, NewTaskActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
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
            viewPager.setCurrentItem(item.getOrder());
            return true;
        }
    };
}
