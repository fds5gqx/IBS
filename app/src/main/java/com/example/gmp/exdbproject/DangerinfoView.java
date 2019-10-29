package com.example.gmp.exdbproject;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.widget.ExpandableListAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DangerinfoView extends AppCompatActivity {
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    private int prePosition = -1;
    private RecyclerView recyclerview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.info_activity);

        recyclerview = findViewById(R.id.recycler);
        recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        List<ExpandableAdapter.Item> data = new ArrayList<>();

        data.add(new ExpandableAdapter.Item(ExpandableAdapter.HEADER, "음주운전 금지"));
        data.add(new ExpandableAdapter.Item(ExpandableAdapter.CHILD, "음주 자전거 이용 시(혈중 알코올 농도 0.05%이상)\n" +
                "범칙금 3만원이 부과됩니다.\n" +
                "\n" +
                "자전거 주행 중 경찰 공무원의 음주측정 요구에 응해야 하며\n" +
                "측정 거부시에는 범칙금 10만원이 부과됩니다."));

        data.add(new ExpandableAdapter.Item(ExpandableAdapter.HEADER, "안전장비 및 안전모 착용"));
        data.add(new ExpandableAdapter.Item(ExpandableAdapter.CHILD, "안전을 위해 법으로도 자전거도로 및 도로를 운행할 때\n" +
                "운전자 및 동승자의 안전모 착용은 의무화 되어있습니다."));

        data.add(new ExpandableAdapter.Item(ExpandableAdapter.HEADER, "전조등 및 후미등 장착"));
        data.add(new ExpandableAdapter.Item(ExpandableAdapter.CHILD, "가로등 불빛만으로는 밤에 주행이 어려울 수 있습니다.\n" +
                "\n" +
                "전조등이나 후미등이 없을 경우 다른 자전거나 차량에 의해\n" +
                "추돌사고가 발생할 수 있습니다."));

        data.add(new ExpandableAdapter.Item(ExpandableAdapter.HEADER, "안전속도 및 거리 확보"));
        data.add(new ExpandableAdapter.Item(ExpandableAdapter.CHILD, "자전거의 속도는 법적인 강제 규정은 아니지만\n" +
                "시속 25km이상 주행 시 사고확률이 높아집니다.\n" +
                "\n" +
                "평지에서 가장 편하고 멀리 갈 수 있는 속도인\n" +
                "시속 20km이하를 권장합니다."));

        data.add(new ExpandableAdapter.Item(ExpandableAdapter.HEADER, "휴대전화 및 이어폰 사용 금지"));
        data.add(new ExpandableAdapter.Item(ExpandableAdapter.CHILD, "이어폰 사용시 주위의 소리를 들을 수 없고\n" +
                "\n" +
                "휴대전화 사용시 갑작스런 장애물에 대처하지 못해\n" +
                "사고가 날 수 있습니다."));

        recyclerview.setAdapter(new ExpandableAdapter(data));
    }
}
