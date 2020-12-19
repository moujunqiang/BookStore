package bjfu.it.lixin.bookstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class MagazineCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magazine_category);
        //指定适配器，适配magazines数组
        ArrayAdapter<Magazine> listAdapter =new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,Magazine.magazines);
        ListView listMagazines=findViewById(R.id.list_magazines);
        listMagazines.setAdapter(listAdapter);

        //指定监听器，响应选项单击
        AdapterView.OnItemClickListener itemClickListener=new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listMagazines, View itemView, int position, long id) {
                Intent intent=new Intent(MagazineCategoryActivity.this,MagazineActivity.class);
                intent.putExtra(MagazineActivity.EXTRA_MAGAZINEID,(int)id);
                startActivity(intent);
            }
        };
        listMagazines.setOnItemClickListener(itemClickListener);

    }
}
