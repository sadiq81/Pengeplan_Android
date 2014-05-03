package dk.eazyit.pengeplan.android.Pengeplan_Android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import dk.eazyit.pengeplan.android.Pengeplan_Android.R;

/**
 * @author
 */
public class Splash extends PengeplanActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.splash);

        ImageView splash = (ImageView) findViewById(R.id.splash_imageView);
        Animation fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        splash.startAnimation(fade_in);


        fade_in.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(Splash.this, Login.class));
                Splash.this.finish();
            }

            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        ImageView splash = (ImageView) findViewById(R.id.splash_imageView);
        splash.clearAnimation();

    }
}
