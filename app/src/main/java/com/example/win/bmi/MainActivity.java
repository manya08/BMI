package com.example.win.bmi;

import android.content.DialogInterface;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    private BmiFragment bmiFragment;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private MySimpleFragmentPagerAdapter mySFPA;
    private Fragment[] fragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bmiFragment = new BmiFragment();
        fragments = new Fragment[1];
        fragments[0]=bmiFragment;
        viewPager = findViewById(R.id.viewPager);
        mySFPA = new MySimpleFragmentPagerAdapter(getSupportFragmentManager());
        mySFPA.setFragments(fragments);
        viewPager.setAdapter(mySFPA);
        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.firstmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    private  boolean title=true;

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.contextMenu1).setTitle(R.string.select_unit);
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.contextMenu1){
            title=!title;
            Choice();
        }else {
            showAboutDialog();
        }
        return super.onOptionsItemSelected(item);
    }


    private  void Choice(){
        final CharSequence[] items={"公制单位","英制单位"};
        AlertDialog.Builder choice  = new AlertDialog.Builder(this);
        choice.setTitle(R.string.select_unit);
        choice.setSingleChoiceItems(items, bmiFragment.getBmi_system(),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        bmiFragment.setBmi_system(which);
                    }
                });
        choice.setNegativeButton("取消", null);
        choice.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        bmiFragment.setSystem();
                    }
                });
        choice.show();
    }
    private void showAboutDialog(){
        AlertDialog.Builder about = new AlertDialog.Builder(this);
        about.setTitle(R.string.title_about);
        about.setMessage(R.string.alert_dialog__msg);
        about.setPositiveButton("确定",null);
        AlertDialog alert = about.create();
        alert.show();
    }

    public void onBackPressed(){
        AlertDialog.Builder pressed = new AlertDialog.Builder(this);
        pressed.setTitle(R.string.confirm_exit);
        pressed.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        MainActivity.this.finish();
                    }
                });
        pressed.setNegativeButton("取消", null);
        AlertDialog alert = pressed.create();
        alert.show();
    }
}
