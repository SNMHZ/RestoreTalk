package kr.co.company.restoretalk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RestoreTalkRoom extends AppCompatActivity {
    ArrayList<MyItem> arItem;
    ArrayAdapter<String> Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restoretalkroom);
        Intent intent = getIntent();
        String filename = intent.getStringExtra("filename");
        String username = intent.getStringExtra("username");

        TextView roomname = (TextView)findViewById(R.id.talkname);
        roomname.setText(filename);

        //Toast.makeText(getApplicationContext(), username, Toast.LENGTH_LONG).show();


        arItem = new ArrayList<MyItem>();
        MyItem mi;

        MyListAdapter MyAdapter = new MyListAdapter(this, R.layout.item, arItem);

        ListView list;
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(MyAdapter);


        //테스트용 raw 파일 열기
        String rawFileData = readTxt( getRawResIdByName(filename) );

        String[] lines = rawFileData.split(System.getProperty("line.separator"));

        String[] group1 = new String[2];

        Pattern pattern1 = Pattern.compile("[\\[](.*?)[\\]]");


        for(int j = 2; j<lines.length; j++) {
            Matcher matcher1 = pattern1.matcher(lines[j]);
            int idx = lines[j].indexOf("] ");
            String talktext1 = lines[j].substring(idx + 1);
            String talktext2 = talktext1.substring(idx + 4);

            int i = 0;
            while (matcher1.find()) {  // 일치하는 게 있다면
                group1[i] = matcher1.group();
                i++;
                if (matcher1.group(1) == null)
                    break;
            }
            for (int count = 0; count < group1.length; count++) {
                group1[count] = group1[count].replace("[", "");
                group1[count] = group1[count].replace("]", "");
            }

            if (group1[0].equals(username)) {
                mi = new MyItem(R.drawable.profileme, group1[0], talktext2, group1[1]);
                arItem.add(mi);
            } else {
                mi = new MyItem(R.drawable.profile, group1[0], talktext2, group1[1]);
                arItem.add(mi);
            }
        }
        //System.out.println(rawFileData);

        Button browseBtn = (Button)findViewById(R.id.back);
        browseBtn.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), RestoreTalkStart.class);
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

class MyItem {
    MyItem(int aIcon, String ausername, String atalktext, String atime) {
        icon = aIcon;
        username = ausername;
        talktext = atalktext;
        time = atime;
    }
    int icon;
    String username;
    String talktext;
    String time;
}

//어댑터 클래스
class MyListAdapter extends BaseAdapter {
    LayoutInflater Inflater;
    ArrayList<MyItem> arSrc;
    int layout;

    public MyListAdapter(Context context, int alayout, ArrayList<MyItem> aarSrc) {
        Inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        arSrc = aarSrc;
        layout = alayout;
    }

    public int getCount() {
        return arSrc.size();
    }

    public String getItem(int position) {
        return arSrc.get(position).talktext;
    }

    public long getItemId(int position) {
        return position;
    }

    // 각 항목의 뷰 생성
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        if (convertView == null) {
            convertView = Inflater.inflate(layout, parent, false);
        }

        ImageView img = (ImageView)convertView.findViewById(R.id.profile);
        img.setImageResource(arSrc.get(position).icon);

        TextView username = (TextView) convertView.findViewById(R.id.name);
        username.setText(arSrc.get(position).username);

        TextView talktext = (TextView) convertView.findViewById(R.id.talktext);
        talktext.setText(arSrc.get(position).talktext);

        TextView time = (TextView) convertView.findViewById(R.id.time);
        time.setText(arSrc.get(position).time);


        return convertView;
    }
}