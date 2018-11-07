package com.land.netcar.min;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.land.netcar.R;
import com.land.netcar.base.BaseActivity;
import com.land.netcar.login.loginsp.LoginSp;
import com.land.netcar.min.bean.PointOutBean;
import com.land.netcar.urls.UURL;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;

public class BluActivity extends BaseActivity implements View.OnClickListener {
    private Button openBluetooth_tb;
    private BluetoothAdapter bluetoothAdapter;
    private ListView unbondDevices;
    private ListView bondDevices;
    private ArrayList<BluetoothDevice> unbondDeviceslist = new ArrayList<BluetoothDevice>();
    private ArrayList<BluetoothDevice> bondDeviceslist = new ArrayList<BluetoothDevice>();
    private ObjectAnimator objectAnimator;
    private ImageView iv_shuxin, iv_npwd, back;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blu);
        initView();
        initData();
    }

    private void initView() {
        iv_shuxin = (ImageView) findViewById(R.id.iv_shuxin);
        iv_npwd = (ImageView) findViewById(R.id.iv_npwd);
        back = (ImageView) findViewById(R.id.back);
        openBluetooth_tb = (Button) findViewById(R.id.openBluetooth_tb);
        unbondDevices = (ListView) findViewById(R.id.unbondDevices);
        bondDevices = (ListView) findViewById(R.id.bondDevices);
        text = findViewById(R.id.textsdw);
        iv_shuxin.setOnClickListener(this);
        iv_npwd.setOnClickListener(this);
        openBluetooth_tb.setOnClickListener(this);
        back.setOnClickListener(this);
        text.setOnClickListener(this);
    }

    private void initData() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //注册广播
        initIntentFilter();
        if (BlueisOpen()) {
            System.out.println("蓝牙有开");
            openBluetooth_tb.setBackgroundResource(R.drawable.kai);


        }
        if (!BlueisOpen()) {
            System.out.println("蓝牙没开!");
            openBluetooth_tb.setBackgroundResource(R.drawable.guan);


        }
    }

    private boolean BlueisOpen() {
        return this.bluetoothAdapter.isEnabled();
    }

    /**
     * 打开蓝牙
     */
    public void openBluetooth() {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, 1);
    }

    /**
     * 关闭蓝牙
     */
    public void closeBluetooth() {
        bluetoothAdapter.disable();
    }

    public void initIntentFilter() {
        // 设置广播信息过滤
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        //注册广播接收器，接受并处理搜索结果
        registerReceiver(receiver, intentFilter);

    }

    /**
     * 蓝牙广播接收器
     */
    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
                    addBandDevices(device);

                    addBondDevicesToListView();
                } else {
                    addUnbondDevices(device);
                    addUnbondDevicesToListView();
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                startAnima();
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED
                    .equals(action)) {
                System.out.println("设备搜索完毕");
                endAnima();
            }
            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                if (bluetoothAdapter.getState() == BluetoothAdapter.STATE_ON) {
                    openBluetooth_tb.setBackgroundResource(R.drawable.kai);
                    bondDevices.setEnabled(true);
                    unbondDevices.setEnabled(true);
                } else if (bluetoothAdapter.getState() == BluetoothAdapter.STATE_OFF) {
                    Toast.makeText(BluActivity.this, "蓝牙断开了", Toast.LENGTH_SHORT).show();
                    ;
                    openBluetooth_tb.setBackgroundResource(R.drawable.guan);
                    bondDevices.setEnabled(false);
                    unbondDevices.setEnabled(false);
                }
            }

        }

    };

    /**
     * 添加未绑定蓝牙设备到list集合
     *
     * @param device
     */
    public void addUnbondDevices(BluetoothDevice device) {
        System.out.println("未绑定设备名称" + device.getName());
        if (!unbondDeviceslist.contains(device)) {
            unbondDeviceslist.add(device);
        }
    }

    /**
     * 添加已绑定蓝牙设备到list集合
     *
     * @param device
     */
    public void addBandDevices(BluetoothDevice device) {
        System.out.println("已绑定设备名称" + device.getName());
        if (!bondDeviceslist.contains(device)) {
            bondDeviceslist.add(device);
        }
    }

    /**
     * 添加已绑定蓝牙设备到ListView
     */
    private void addBondDevicesToListView() {
        ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        int count = bondDeviceslist.size();
        System.out.println("已绑定设备数量" + count);
        for (int i = 0; i < count; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("deviceName", bondDeviceslist.get(i).getName());
            data.add(map);// 把item项的数据加到data中
        }
        String[] from = {"deviceName"};
        int[] to = {R.id.device_name};
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, data,
                R.layout.bonddevice_item, from, to);
        // 把适配器装载到listView中
        bondDevices.setAdapter(simpleAdapter);

        bondDevices.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int arg2, long arg3) {
                BluetoothDevice device = bondDeviceslist.get(arg2);
                Intent intent = new Intent();
                intent.setClass(BluActivity.this, ChatActivity.class);
                intent.putExtra("deviceAddress", device.getAddress());
                startActivity(intent);
            }
        });

    }

    /**
     * 添加未绑定蓝牙设备到ListView
     */
    private void addUnbondDevicesToListView() {
        ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        int count = unbondDeviceslist.size();
        System.out.println("未绑定设备数量" + count);
        for (int i = 0; i < count; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("deviceName", unbondDeviceslist.get(i).getName());
            data.add(map);// 把item项的数据加到data中
        }
        String[] from = {"deviceName"};
        int[] to = {R.id.undevice_name};
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, data,
                R.layout.unbonddevice_item, from, to);

        //把适配器装载到listView中
        unbondDevices.setAdapter(simpleAdapter);

        // 为每个item绑定监听，用于设备间的配对
        unbondDevices.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int arg2, long arg3) {
                try {
                    Method createBondMethod = BluetoothDevice.class
                            .getMethod("createBond");
                    createBondMethod
                            .invoke(unbondDeviceslist.get(arg2));
                    // 将绑定好的设备添加的已绑定list集合
                    bondDeviceslist.add(unbondDeviceslist.get(arg2));
                    // 将绑定好的设备从未绑定list集合中移除
                    unbondDeviceslist.remove(arg2);
                    addBondDevicesToListView();
                    addUnbondDevicesToListView();

                } catch (Exception e) {
                    System.out.println("配对失败。。。。。。。");
                    Toast.makeText(BluActivity.this, "配对失败!", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.openBluetooth_tb:
                if (!BlueisOpen()) {
                    //蓝牙关闭的情况
//                    System.out.println("蓝牙关闭的情况");
                    openBluetooth();

//                    Intent intent = new Intent();
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    Intent intentd = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
//                    startActivityForResult(intentd, 1);
//
//                    Intent intent = new Intent();
//                    intent.setAction(Settings.ACTION_BLUETOOTH_SETTINGS);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    try{
//                        startActivity(intent);
//                    } catch(ActivityNotFoundException ex){
//                        ex.printStackTrace();
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }

                } else {
                    //蓝牙打开的情况
//                    System.out.println("蓝牙打开的情况");
                    closeBluetooth();
//
                }
                break;
            case R.id.iv_shuxin:
                if (BlueisOpen()) {
                    System.out.println("搜索蓝牙开始。。。。。。。。。。。。。。。。。");
                    initAnima();
                    bondDeviceslist.clear();
                    unbondDeviceslist.clear();
                    bluetoothAdapter.startDiscovery();
                } else {
                    Toast.makeText(BluActivity.this, "请打开蓝牙", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.textsdw:
//                final AlertDialog dialogd = new AlertDialog.Builder(this).create();
////                dialog.setView(inflater.inflate(R.layout.alert_dialog, null));
//                LayoutInflater flaters = LayoutInflater.from(this);
//                final View viewsda = flaters.inflate(R.layout.alert_dialog_main, null);
//                dialogd.setView(viewsda);
//
//                final TextView text = viewsda.findViewById(R.id.text);
//                text.setText(UURL.MYMAC);
//                TextView confirms = viewsda.findViewById(R.id.confirm);
//                TextView cancels = viewsda.findViewById(R.id.cancel);
//                cancels.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//
//                        dialogd.dismiss();
//                    }
//                });
//                confirms.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        getData(text.getText().toString());
//
//                        dialogd.dismiss();
//                    }
//                });
                final AlertDialog dialog = new AlertDialog.Builder(this).create();
//                dialog.setView(inflater.inflate(R.layout.alert_dialog, null));
                LayoutInflater flater = LayoutInflater.from(this);
                final View views = flater.inflate(R.layout.alert_dialog_blu_pwd, null);
                dialog.setView(views);
                final EditText number = views.findViewById(R.id.name_eit_bind);

                TextView confirm = views.findViewById(R.id.confirm);
                TextView cancel = views.findViewById(R.id.cancel);
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Loading();
                        getData(number.getText().toString().trim());
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                dialog.show();

                break;
            case R.id.iv_npwd:
//                final AlertDialog dialog = new AlertDialog.Builder(this).create();
////                dialog.setView(inflater.inflate(R.layout.alert_dialog, null));
//                LayoutInflater flater = LayoutInflater.from(this);
//                final View views = flater.inflate(R.layout.alert_dialog_blu_pwd, null);
//                dialog.setView(views);
//                final EditText number = views.findViewById(R.id.name_eit_bind);
//
//                TextView confirm = views.findViewById(R.id.confirm);
//                TextView cancel = views.findViewById(R.id.cancel);
//                confirm.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Loading();
//                        getData(number.getText().toString().trim());
//                    }
//                });
//                cancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });
//
//
//                dialog.show();
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    private void getData(String trim) {

        OkGo.post(UURL.TOOTH)
                .params("token", LoginSp.gettoken(this))
                .params("deviceid", "" + UURL.equipsearch)
                .params("num", trim)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        PointOutBean pointOutBean = JSON.parseObject(s, PointOutBean.class);
                        Toast.makeText(BluActivity.this, "" + pointOutBean.getInfo(), Toast.LENGTH_SHORT).show();
                        dismissDialog();
                    }
                });
    }

    private void initAnima() {
        Keyframe kf0 = Keyframe.ofFloat(0f, 0f);
        Keyframe kf1 = Keyframe.ofFloat(.5f, 180f);
        Keyframe kf2 = Keyframe.ofFloat(1f, 360f);
        PropertyValuesHolder pvhRotation = PropertyValuesHolder.ofKeyframe("rotation", kf0, kf1, kf2);
        objectAnimator = ObjectAnimator.ofPropertyValuesHolder(iv_shuxin, pvhRotation);
    }

    public void startAnima() {
        objectAnimator.setDuration(1000);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.setRepeatCount(-1);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();
    }

    public void endAnima() {
        if (objectAnimator != null) {
            objectAnimator.end();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        endAnima();
        bluetoothAdapter.cancelDiscovery();
    }
}

