package com.example.tarifaportiempo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// pantalla entera xd

        Window window = this.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);

        setContentView(R.layout.activity_main);


        //animatcion

        Animation animacion1 = AnimationUtils.loadAnimation(this,R.anim.arriba);
        Animation animacion2= AnimationUtils.loadAnimation(this,R.anim.abajo);

        TextView nametxt=findViewById(R.id.tv_marca);
        ImageView logoxd=findViewById(R.id.imv_logo);

        nametxt.setAnimation(animacion2);
        logoxd.setAnimation(animacion1);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(MainActivity.this, calculadora.class );
                startActivity(intent);
                finish();
            }
        },3000);








    }


}