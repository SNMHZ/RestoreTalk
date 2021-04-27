package kr.co.company.restoretalk;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class RestoreTalkBrowse extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restoretalkbrowse);

        String path = "/sdcard/Download";

        File directory = new File(path);
        File[] files = directory.listFiles();

        List<String> filesNameList = new ArrayList<>();

        try {
            filesNameList.add(files[0].getName());
        }
        catch(Exception e) {
            Toast.makeText(getApplicationContext(), "에러", Toast.LENGTH_LONG).show();
        }


        Button browseBtn = (Button)findViewById(R.id.confirm);
        browseBtn.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), RestoreTalkUser.class);
                startActivity(intent);
            }
        });
    }
}