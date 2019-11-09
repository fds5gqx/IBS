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
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;

public class LEDView extends AppCompatActivity {

    ImageButton colorSelect;
    ImageButton colorpic;

    Boolean resetCheck = true;

    BluetoothSPP bt;
    Button load;
    Button reset;

    ToggleButton btn;
//    Drawable roundDrawable = ContextCompat.getDrawable(this, R.drawable.cerclebutton);
    int red = 255;
    int green = 255;
    int blue = 255;

    FirebaseDatabase fdb = FirebaseDatabase.getInstance();
    DatabaseReference rdb = fdb.getReference("user");

    ArrayList<String> points = new ArrayList<>();

    TableRow tableRow;
    TableLayout tableLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.led_activity);

        bt = new BluetoothSPP(this);

        Intent intent = getIntent();
        final String str = intent.getStringExtra("id");

        colorSelect = (ImageButton) findViewById(R.id.colorpick2);
        colorpic = (ImageButton) findViewById(R.id.colorpick);
        colorSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showColorDialog();
            }
        });

        load = (Button) findViewById(R.id.Fianece2);
        reset = (Button) findViewById(R.id.buttonR);

        setPixels(str);

        load.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                rdb.child(str).child("RGBP").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //Toast.makeText(getApplicationContext(), dataSnapshot.getValue().toString().replaceAll("=","").replace("{","").replace("}",""), Toast.LENGTH_SHORT).show();
                        String[] spt = dataSnapshot.getValue().toString().replaceAll("=","").replace("{","").replace("}","").split(",");
                        for(int i = 0; i < spt.length; i++){
                            points.add(spt[i]);
                        }
                        if(bt.getServiceState() == BluetoothState.STATE_CONNECTED){
                            try{
                                bt.send("P500R0G0B0",true);
                                Thread.sleep(100);
                                for(int j = 0; j<points.size(); j++){
                                    bt.send(points.get(j),true);
                                    Thread.sleep(100);
                                }
                            }
                            catch (InterruptedException e){
                                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Bluetooth disconnected",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "DB error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        reset.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                if(bt.getServiceState() == BluetoothState.STATE_CONNECTED){
                    bt.send("P500R0G0B0",true);
                    tableLayout.removeAllViews();
                    setPixels(str);

                    rdb.child(str).child("RGBP").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            rdb.child(str).child("RGBP").removeValue();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getApplicationContext(), "DB error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    Toast.makeText(getApplicationContext(),"Bluetooth disconnected",Toast.LENGTH_SHORT).show();
                }
            }
        });

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
                .noSliders()
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
//                        toast("onColorSelected: 0x" + Integer.toHexString(selectedColor));
//                        roundDrawable.setColorFilter(selectedColor, PorterDuff.Mode.SRC_ATOP);
                        Toast.makeText(LEDView.this,
                                "R: "+Color.red(selectedColor)+" G: "+Color.green(selectedColor)+" B: "+Color.blue(selectedColor),
                                Toast.LENGTH_SHORT).show();

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
//                        int color = getResources().getColor(selectedColor);
                        red = Color.red(selectedColor);
                        green = Color.green(selectedColor);
                        blue = Color.blue(selectedColor);
                        colorpic.setBackgroundColor(selectedColor);
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
        if(bt.getBluetoothAdapter() == null) {
            Toast.makeText(LEDView.this, "블루투스를 사용할 수 없습니다.", Toast.LENGTH_SHORT).show();
        } else {
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
    }

    public void setup() {
        //setup
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

    public void setPixels(final String str){
        tableLayout = (TableLayout) findViewById(R.id.table);
        TableRow.LayoutParams tableParams = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);

        for (int i = 0; i < 8; i++) {
            // Creation row
            tableRow = new TableRow(this);
            tableRow.setLayoutParams(tableParams);
            int position = i;

            for(int j = 0 ; j < 32 ; j++){
                btn = new ToggleButton(this);
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
                btn.setTextOn(text);
                btn.setTextOff(text);
                btn.setLayoutParams(new TableRow.LayoutParams(150,150));
                btn.setPadding(0,0,0,0);

                btn.setOnClickListener(new Button.OnClickListener(){
                    public void onClick(View view){
                        if(btn.isChecked()) {
                            Toast.makeText(getApplicationContext(), "Clicked Position : " + getPos, Toast.LENGTH_SHORT).show();
                            //points.add("R" + red + "G" + green + "B" + blue + "P" + getPos);
                            rdb.child(str).child("RGBP").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    rdb.child(str).child("RGBP").child("P"+getPos).setValue("R" + red + "G" + green + "B" + blue);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(getApplicationContext(), "DB error", Toast.LENGTH_SHORT).show();
                                }
                            });
                            if(bt.getServiceState() == BluetoothState.STATE_CONNECTED){
                                String send = "P" + getPos + "R" + red + "G" + green + "B" + blue;
                                //Toast.makeText(getApplicationContext(),send,Toast.LENGTH_SHORT).show();
                                bt.send(send, true);
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Bluetooth disconnected",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            //Toast.makeText(getApplicationContext(), "LED off", Toast.LENGTH_SHORT).show();
                            rdb.child(str).child("RGBP").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    rdb.child(str).child("RGBP").child("P"+getPos).removeValue();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(getApplicationContext(), "DB error", Toast.LENGTH_SHORT).show();
                                }
                            });
                            if(bt.getServiceState() == BluetoothState.STATE_CONNECTED){
                                String del = "P" + Integer.toString(getPos) + "R0G0B0";
                                //Toast.makeText(getApplicationContext(), del, Toast.LENGTH_SHORT).show();
                                bt.send(del,true);
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Bluetooth disconnected",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

                tableRow.addView(btn);
            }
            tableLayout.addView(tableRow);
        }
    }



}

