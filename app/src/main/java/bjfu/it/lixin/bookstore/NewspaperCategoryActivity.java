package bjfu.it.lixin.bookstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class NewspaperCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newspaper_category);
        //指定适配器，适配newspapers数组
        ArrayAdapter<Newspaper> listAdapter =new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,Newspaper.newspapers);
        ListView listNewspapers=findViewById(R.id.list_newspapers);
        listNewspapers.setAdapter(listAdapter);

        //指定监听器，响应选项单击
        AdapterView.OnItemClickListener itemClickListener=new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listNewspapers, View itemView, int position, long id) {
                Intent intent=new Intent(NewspaperCategoryActivity.this,NewspaperActivity.class);
                intent.putExtra(NewspaperActivity.EXTRA_NEWSPAPERID,(int)id);
                startActivity(intent);
            }
        };
        listNewspapers.setOnItemClickListener(itemClickListener);

    }
}
