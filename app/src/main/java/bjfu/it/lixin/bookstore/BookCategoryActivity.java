package bjfu.it.lixin.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import bjfu.it.lixin.bookstore.db.CarBean;
import bjfu.it.lixin.bookstore.db.CarDao;

public class BookCategoryActivity extends AppCompatActivity {

    private Cursor cursor;

    //ListView随时可能向游标请求更多数据，BookCategoryActivity销毁时才关闭游标和数据库
 /*   @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
    }
  */
    private TextView carNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_category);
        carNumber = findViewById(R.id.tv_car_number);
        //指定适配器，适配books数组
        ListView listBooks = findViewById(R.id.list_books);

        SQLiteOpenHelper bookstoreDatabaseHelper = new BookStoreDatabaseHelper(this);
        try {
            //获得数据库引用
            SQLiteDatabase db = bookstoreDatabaseHelper.getReadableDatabase();
            //新建游标，检索所有记录的_id和NAME
            cursor = db.query("BOOK",
                    new String[]{"_id", "PRICE", "IMAGE_RESOURCE_ID", "NAME"},
                    null, null, null, null, null);
            List<BookCateGoryBean> bookCateGoryBeans = new ArrayList<>();

            while (cursor.moveToNext()) {
                BookCateGoryBean bookCateGoryBean = new BookCateGoryBean();
                bookCateGoryBean.setBookName(cursor.getString(cursor.getColumnIndex("NAME")));
                bookCateGoryBean.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                bookCateGoryBean.setImage(cursor.getInt(cursor.getColumnIndex("IMAGE_RESOURCE_ID")));
                bookCateGoryBean.setPrice(cursor.getInt(cursor.getColumnIndex("PRICE")));
                bookCateGoryBeans.add(bookCateGoryBean);

            }
            listBooks.setAdapter(new MyListAdapter(bookCateGoryBeans, this));
        } catch (SQLException e) {
            Log.e("sqlite", e.getMessage());
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }


        //指定监听器，响应选项单击
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listBooks, View itemView, int position, long id) {
                Intent intent = new Intent(BookCategoryActivity.this, BookActivity.class);
                intent.putExtra(BookActivity.EXTRA_BOOKID, (int) id);
                startActivity(intent);
            }
        };
        listBooks.setOnItemClickListener(itemClickListener);

        queryCarNumber();
        findViewById(R.id.ll_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BookCategoryActivity.this, BookCarActivity.class));
            }
        });
    }

    public void queryCarNumber() {
        List<CarBean> carBeans = new CarDao(this).queryAll();
        carNumber.setText(carBeans.size() + "");

    }

    public class MyListAdapter extends BaseAdapter {

        private List<BookCateGoryBean> mStudentDataList;   //创建一个StudentData 类的对象 集合
        private LayoutInflater inflater;

        public MyListAdapter(List<BookCateGoryBean> mStudentDataList, Context context) {
            this.mStudentDataList = mStudentDataList;
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mStudentDataList == null ? 0 : mStudentDataList.size();  //判断有说个Item
        }

        @Override
        public Object getItem(int position) {
            return mStudentDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //加载布局为一个视图
            View view = inflater.inflate(R.layout.item_book_category, null);
            final BookCateGoryBean bookCateGoryBean = (BookCateGoryBean) getItem(position);

            //在view 视图中查找 组件
            TextView tv_name = (TextView) view.findViewById(R.id.tv_book_name);
            Button addCar = (Button) view.findViewById(R.id.btn_add_car);

            //为Item 里面的组件设置相应的数据
            tv_name.setText(bookCateGoryBean.getBookName());
            addCar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CarBean bean = new CarBean();
                    bean.setBookId(bookCateGoryBean.getId());
                    bean.setBookName(bookCateGoryBean.getBookName());
                    bean.setBookPrice(bookCateGoryBean.getPrice());
                    bean.setBookImage(bookCateGoryBean.getImage());
                    new CarDao(BookCategoryActivity.this).insertCar(bean);
                    queryCarNumber();
                }
            });
            //返回含有数据的view
            return view;
        }
    }

}