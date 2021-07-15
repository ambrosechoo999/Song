package sg.edu.rp.c346.id20008189.song;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAdd, btnRetrieve;
    TextView tvContent, tvStars, tvSingers;
    EditText etContent, etYear, etSingers;
    ArrayList<Song> al;
    RadioButton rb1, rb2, rb3, rb4, rb5;
    RadioGroup rg;
    ListView lv;
    ArrayAdapter<Song> aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAdd = findViewById(R.id.btnAdd);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
        rb4 = findViewById(R.id.rb4);
        rb5 = findViewById(R.id.rb5);
        rg = findViewById(R.id.rgStars);
        btnRetrieve = findViewById(R.id.btnRetrieve);
        tvContent = findViewById(R.id.tvContent);
        etContent = findViewById(R.id.etContent);
        lv = findViewById(R.id.lv);
        etYear = findViewById(R.id.etYear);
        etSingers = findViewById(R.id.etSingers);
        tvStars = findViewById(R.id.tvStars);
        tvSingers = findViewById(R.id.tvSingers);

        //initialize the variables with UI here

        al = new ArrayList<Song>();
        aa = new ArrayAdapter<Song>(this,
                android.R.layout.simple_list_item_1, al);
        lv.setAdapter(aa);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String data = etContent.getText().toString();
                DBHelper dbh = new DBHelper(MainActivity.this);
                long result = dbh.insertSong(data);

                if (result != -1) {
                    al.clear();
                    al.addAll(dbh.getAllSongs());
                    aa.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Insert successful",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnRetrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(MainActivity.this);
                al.clear();
                String filterText = etContent.getText().toString().trim();
                if (filterText.length() == 0) {
                    al.addAll(dbh.getAllSongs());
                } else {
                    al.addAll(dbh.getAllSongs(filterText));
                }


                aa.notifyDataSetChanged();
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int
                    position, long identity) {
                Song data = al.get(position);
                Intent i = new Intent(MainActivity.this,
                        EditActivity.class);
                i.putExtra("data", data);
                startActivity(i);
            }
        });
        
    }




    @Override
    protected void onResume() {
        super.onResume();

        btnRetrieve.performClick();

    }

}
