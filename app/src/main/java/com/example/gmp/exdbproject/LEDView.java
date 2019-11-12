package com.example.gmp.exdbproject;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import java.util.ArrayList;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;

public class LEDView extends AppCompatActivity {

    ImageButton colorSelect;
    ImageButton colorpic;

    BluetoothSPP bt;
    Button load;
    Button reset;

    int red = 255;
    int green = 255;
    int blue = 255;

    final DBManager dbm = new DBManager();

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
        colorSelect.setOnClickListener(new View.OnClickListener() { //색상 선택 버튼을 클릭했을 경우
            @Override
            public void onClick(View v) {
                showColorDialog(); //컬러피커 다이얼로그 실행
            }
        });

        load = (Button) findViewById(R.id.Fianece2);
        reset = (Button) findViewById(R.id.buttonR);

        final TableLayout tableLayout = (TableLayout) findViewById(R.id.table);
        TableRow.LayoutParams tableParams = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        //테이블 레이아웃과 파라미터 선언

        for (int i = 0; i < 8; i++) {
            // Creation row
            final TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(tableParams);
            int position = i;

            for(int j = 0 ; j < 32 ; j++){ //LED배열에 맞춰 ID 및 좌표값 설정
                final ToggleButton btn = new ToggleButton(this);
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
                btn.setBackgroundColor(Color.rgb(red,green,blue));

                btn.setOnClickListener(new Button.OnClickListener(){
                    public void onClick(View view){
                        if(btn.isChecked()) {
                            btn.setBackgroundColor(Color.rgb(red,green,blue));
                            //버튼이 클릭됬을 때 설정된 색으로 변경
                            dbm.setPoints(new SimpleCallback() {
                                @Override
                                public void callback(Object data) {
                                    if(!data.toString().isEmpty()){
                                        Toast.makeText(getApplicationContext(),data.toString(),Toast.LENGTH_SHORT).show();
                                        //에러가 났을 경우 에러메시지 표시
                                    }
                                }
                            },str, getPos, red, green, blue);
                            //DB로 값을 전송하는 메소드 실행
                            if(bt.getServiceState() == BluetoothState.STATE_CONNECTED){
                                String send = "P" + getPos + "R" + red + "G" + green + "B" + blue;
                                bt.send(send, true);
                                //블루투스로 좌표값 및 색상값 전송
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Bluetooth disconnected",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            btn.setBackgroundColor(Color.WHITE);
                            //버튼의 클릭이 false가 될 경우 색을 지움
                            dbm.removePoints(new SimpleCallback() {
                                @Override
                                public void callback(Object data) {
                                    if(!data.toString().isEmpty()){
                                        Toast.makeText(getApplicationContext(),data.toString(),Toast.LENGTH_SHORT).show();
                                        //에러가 났을 경우 에러메시지 표시
                                    }
                                }
                            },str, getPos);
                            if(bt.getServiceState() == BluetoothState.STATE_CONNECTED){
                                String del = "P" + Integer.toString(getPos) + "R0G0B0";
                                bt.send(del,true);
                                //해당 좌표의 LED를 끄도록 값을 전송
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

        load.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                dbm.setAll(new SimpleCallbackArray() {
                    @Override
                    public void callback(Object data) {
                        if(!data.toString().isEmpty()){
                            Toast.makeText(getApplicationContext(),data.toString(),Toast.LENGTH_SHORT).show();
                            //에러가 났을 경우 에러메시지 표시
                        }
                    }
                    //DB의 값을 불러오는 메소드 실행
                    @Override
                    public void callbackArray(ArrayList<String> data) {
                        if(bt.getServiceState() == BluetoothState.STATE_CONNECTED){
                            try{
                                bt.send("P500R0G0B0",true);
                                Thread.sleep(100);
                                for(int j = 0; j<data.size(); j++){
                                    bt.send(data.get(j),true);
                                    Thread.sleep(100);
                                    //배열을 받았을 경우(불러오기) 딜레이를 주어 여러값을 오차없이 전송
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
                }, str);
            }
        });

        reset.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                if(bt.getServiceState() == BluetoothState.STATE_CONNECTED){
                    bt.send("P500R0G0B0",true);
                    dbm.removeAll(new SimpleCallback() {
                        @Override
                        public void callback(Object data) {
                            if(!data.toString().isEmpty()){
                                Toast.makeText(getApplicationContext(),data.toString(),Toast.LENGTH_SHORT).show();
                                //에러가 났을 경우 에러메시지 표시
                            }
                        }
                    },str);
                    //DB의 값을 지우는 메소드 실행
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
                .setTitle("Choose color") //다이얼로그의 제목
                .initialColor(Color.WHITE) //다이얼로그의 배경색
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER) //컬러피커의 형태 설정
                .noSliders() //슬라이더 설정 x
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() { //컬러피커에서 색상을 선택했을 경우
                    @Override
                    public void onColorSelected(int selectedColor) {
                        Toast.makeText(LEDView.this,
                                "R: "+Color.red(selectedColor)+" G: "+Color.green(selectedColor)+" B: "+Color.blue(selectedColor),
                                Toast.LENGTH_SHORT).show(); //선택한 색상의 색상값을 r, g, b로 나누어 토스트 메세지로 출력

                    }
                })
                .setPositiveButton("ok", new ColorPickerClickListener() { //컬러피커에서 OK버튼을 누를 경우
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        red = Color.red(selectedColor);
                        green = Color.green(selectedColor);
                        blue = Color.blue(selectedColor); //선택한 색의 색상코드를 r, g, b로 나누어 각각의 색상값을 전역변수에 저장
                        colorpic.setBackgroundColor(selectedColor); //컬러피커 버튼의 배경색을 선택한 색으로 변경
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() { //컬러피커에서 cancel버튼을 누를 경우
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show(); //다이얼로그 실행
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
}

