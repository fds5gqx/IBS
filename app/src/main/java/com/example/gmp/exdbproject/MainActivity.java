package com.example.gmp.exdbproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText user_id;
    EditText passwd; //EditText변수 선언
    Button btn;
    Button btn2; //Button변수 선언
    ImageView imageView;
    AnimationDrawable aniCatDrawable;

    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    @Override
    public void onBackPressed() {
//        mp.stop();
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
            super.onBackPressed();
        } else {
            backPressedTime = tempTime;
            Toast.makeText(this, "한번 더 누르면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        user_id = (EditText)findViewById(R.id.id_input);
        passwd = (EditText)findViewById(R.id.pwd_input);
        btn = (Button)findViewById(R.id.login_button);
        btn2 = (Button)findViewById(R.id.login_button2);
        final LinearLayout l_layout = (LinearLayout) findViewById(R.id.layout);
        final LinearLayout l_layout2 = (LinearLayout) findViewById(R.id.layout_2);
        final LinearLayout l_layout3 = (LinearLayout) findViewById(R.id.layout3);
        final Animation animTrans = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate);
        final Animation animTrans2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate);
        final Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

        imageView = findViewById(R.id.back);
        aniCatDrawable = (AnimationDrawable) imageView.getDrawable();
        aniCatDrawable.start();

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                //여기에 딜레이 후 시작할 작업들을 입력
//                l_layout3.startAnimation(animTrans2);
//                l_layout.startAnimation(animTrans);
                l_layout2.setVisibility(View.VISIBLE);
                l_layout2.startAnimation(animFadeIn);
            }
        }, 2500);
//        animTrans.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                l_layout2.setVisibility(View.VISIBLE);
//                l_layout2.startAnimation(animFadeIn);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
        //각각의 변수들과 XML의 View와 연결

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) { //로그인 기능
                DBManager dbm = new DBManager();
                dbm.getPW(new SimpleCallback() {
                    @Override
                    public void callback(Object data) {
                        if(passwd.getText().toString().equals(data.toString())){
                            Toast.makeText(getApplicationContext(), "login sucess", Toast.LENGTH_SHORT).show();
                            //불러온 password값과 입력한 password값이 일치하면 로그인 성공 메시지 출력
                            Intent intent = new Intent(getApplicationContext(), SelectView.class); // 다음 넘어갈 클래스 지정
                            intent.putExtra("id", user_id.getText().toString());
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "check your ID or PW", Toast.LENGTH_SHORT).show();
                        }
                    }
                },user_id.getText().toString());
                //DB의 pw값을 받아오는 메소드 실행
            }
        });

        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        //회원가입 버튼 클릭시 다이얼로그 실행

    }
    public void showDialog() {

        AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);
        //다이얼로그 생성
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.signup_dialog, null);
        //다이얼로그 View설정
        ad.setView(view);
        final EditText signID = (EditText)view.findViewById(R.id.id_input_sign);
        final EditText signPW = (EditText)view.findViewById(R.id.pwd_input_sign);
        //View에서 입력된 ID와 PW를 가져옴

        ad.setPositiveButton("Sign Up", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DBManager dbm = new DBManager();
                dbm.sign(new SimpleCallback() {
                    @Override
                    public void callback(Object data) {
                        Toast.makeText(getApplicationContext(),data.toString(),Toast.LENGTH_SHORT).show();
                    }
                },signID.getText().toString(), signPW.getText().toString());
                //DB로 id 및 pw값을 저장하는 메소드 실행
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Cancel버튼 클릭시 다이얼로그 종료
            }
        });

        AlertDialog dialog = ad.create();
        dialog.show();
        //다이얼로그 실행
    }
}
