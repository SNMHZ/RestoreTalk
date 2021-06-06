package kr.co.company.restoretalk;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class RestoreTalkBrowse extends AppCompatActivity {
    ArrayList<String> Items;
    ArrayAdapter<String> Adapter;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restoretalkbrowse);

        Field[] fields = R.raw.class.getFields();

        Items = new ArrayList<String>();
        System.out.println();
        for(int i = 0; i<fields.length; i++){
            Items.add(fields[i].getName());
        }

        Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, Items);
        list = (ListView)findViewById(R.id.list);
        list.setAdapter(Adapter);
        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        try {
            //filesNameList.add(files[0].getName());
            //InputStream fres = getResources().openRawResource(R.raw.kakaotalk_20210605_1252_24_495_group);
            //byte[] data = new byte[fres.available()];
            //while(fres.read(data) != -1){;}
            //fres.close();
            //mEdit.setText(new String(data));
        }
        catch(Exception e) {
            Toast.makeText(getApplicationContext(), "에러", Toast.LENGTH_LONG).show();
        }


        Button browseBtn = (Button)findViewById(R.id.confirm);
        browseBtn.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                int id = list.getCheckedItemPosition();
                if(id!=ListView.INVALID_POSITION) {
                    String intentitem = Items.get(id).toString();
                    //Toast.makeText(getApplicationContext(), intentitem, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), RestoreTalkUser.class);
                    intent.putExtra("filename", intentitem);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "다시 선택해주세요", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}