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

public class DBManager {

    private FirebaseDatabase fdb = FirebaseDatabase.getInstance();
    private DatabaseReference rdb = fdb.getReference("user");

    String password;

    public String getPW(String id){

        rdb.child(id).child("password").addListenerForSingleValueEvent(new ValueEventListener() {
            //Firebase에 연결하여 id값으로 password를 불러옴

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String pw = dataSnapshot.getValue().toString();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //DB에러시 메시지 출력
            }
        });
        return password;
    }

}
