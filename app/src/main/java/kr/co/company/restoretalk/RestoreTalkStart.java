package kr.co.company.restoretalk;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.app.*;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class RestoreTalkStart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restoretalkstart);

        Button browseBtn = (Button)findViewById(R.id.browse);
        browseBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Toast.makeText(getApplicationContext(), "목록 가져오기 버튼", Toast.LENGTH_LONG).show();
            }
        });
    }
}