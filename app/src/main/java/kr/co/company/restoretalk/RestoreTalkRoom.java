package kr.co.company.restoretalk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class RestoreTalkRoom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restoretalkroom);

        Button browseBtn = (Button)findViewById(R.id.back);
        browseBtn.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), RestoreTalkStart.class);
                startActivity(intent);
            }
        });
    }
}