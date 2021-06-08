package kr.co.company.restoretalk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;


public class RestoreTalkUser extends AppCompatActivity {
    HashSet<String> idSet = new HashSet<>();
    ArrayList<String> Items = new ArrayList<>();
    ArrayAdapter<String> Adapter;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restoretalkuser);

        Intent intent = getIntent();
        String filename = intent.getStringExtra("filename");
        //Toast.makeText(getApplicationContext(), filename, Toast.LENGTH_LONG).show();

        //테스트용 raw 파일 열기
        String rawFileData = readTxt( getRawResIdByName(filename) );

        String[] lines = rawFileData.split("\n");

        //일반 파일 열기용
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
//        catch (Exception e) {;}

        //한줄씩 확인
        for(String line: lines){
            if (line.charAt(0)!='[')
                continue;

            for(int i=2; i<line.length()-4; i++){
                if( line.substring(i, i+4).equals("] [오") ) {
                    idSet.add(line.substring(1, i));
                    break;
                }
            }
        }

        //레이아웃
        Iterator<String> it = idSet.iterator();
        while (it.hasNext())
            Items.add(it.next());
        Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, Items);
        list = (ListView)findViewById(R.id.list);
        list.setAdapter(Adapter);
        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        Button browseBtn = (Button)findViewById(R.id.room);
        browseBtn.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                int id = list.getCheckedItemPosition();
                if(id!=ListView.INVALID_POSITION) {
                    String username = Items.get(id).toString();
                    Intent intent = new Intent(getApplicationContext(), RestoreTalkRoom.class);
                    intent.putExtra("filename", filename);
                    intent.putExtra("username", username);
                    startActivity(intent);
                }
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

    private int getRawResIdByName(String resName) {
        String pkgName = this.getPackageName();
        // Return 0 if not found.
        int resID = this.getResources().getIdentifier(resName, "raw", pkgName);
        //Log.i("AndroidVideoView", "Res Name: " + resName + "==> Res ID = " + resID);
        return resID;
    }
}