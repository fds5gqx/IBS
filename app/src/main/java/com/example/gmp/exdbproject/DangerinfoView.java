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

        ExpandableAdapter.Item info1 = new ExpandableAdapter.Item(ExpandableAdapter.HEADER, "음주운전 금지");
        info1.invisibleChildren = new ArrayList<>();
        info1.invisibleChildren.add(new ExpandableAdapter.Item(ExpandableAdapter.CHILD, "음주 자전거 이용 시(혈중 알코올 농도 0.05%이상)" +
                " 범칙금 3만원이 부과됩니다." +
                "\n" +
                "자전거 주행 중 경찰 공무원의 음주측정 요구에 응해야 하며" +
                " 측정 거부시에는 범칙금 10만원이 부과됩니다.\n"));
        data.add(info1);

        ExpandableAdapter.Item info2 = new ExpandableAdapter.Item(ExpandableAdapter.HEADER, "안전장비 및 안전모 착용");
        info2.invisibleChildren = new ArrayList<>();
        info2.invisibleChildren.add(new ExpandableAdapter.Item(ExpandableAdapter.CHILD, "안전을 위해 법으로도 자전거도로 및 도로를 운행할 때" +
                " 운전자 및 동승자의 안전모 착용은 의무화 되어 있습니다.\n"));
        data.add(info2);

        ExpandableAdapter.Item info3 = new ExpandableAdapter.Item(ExpandableAdapter.HEADER, "전조등 및 후미등 장착");
        info3.invisibleChildren = new ArrayList<>();
        info3.invisibleChildren.add(new ExpandableAdapter.Item(ExpandableAdapter.CHILD, "가로등 불빛만으로는 밤에 주행이 어려울 수 있습니다." +
                "\n" +
                "전조등이나 후미등이 없을 경우 다른 자전거나 차량에 의해" +
                " 추돌사고가 발생할 수 있습니다.\n"));
        data.add(info3);

        ExpandableAdapter.Item info4 = new ExpandableAdapter.Item(ExpandableAdapter.HEADER, "안전속도 및 거리 확보");
        info4.invisibleChildren = new ArrayList<>();
        info4.invisibleChildren.add(new ExpandableAdapter.Item(ExpandableAdapter.CHILD, "자전거의 속도는 법적인 강제 규정은 아니지만," +
                " 시속 25km이상 주행 시 사고확률이 높아집니다." +
                "\n" +
                "평지에서 가장 편하고 멀리 갈 수 있는 속도인" +
                " 시속 20km이하를 권장합니다.\n"));
        data.add(info4);

        ExpandableAdapter.Item info5 = new ExpandableAdapter.Item(ExpandableAdapter.HEADER, "휴대전화 및 이어폰 사용 금지");
        info5.invisibleChildren = new ArrayList<>();
        info5.invisibleChildren.add(new ExpandableAdapter.Item(ExpandableAdapter.CHILD, "이어폰 사용시 주위의 소리를 들을 수 없고,"+
                " 휴대전화 사용시 갑작스런 장애물에 대처하지 못해" +
                " 사고가 날 수 있습니다.\n"));
        data.add(info5);

        recyclerview.setAdapter(new ExpandableAdapter(data));
    }
}
