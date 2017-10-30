package com.sychan.shaka;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.sychan.shaka.app.ui.activity.LoginActivity;
import com.sychan.shaka.app.ui.activity.RegisterActivity;
import com.sychan.shaka.app.ui.activity.RetrieveActivity;
import com.sychan.shaka.project.entity.model.Bean;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.btn_insert)
    Button btnInsert;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    @BindView(R.id.btn_update)
    Button btnUpdate;
    @BindView(R.id.btn_select)
    Button btnSelect;
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawer;
    private Bean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

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
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @OnClick({R.id.btn_insert, R.id.btn_delete, R.id.btn_update, R.id.btn_select})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_insert:
                bean = new Bean();
                bean.setName("123");
                bean.setAge("20");
                bean.setScore("100");
                bean.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {
                            Toast.makeText(MainActivity.this, "保存成功", Toast.LENGTH_SHORT)
                                    .show();
                        } else {
                            // 失败
                            Toast.makeText(MainActivity.this, "保存失败", Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });
                break;
            case R.id.btn_delete:
                bean = new Bean();
                bean.setObjectId("965de258b8");
                bean.delete(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_SHORT)
                                    .show();
                        } else {
                            Toast.makeText(MainActivity.this, "删除失败", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
                break;
            case R.id.btn_update:
                bean = new Bean();
                bean.setObjectId("965de258b8");
                bean.setScore("99");
                bean.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(MainActivity.this, "更新成功", Toast.LENGTH_SHORT)
                                    .show();
                        } else {
                            Toast.makeText(MainActivity.this, "更新失败", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
                break;
            case R.id.btn_select:
                BmobQuery<Bean> query = new BmobQuery<Bean>();
                query.addWhereEqualTo("name", "123");
                //返回5条数据，如果不加上这条语句，默认返回10条数据
                query.setLimit(5);
                query.findObjects(new FindListener<Bean>() {
                    @Override
                    public void done(List<Bean> list, BmobException e) {
                        if (e == null) {
                            Toast.makeText(MainActivity.this, "查询成功" + "\t" + list.size(), Toast.LENGTH_SHORT)
                                    .show();
                            Logger.d(list.size());
                            for (Bean b : list) {
                                Logger.d(b.toString());
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "查询失败", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
                break;
            default:
                break;
        }
    }
}
