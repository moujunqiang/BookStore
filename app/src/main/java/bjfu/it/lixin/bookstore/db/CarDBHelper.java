package bjfu.it.lixin.bookstore.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class CarDBHelper extends SQLiteOpenHelper {
    public CarDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table if not exists car(" +
                "_id integer primary key autoincrement," +
                "book_image integer," +
                "book_name varchar," +
                "book_id integer," +

                "booK_number integer," +
                "book_price integer)";
        sqLiteDatabase.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
