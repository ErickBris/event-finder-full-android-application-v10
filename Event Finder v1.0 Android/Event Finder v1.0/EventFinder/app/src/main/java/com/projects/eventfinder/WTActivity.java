package com.projects.eventfinder;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.config.UIConfig;
import com.libraries.slider.MGSlider;
import com.libraries.slider.MGSliderAdapter;
import com.libraries.usersession.UserAccessSession;

public class WTActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        setContentView(R.layout.activity_wt);

        final int maxSliderCount = UIConfig.ARRAY_WT_BG.length;

        MGSlider slider = (MGSlider) findViewById(R.id.slider);
        slider.setMaxSliderThumb(maxSliderCount);
        
        MGSliderAdapter adapter = new MGSliderAdapter(R.layout.wt_entry, maxSliderCount, maxSliderCount);
        adapter.setOnMGSliderAdapterListener(new MGSliderAdapter.OnMGSliderAdapterListener() {

            @Override
            public void onOnMGSliderAdapterCreated(MGSliderAdapter adapter, View v, final int position) {
                // TODO Auto-generated method stub
                ImageView imgViewThumb = (ImageView) v.findViewById(R.id.imgViewThumb);
                imgViewThumb.setImageResource(UIConfig.ARRAY_WT_THUMB[position]);

                ImageView imgViewBg = (ImageView) v.findViewById(R.id.imgViewBg);
                imgViewBg.setImageResource(UIConfig.ARRAY_WT_BG[position]);

                TextView tvTitle = (TextView) v.findViewById(R.id.tvTitle);
                tvTitle.setText(UIConfig.ARRAY_WT_TITLE[position]);

                TextView tvDesc = (TextView) v.findViewById(R.id.tvDesc);
                tvDesc.setText(UIConfig.ARRAY_WT_DESC[position]);
            }
        });

        slider.setOnMGSliderListener(new MGSlider.OnMGSliderListener() {
            @Override
            public void onItemMGSliderToView(MGSlider slider, int pos) { }

            @Override
            public void onItemMGSliderViewClick(AdapterView<?> adapterView, View v, int pos, long resid) { }

            @Override
            public void onItemThumbCreated(MGSlider slider, ImageView imgView, int pos) {
                imgView.setImageResource(R.mipmap.slider_thumb_unselected);
            }

            @Override
            public void onAllItemThumbCreated(MGSlider slider, LinearLayout linearLayout) {
                FrameLayout frameSlider = (FrameLayout) findViewById(R.id.frameSliderThumb);
                frameSlider.addView(linearLayout);
            }

            @Override
            public void onItemThumbSelected(MGSlider slider, ImageView[] buttonPoint, ImageView imgView, int pos) {
                if(buttonPoint == null)
                    return;

                for (int x = 0; x < buttonPoint.length; x++) {
                    buttonPoint[x].setImageResource(R.mipmap.slider_thumb_unselected);
                }
                imgView.setImageResource(R.mipmap.slider_thumb_selected);
                slider.setSlideAtIndex(pos);
            }

            @Override
            public void onItemPageScrolled(MGSlider slider, ImageView[] buttonPoint, int pos) {
                if(buttonPoint == null)
                    return;

                if (buttonPoint.length > 0) {
                    for (int x = 0; x < buttonPoint.length; x++) {
                        buttonPoint[x].setImageResource(R.mipmap.slider_thumb_unselected);
                    }
                    buttonPoint[pos].setImageResource(R.mipmap.slider_thumb_selected);
                }
            }
        });

        slider.setOffscreenPageLimit(maxSliderCount - 1);
        slider.setAdapter(adapter);
        slider.setActivity(this);
        slider.showThumb();

        final TextView tvSkipNow = (TextView) findViewById(R.id.tvSkipNow);
        tvSkipNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserAccessSession.getInstance(WTActivity.this).setWillNotShowWalkthrough(true);
                Intent intent = new Intent(WTActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
