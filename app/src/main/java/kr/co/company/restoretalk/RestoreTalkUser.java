package kr.co.company.restoretalk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class RestoreTalkUser extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restoretalkuser);

        Intent intent = getIntent();
        String intentitem = intent.getStringExtra("filename");
        Toast.makeText(getApplicationContext(), intentitem, Toast.LENGTH_LONG).show();

        //read raw txt
        String rawFileData = readTxt( getRawResIdByName(intentitem) );
        System.out.println(rawFileData);

        String[] lines = rawFileData.split("\n");

        for(String line: lines){
            System.out.println(line);
            System.out.println("^^");
        }

//        try{
//            FileInputStream fis = openFileInput(intentitem+".txt");
//            byte[] data = new byte[fis.available()];
//            while (fis.read(data) != -1) {;} // fres 스트림을 읽어 data 버퍼 저장
//                fis.close();
//            System.out.println(new String(data));
//            Toast.makeText(getApplicationContext(), new String(data), Toast.LENGTH_LONG).show();
//        }
//        catch (FileNotFoundException e) {
//            System.out.println("\n\n\n\n\n\n");
//            System.out.println("\n\n\n\n\n\n File Not Found \n\n\n\n\n\n\n\n\n\n\n");
//            System.out.println("\n\n\n\n\n\n");
//        }
//        catch (Exception e) {System.out.println("\n\n\n\n\n\n");System.out.println("yes");System.out.println("\n\n\n\n\n\n");;}

        Button browseBtn = (Button)findViewById(R.id.room);
        browseBtn.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), RestoreTalkRoom.class);
                startActivity(intent);
            }
        });
    }

    private String readTxt(int id) {
        String data = null;
        InputStream inputStream = getResources().openRawResource(id);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int i;
        try {
            i = inputStream.read();
            while (i != -1) {
                //if(i == 64) {byteArrayOutputStream.write('\n');byteArrayOutputStream.write('\n');}
                //else
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }
            data = new String(byteArrayOutputStream.toByteArray(),"UTF8");

            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public int getRawResIdByName(String resName) {
        String pkgName = this.getPackageName();
        // Return 0 if not found.
        int resID = this.getResources().getIdentifier(resName, "raw", pkgName);
        //Log.i("AndroidVideoView", "Res Name: " + resName + "==> Res ID = " + resID);
        return resID;
    }
}