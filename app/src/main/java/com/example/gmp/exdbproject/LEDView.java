package com.example.gmp.exdbproject;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;

public class LEDView extends AppCompatActivity {

    ImageButton colorSelect;
    BluetoothSPP bt;
//    Drawable roundDrawable = getResources().getDrawable(R.drawable.cerclebutton);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.led_activity);

        bt = new BluetoothSPP(this);

        colorSelect = (ImageButton) findViewById(R.id.colorpick);
        colorSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showColorDialog();
            }
        });
        final TableLayout tableLayout = (TableLayout) findViewById(R.id.table);

        TableRow.LayoutParams tableParams = new TableRow.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);

        for (int i = 0; i < 8; i++) {
            // Creation row
            final TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(tableParams);
            int position = i;

            for(int j = 0 ; j < 32 ; j++){
                final Button btn = new Button(this);
                int setPos = i;
                if(i==0 && (j%2 == 0)){
                    if(j==0){
                        setPos = i;
                        position += 15;
                    }
                    else{
                        setPos = position;
                        position += 15;
                    }

                }
                else if(i==0 && (j%2 == 1)){
                    setPos = position;
                    position += 1;
                }

                if(i==1 && (j%2 == 0)){
                    if(j==0){
                        setPos = i;
                        position += 13;
                    }
                    else{
                        setPos = position;
                        position += 13;
                    }
                }
                else if(i==1 && (j%2 == 1)){
                    setPos = position;
                    position += 3;
                }

                if(i==2 && (j%2 == 0)){
                    if(j==0){
                        setPos = i;
                        position += 11;
                    }
                    else{
                        setPos = position;
                        position += 11;
                    }
                }
                else if(i==2 && (j%2 == 1)){
                    setPos = position;
                    position += 5;
                }

                if(i==3 && (j%2 == 0)){
                    if(j==0){
                        setPos = i;
                        position += 9;
                    }
                    else{
                        setPos = position;
                        position += 9;
                    }
                }
                else if(i==3 && (j%2 == 1)){
                    setPos = position;
                    position += 7;
                }

                if(i==4 && (j%2 == 0)){
                    if(j==0){
                        setPos = i;
                        position += 7;
                    }
                    else{
                        setPos = position;
                        position += 7;
                    }
                }
                else if(i==4 && (j%2 == 1)){
                    setPos = position;
                    position += 9;
                }

                if(i==5 && (j%2 == 0)){
                    if(j==0){
                        setPos = i;
                        position += 5;
                    }
                    else{
                        setPos = position;
                        position += 5;
                    }
                }
                else if(i==5 && (j%2 == 1)){
                    setPos = position;
                    position += 11;
                }

                if(i==6 && (j%2 == 0)){
                    if(j==0){
                        setPos = i;
                        position += 3;
                    }
                    else{
                        setPos = position;
                        position += 3;
                    }
                }
                else if(i==6 && (j%2 == 1)){
                    setPos = position;
                    position += 13;
                }

                if(i==7 && (j%2 == 0)){
                    if(j==0){
                        setPos = i;
                        position += 1;
                    }
                    else{
                        setPos = position;
                        position += 1;
                    }
                }
                else if(i==7 && (j%2 == 1)){
                    setPos = position;
                    position += 15;
                }

                final int getPos = setPos;
                btn.setId(setPos);
                String text = Integer.toString(setPos);
                btn.setText(text);
                btn.setLayoutParams(tableParams);

                btn.setOnClickListener(new Button.OnClickListener(){
                    public void onClick(View view){
                        Toast.makeText(getApplicationContext(),"Clicked Position : " + getPos, Toast.LENGTH_SHORT).show();
                        bt.send("R255G0B255P"+getPos,true);
                        bt.send("R255G0B0P255",true);
                    }
                });
                tableRow.addView(btn);
            }
            tableLayout.addView(tableRow);
        }

        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() { //데이터 수신
            public void onDataReceived(byte[] data, String message) {
                Toast.makeText(LEDView.this, message, Toast.LENGTH_SHORT).show();
                //Bluetooth로 값을 입력받으면 메시지로 보여줌
            }
        });

        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() { //연결됐을 때
            public void onDeviceConnected(String name, String address) {
                Toast.makeText(getApplicationContext()
                        , "Connected to " + name + "\n" + address
                        , Toast.LENGTH_SHORT).show();
            }

            public void onDeviceDisconnected() { //연결해제
                Toast.makeText(getApplicationContext()
                        , "Connection lost", Toast.LENGTH_SHORT).show();
            }

            public void onDeviceConnectionFailed() { //연결실패
                Toast.makeText(getApplicationContext()
                        , "Unable to connect", Toast.LENGTH_SHORT).show();
            }
        });

        Button bluetoothConnect = (Button) findViewById(R.id.buttonB);
        bluetoothConnect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (bt.getServiceState() == BluetoothState.STATE_CONNECTED) {
                    bt.disconnect();
                    //동기 상태면 연결 해제
                } else {
                    Intent intent = new Intent(getApplicationContext(), DeviceList.class);
                    startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
                }
            }
        });
    }

    public void showColorDialog() {
        ColorPickerDialogBuilder
                .with(LEDView.this)
                .setTitle("Choose color")
                .initialColor(Color.WHITE)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .lightnessSliderOnly()
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
//                        toast("onColorSelected: 0x" + Integer.toHexString(selectedColor));
//                        roundDrawable.setColorFilter(selectedColor, PorterDuff.Mode.SRC_ATOP);
                    }
                })
                .setPositiveButton("ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
//                        changeBackgroundColor(selectedColor);
//                        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//                            colorSelect.setBackgroundDrawable(roundDrawable);
//                        } else {
//                            colorSelect.setBackground(roundDrawable);
//                        }
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();
    }

    public void onDestroy() {
        super.onDestroy();
        bt.stopService(); //블루투스 중지
    }

    public void onStart() {
        super.onStart();
        if (!bt.isBluetoothEnabled()) { //
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
            //블루투스 연결
        } else {
            if (!bt.isServiceAvailable()) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER); //DEVICE_ANDROID는 안드로이드 기기 끼리
                setup();
            }
        }
    }

    public void setup() {
        /*Button btnSend = findViewById(R.id.Fianece); //데이터 전송
        btnSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int point = 0;
                int red = 255;
                int green = 0;
                int blue = 255;
                while (true) {
                    bt.send("R" + red + "G" + green + "B" + blue + "P" + point, true);
                    if(point == 255) break;
                    try {
                        Thread.sleep(100);
                        point++;
                        red--;
                        green++;
                        blue--;
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());    //sleep 메소드가 발생시키는 InterruptedException
                    }
                }
            }
        });*/
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) { // 위에 구현했던 함수를 이용해 Bluetooth기능 제어
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == Activity.RESULT_OK)
                bt.connect(data);
        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);
                setup();
            } else {
                Toast.makeText(getApplicationContext()
                        , "Bluetooth was not enabled."
                        , Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}

