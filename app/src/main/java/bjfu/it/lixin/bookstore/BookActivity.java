package bjfu.it.lixin.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BookActivity extends AppCompatActivity {

    public static final String EXTRA_BOOKID = "bookId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        //取出Intent的序号，从Book模型层获取具体信息
        int bookId = getIntent().getIntExtra(EXTRA_BOOKID, 0);

        //实例化helper对象
        SQLiteOpenHelper bookstoreDatabaseHelper = new BookStoreDatabaseHelper(this);

        try (SQLiteDatabase db = bookstoreDatabaseHelper.getReadableDatabase()) {
            //TODO获取数据库
            //query查询数据库，拿到游标
            Cursor cursor = db.query("BOOK",
                    new String[]{"NAME", "DESCRIPTION", "PRICE", "IMAGE_RESOURCE_ID", "FAVORITE"},
                    "_id=?",
                    new String[]{Integer.toString(bookId)},
                    null, null, null);
            //导航到第一条记录
            if (cursor.moveToFirst()) {
                //取出记录1-4列
                String nameText = cursor.getString(0);
                String descriptionText = cursor.getString(1);
                int price = cursor.getInt(2);
                int photoId = cursor.getInt(3);
                boolean isFavorite = (cursor.getInt(4) == 1);
                //显示name
                TextView name = findViewById(R.id.name);
                name.setText(nameText);
                //显示description
                TextView description = findViewById(R.id.description);
                description.setText(descriptionText);
                //显示价格
                TextView priceText = findViewById(R.id.price);
                priceText.setText("PRICE RMB:"+price);
                //显示图片
                ImageView photo = findViewById(R.id.photo);
                photo.setImageResource(photoId);
                photo.setContentDescription(nameText);
                //显示收藏
                CheckBox favorite = findViewById(R.id.favorite);
                favorite.setChecked(isFavorite);
            }
            cursor.close();
        } catch (SQLiteException e) {
            Log.e("sqlite", e.getMessage());
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void onFavoriteClicked(View view) {
        int bookId = (Integer) getIntent().getExtras().get(EXTRA_BOOKID);
        new UpdateBookTask().execute(bookId);
    }

    private class UpdateBookTask extends AsyncTask<Integer, Void, Boolean> {
        private ContentValues bookValues;

        //初始化任务
        protected void onPreExecute() {
            CheckBox favorite = findViewById(R.id.favorite);
            bookValues = new ContentValues();
            bookValues.put("FAVORITE", favorite.isChecked());
        }

        //执行费时的任务
        protected Boolean doInBackground(Integer... integers) {
            int bookId = getIntent().getExtras().getInt(EXTRA_BOOKID);
            //获得数据库引用
            SQLiteOpenHelper MyHelper = new BookStoreDatabaseHelper(BookActivity.this);
            try {
                SQLiteDatabase db = MyHelper.getWritableDatabase();
                db.update("BOOK", bookValues,
                        "_id=?", new String[]{Integer.toString(bookId)});
                return true;
            } catch (SQLiteException e) {
                Log.e("sqlite", e.getMessage());
                return false;
            }
        }

        //void表示不跟新进度，不覆盖onProgressUpdate()
        //任务结束后更新界面
        protected void onPostExecute(Boolean success) {
            if (!success) {
                Toast toast = Toast.makeText(BookActivity.this, "Database unavailable", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}