package ican.com.hotel3.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import ican.com.hotel3.R;

/**
 * Created by Administrator on 2017/4/14.
 */

public class CardActivity extends Activity {
    ImageButton back;
    TextView mianCard;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_card);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.information_title);

        mianCard = (TextView)findViewById(R.id.maintitle);
        mianCard.setText("绑定银行卡");

        back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(CardActivity.this,
                        PersonalActivity.class);
                CardActivity.this.startActivity(it);
            }
        });
    }
}
