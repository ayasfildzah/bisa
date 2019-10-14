package com.mkp.bisa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.HashMap;

public class dashboard extends AppCompatActivity {
    private SliderLayout sliderLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        sliderLayout = (SliderLayout) findViewById(R.id.slider);
        // Load image dari URL
        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put("Massindo Group", "https://www.canva.com/design/DADmSHbNKbY/0w5pcPuVrX9xkqzNpM0wgA/view#1");
        url_maps.put("The Best Spring Air", "https://www.canva.com/design/DADmSHbNKbY/0w5pcPuVrX9xkqzNpM0wgA/view#2");
        url_maps.put("Spesial Award", "https://www.canva.com/design/DADmSHbNKbY/0w5pcPuVrX9xkqzNpM0wgA/view#3");
        // Load Image Dari res/drawable
        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Massindo Group",R.drawable.satu);
        file_maps.put("The Best Spring Air",R.drawable.dua);
        file_maps.put("Spesial Award",R.drawable.tiga);

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);
            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(4000);
    }


    public void exit(View view) {
        Intent intent = new Intent(dashboard.this, Login.class);
        startActivity(intent);


}

    public void about(View view) {
        Intent intent = new Intent(dashboard.this, about.class);
        startActivity(intent);

    }

    public void brands(View view) {
        Intent intent = new Intent(dashboard.this, tampil.class);
        startActivity(intent);
    }

    public void scan(View view) {
        Intent intent = new Intent(dashboard.this, MainActivity.class);
        startActivity(intent);


    }
}
