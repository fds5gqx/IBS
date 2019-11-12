package com.example.gmp.exdbproject;

import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class DBManager {

    private FirebaseDatabase fdb = FirebaseDatabase.getInstance();
    private DatabaseReference rdb = fdb.getReference("user");

   String password;
   Boolean check = false;



    public void getPW(@NonNull final SimpleCallback finishedCallback, String id){
        rdb.child(id).child("password").addListenerForSingleValueEvent(new ValueEventListener() {
            //Firebase에 연결하여 id값으로 password를 불러옴

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                finishedCallback.callback(dataSnapshot.getValue().toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                finishedCallback.callback("This id is already exist");
                //DB에러시 메시지 출력
            }
        });
    }

    public void sign(@NonNull final SimpleCallback finishedCallback, final String id, final String pw) {
        rdb.child(id).child("password").addListenerForSingleValueEvent(new ValueEventListener() {
            //입력된 값을 Firebase와 연동해 DB에 저장
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    String str = dataSnapshot.getValue().toString();
                    finishedCallback.callback("This id is already exist");
                    //Toast.makeText(getApplicationContext(), "This id is already exist", Toast.LENGTH_SHORT).show();
                    //ID중복을 체크하고 알려줌
                } catch (Exception e) {
                    rdb.child(id).child("password").setValue(pw);
                    finishedCallback.callback("Sign Up!!");
                    //Toast.makeText(getApplicationContext(), "Sign Up!!", Toast.LENGTH_SHORT).show();
                    //값 입력 성공시 회원가입 완료 메시지 출력
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                finishedCallback.callback("Sign Up!!");
            }
        });
    }

    public void setPoints(@NonNull final SimpleCallback finishedCallback, final String id, final int getPos, final int red, final int green, final int blue){
        rdb.child(id).child("RGBP").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                rdb.child(id).child("RGBP").child("P"+getPos).setValue("R" + red + "G" + green + "B" + blue);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                finishedCallback.callback("DB Error!");
            }
        });
    }
    public void removePoints(@NonNull final SimpleCallback finishedCallback, final String id, final int getPos){
        rdb.child(id).child("RGBP").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                rdb.child(id).child("RGBP").child("P"+getPos).removeValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                finishedCallback.callback("DB Error!");
            }
        });
    }

    public void removeAll(@NonNull final SimpleCallback finishedCallback, final String id){
        rdb.child(id).child("RGBP").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                rdb.child(id).child("RGBP").removeValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                finishedCallback.callback("DB Error!");
            }
        });
    }

    public void setAll(@NonNull final SimpleCallbackArray finishedCallback, final String id){
        final ArrayList<String> points = new ArrayList();
        rdb.child(id).child("RGBP").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Toast.makeText(getApplicationContext(), dataSnapshot.getValue().toString().replaceAll("=","").replace("{","").replace("}",""), Toast.LENGTH_SHORT).show();
                String[] spt = dataSnapshot.getValue().toString().replaceAll("=","").replace("{","").replace("}","").split(",");
                for(int i = 0; i < spt.length; i++){
                    points.add(spt[i]);
                }
                finishedCallback.callbackArray(points);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                finishedCallback.callback("DB Error");
            }
        });
    }
}
