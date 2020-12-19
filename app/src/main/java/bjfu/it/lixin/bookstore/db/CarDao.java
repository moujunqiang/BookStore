package bjfu.it.lixin.bookstore.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;
import java.util.List;


public class CarDao {
    Context context;
    CarDBHelper dbHelper;

    public CarDao(Context context) {
        this.context = context;
        dbHelper = new CarDBHelper(context, "bookstore1.db", null, 3);
    }

    public void insertCar(CarBean bean) {

        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("book_id", bean.getBookImage());
        cv.put("book_image", bean.getBookImage());
        cv.put("book_name", bean.getBookName());
        cv.put("booK_number", bean.getBookNumber());
        cv.put("book_price", bean.getBookPrice());
        if (isExit(bean.getBookId())) {
            updateCarBookNumber(bean);
        } else {
            sqLiteDatabase.insert("car", null, cv);

        }
    }

    public int deleteCar(int id) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        int ret = 0;
        ret = sqLiteDatabase.delete("car", "_id=?", new String[]{id + ""});
        return ret;
    }

    /**
     * 查询图书是否存在
     *
     * @return
     */
    public boolean isExit(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String sql = "select * from car where _id=? ";
        Cursor cursor = db.rawQuery(sql, new String[]{id + ""});
        if (cursor.moveToNext()) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 更新购物车中 同一个图书的数量
     *
     * @param carBean
     */
    public void updateCarBookNumber(CarBean carBean) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("booK_number", carBean.getBookNumber() + 1);
        db.update("car", cv, "_id=?", new String[]{carBean.get_id() + ""});
        db.close();
    }


    public List<CarBean> queryAll() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        List<CarBean> carBeans = new ArrayList<>();
        CarBean carBean;
        String sql = "select * from car ";
        Cursor cursor = null;

        cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            carBean = new CarBean();

            carBean.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
            carBean.setBookImage(cursor.getInt(cursor.getColumnIndex("book_image")));
            carBean.setBookName(cursor.getString(cursor.getColumnIndex("book_name")));
            carBean.setBookId(cursor.getInt(cursor.getColumnIndex("book_id")));
            int booK_number = cursor.getInt(cursor.getColumnIndex("booK_number"));
            int book_price = cursor.getInt(cursor.getColumnIndex("book_price"));

            carBean.setBookNumber(booK_number);
            carBean.setBookPrice(book_price);
            carBean.setTotalPrice(booK_number * book_price);
            carBeans.add(carBean);
        }

        if (cursor != null) {
            cursor.close();
        }
        if (db != null) {
            db.close();
        }

        return carBeans;
    }




}
