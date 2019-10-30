package com.example.gmp.exdbproject;

import android.app.Activity;
import android.content.Intent;
import android.bluetooth.BluetoothAdapter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SelectView extends AppCompatActivity {

    TextView idText;
    BluetoothSPP bt;
    //텍스트 빛 블루투스SPP통신에 필요한 변수 선언

    public static String BaseUrl = "http://api.openweathermap.org/";
    public static String AppId = "bffd9eb9d548efe0fd97c4a12ec0cee6";

    private TextView weatherData;

    FirebaseDatabase fdb = FirebaseDatabase.getInstance();
    DatabaseReference rdb = fdb.getReference("user");
    //Firebase연동

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.select_main);


        bt = new BluetoothSPP(this);
        //BluetoothSPP라이브러리 사용을 위해 객체 선언

        weatherData = findViewById(R.id.testWeather);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION) && ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
                return;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
                return;
            }
        }
        LocationManager locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                String setLat = Double.toString(latitude);
                String setLong = Double.toString(longitude);

                getCurrentData(setLat, setLong);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 1, locationListener);

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            String setLat = Double.toString(latitude);
            String setLong = Double.toString(longitude);

            getCurrentData(setLat, setLong);
        } else {

        }

        /*ImageView rabbit = (ImageView) findViewById(R.id.gif_image);
        GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(rabbit);
        Glide.with(this).load(R.drawable.ganan).into(rabbit);*/
        //배경화면으로 gif이미지 출력

        Intent intent = getIntent();
        /*idText = (TextView)findViewById(R.id.textID);

        final String str = intent.getStringExtra("id");
        idText.setText("ID : " + str);*/

        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() { //데이터 수신
            public void onDataReceived(byte[] data, String message) {
                Toast.makeText(SelectView.this, message, Toast.LENGTH_SHORT).show();
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
        Button btnConnect = findViewById(R.id.Fianece2);
        //아두이노로 값을 보내는 버튼을 View와 연결
        btnConnect.setOnClickListener(new View.OnClickListener() {
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

        Button btnMap = findViewById(R.id.Work);
        btnMap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GoogleMapEx.class);
                startActivity(intent);
            }
        });

        Button btnLED = findViewById(R.id.Fianece);
        btnLED.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LEDView.class);
                startActivity(intent);
            }
        });

        Button btnsafety = findViewById(R.id.safety);
        btnsafety.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DangerinfoView.class);
                startActivity(intent);
            }
        });
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

    void getCurrentData(String lat, String lon) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherService service = retrofit.create(WeatherService.class);

        Call<WeatherResponse> call = service.getCurrentWeatherData(lat, lon, AppId);
                    call.enqueue(new Callback<WeatherResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
                            if (response.code() == 200) {
                                WeatherResponse weatherResponse = response.body();
                                assert weatherResponse != null;

                                String stringBuilder = "Country: " +
                                        weatherResponse.sys.country +
                                        "\n" +
                                        "Temperature: " +
                                        (weatherResponse.main.temp - 273) +
                                        "\n" +
                                        /*"Temperature(Min): " +
                                        weatherResponse.main.temp_min +
                                        "\n" +
                                        "Temperature(Max): " +
                                        weatherResponse.main.temp_max +
                                        "\n" +*/
                                        "Humidity: " +
                                        weatherResponse.main.humidity /*+
                                        "\n" +
                                        "Pressure: " +
                                        weatherResponse.main.pressure*/
                                        + "\n" +
                                        weatherResponse.weather.get(0).main;

                                String stringset = "현재 위치의 날씨는" + weatherResponse.weather.get(0).main +
                                        ",\n기온은 " + String.format("%.1f", weatherResponse.main.temp - 273) + "℃ 입니다.";

                                weatherData.setText(stringset);
                            }
                            else {
                                System.out.println("responsecode = " + response.code());
                                weatherData.setText("죄송합니다, 현재 날씨를 불러올 수 없었어요. :(");
                            }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherResponse> call, @NonNull Throwable t) {
                weatherData.setText(t.getMessage());
            }
        });
    }
}
