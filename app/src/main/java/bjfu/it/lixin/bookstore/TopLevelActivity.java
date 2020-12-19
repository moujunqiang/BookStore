package bjfu.it.lixin.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class TopLevelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);
        setupOptionsListView();
        setupFavoriteListView();
    }
    private Cursor favoritesCursor;
    private SQLiteDatabase db;

    private void setupFavoriteListView() {
        ListView listFavorites = findViewById(R.id.list_favorites);
        SQLiteOpenHelper bookstoreDatabaseHelper = new BookStoreDatabaseHelper(this);
        try {
            db = bookstoreDatabaseHelper.getReadableDatabase();
            //造出FAVORITE为1的记录入游标
            favoritesCursor = db.query("BOOK",
                    new String[]{"_id", "NAME"}, "FAVORITE=1",
                    null, null, null, null);
            //适配，在文本框中显示NAME
            CursorAdapter favoriteAdapter =
                    new SimpleCursorAdapter(TopLevelActivity.this,
                            android.R.layout.simple_list_item_1,
                            favoritesCursor,
                            new String[]{"NAME"},
                            new int[]{android.R.id.text1}, 0);
            listFavorites.setAdapter(favoriteAdapter);
        } catch (SQLiteException e) {
            Log.e("sqlite", e.getMessage());
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

        //点击跳转到第三页面
        listFavorites.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(TopLevelActivity.this,BookActivity.class);
                intent.putExtra(BookActivity.EXTRA_BOOKID,(int)id);
                startActivity(intent);
            }
        });

    }
    private void setupOptionsListView() {
        //实现OnItemClickListener,点击是打开二级页面
        AdapterView.OnItemClickListener itemClickListener=new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View itemView, int position, long id) {
                if(position==0){
                    Intent intent =new Intent(TopLevelActivity.this, BookCategoryActivity.class);
                    startActivity(intent);
                }
                if(position==1){
                    Intent intent =new Intent(TopLevelActivity.this, MagazineCategoryActivity.class);
                    startActivity(intent);
                }
                if(position==2){
                    Intent intent =new Intent(TopLevelActivity.this,NewspaperCategoryActivity.class);
                    startActivity(intent);
                }
            }
        };
        //为listView注册单机监听器
        ListView listView =findViewById(R.id.list_options);
        listView.setOnItemClickListener(itemClickListener);
    }

    //ListView随时可能向游标请求更多数据，Activity销毁时才关闭游标和数据库
    @Override
    public void onDestroy() {
        super.onDestroy();
        favoritesCursor.close();
        db.close();
    }
    //更新游标
    @Override
    protected void onRestart() {
        super.onRestart();
        Cursor newcursor = db.query("BOOK",
                new String[]{"_id", "NAME"},
                "FAVORITE=1",
                null, null, null, null);
        ListView listFavorites = findViewById(R.id.list_favorites);
        CursorAdapter adapter = (CursorAdapter) listFavorites.getAdapter();
        //关闭之前的适配器
        adapter.changeCursor(newcursor);
        favoritesCursor = newcursor;
    }
}