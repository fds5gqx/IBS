package com.example.gmp.exdbproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOError;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    EditText user_id;
    EditText passwd; //EditText변수 선언
    Button btn;
    Button btn2; //Button변수 선언
    ImageView imageView;
    AnimationDrawable aniCatDrawable;

    FirebaseDatabase fdb = FirebaseDatabase.getInstance();
    DatabaseReference rdb = fdb.getReference("user");
    //Firebase연결

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
                            Toast.makeText(getApplicationContext(), data.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },user_id.getText().toString());
                /*rdb.child(user_id.getText().toString()).child("password").addListenerForSingleValueEvent(new ValueEventListener() {
                    //Firebase에 연결하여 id값으로 password를 불러옴
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        try {
                            if (passwd.getText().toString().equals(dataSnapshot.getValue().toString())) {
                                Toast.makeText(getApplicationContext(), "login sucess", Toast.LENGTH_SHORT).show();
                                //불러온 password값과 입력한 password값이 일치하면 로그인 성공 메시지 출력
                                Intent intent = new Intent(getApplicationContext(), SelectView.class); // 다음 넘어갈 클래스 지정
                                intent.putExtra("id", user_id.getText().toString());
                                startActivity(intent);
                                //다음 액티비티로 넘어감
                                //finish();
                            } else
                                Toast.makeText(getApplicationContext(), "check your id or password", Toast.LENGTH_SHORT).show();
                        }catch(Exception e){
                            Toast.makeText(getApplicationContext(), "check your id or password", Toast.LENGTH_SHORT).show();
                            //로그인 실패시 로그인 실패 메시지 출력
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "DB error", Toast.LENGTH_SHORT).show();
                        //DB에러시 메시지 출력
                    }
                });*/
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
                rdb.child(signID.getText().toString()).child("password").addListenerForSingleValueEvent(new ValueEventListener() {
                    //입력된 값을 Firebase와 연동해 DB에 저장
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        try{
                            String str = dataSnapshot.getValue().toString();
                            Toast.makeText(getApplicationContext(), "This id is already exist", Toast.LENGTH_SHORT).show();
                            //ID중복을 체크하고 알려줌
                        }catch(Exception e){
                            rdb.child(signID.getText().toString()).child("password").setValue(signPW.getText().toString());
                            rdb.child(signID.getText().toString()).child("cash").setValue(1000000);
                            Toast.makeText(getApplicationContext(), "Sign Up!!", Toast.LENGTH_SHORT).show();
                            //값 입력 성공시 회원가입 완료 메시지 출력
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "DB error", Toast.LENGTH_SHORT).show();
                    }
                });
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
