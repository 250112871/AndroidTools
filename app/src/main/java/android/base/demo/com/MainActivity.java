package android.base.demo.com;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity {

    private ViewFlipper viewflipper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
   public void initView(){
//       viewflipper = findViewById(R.id.vf_flipper);
//       View item1 = View.inflate(this,R.layout.vod_page,null);
//       item1.setBackgroundColor(getResources().getColor(R.color.colorAccent));
//       View item2 = View.inflate(this,R.layout.vod_page,null);
//       item2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//       View item3 = View.inflate(this,R.layout.vod_page,null);
//
//       viewflipper.addView(item1);
//       viewflipper.addView(item2);
//       viewflipper.addView(item3);


   }
}
