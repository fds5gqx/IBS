package com.example.gmp.exdbproject;

import android.content.DialogInterface;
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

public class LEDView extends AppCompatActivity {

    ImageButton colorSelect;
//    Drawable roundDrawable = getResources().getDrawable(R.drawable.cerclebutton);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.led_activity);

        colorSelect = (ImageButton) findViewById(R.id.colorpick);
        colorSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showColorDialog();
            }
        });
        final TableLayout tableLayout = (TableLayout) findViewById(R.id.table);

        TableRow.LayoutParams tableParams = new TableRow.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);

        int cnt=0;
        for (int i = 0; i < 8; i++) {
            // Creation row
            final TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(tableParams);

            for(int j = 0 ; j < 32 ; j++){
                final Button btn = new Button(this);
                btn.setId(cnt);
                String text = Integer.toString(cnt);
                btn.setText(text);
                btn.setLayoutParams(tableParams);

                final int position = cnt;

                btn.setOnClickListener(new Button.OnClickListener(){
                    public void onClick(View view){
                        Toast.makeText(getApplicationContext(),"Clicked Position : " + position, Toast.LENGTH_SHORT).show();
                    }
                });
                tableRow.addView(btn);
                cnt++;
            }
            tableLayout.addView(tableRow);
        }
        cnt = 0;
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
}

