package com.example.codyclawson.runescapecompanion;

import android.content.Intent;
import com.daimajia.androidanimations.library.Techniques;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.model.ConfigSplash;

/**
 * Created by CodyClawson on 3/7/2017.
 */

public class SplashScreenActivity extends AwesomeSplash {

    @Override
    public void initSplash(ConfigSplash configSplash) {

        //Customize Circular Reveal
        configSplash.setBackgroundColor(R.color.splashblue); //any color you want form colors.xml
        configSplash.setAnimCircularRevealDuration(2000); //int ms

        //Customize Logo
        configSplash.setLogoSplash(R.mipmap.oldschoollogo); //or any other drawable
        configSplash.setAnimLogoSplashDuration(2000); //int ms
        configSplash.setAnimLogoSplashTechnique(Techniques.SlideInDown);

        //Customize Title *Doesnt have text but it needs to be here to override default*
        configSplash.setTitleSplash("");
        configSplash.setTitleTextColor(R.color.white);
        configSplash.setTitleTextSize(60f); //float value
        configSplash.setAnimTitleDuration(2500);
    }

    @Override
    public void animationsFinished() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}