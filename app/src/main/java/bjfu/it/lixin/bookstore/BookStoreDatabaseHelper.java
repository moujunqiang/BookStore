package bjfu.it.lixin.bookstore;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BookStoreDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME="bookstore.db";
    private static final int DB_VERSION=3;

    public BookStoreDatabaseHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //建表
        db.execSQL("CREATE TABLE BOOK(_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"NAME TEXT,"
                +"DESCRIPTION TEXT,"
                +"PRICE INTEGER,"
                +"IMAGE_RESOURCE_ID INTEGER,"
                +"FAVORITE NUMERIC);");
        insertBook(db,"《钢铁是怎样练成的》","《钢铁是怎样炼成的》是前苏联作家尼古拉·奥斯特洛夫斯基所著的一部长篇小说，" +
                "故事主要讲述了保尔·柯察金从一个不懂事的少年到成为一个忠于革命的布尔什维克战士，" +
                "再到双目失明却坚强不屈创作小说，成为一名钢铁战士的故事。",23,R.drawable.gangtie);
        insertBook(db,"《启示录》","这是一部，由<独立1653日>的比尔·普尔曼所内扮演的哈佛大雪物理学家相容信世界上所有的事情都可以用科学来解释。" +
                " 但他的理论却被一个修女推翻，带领他走向信仰之道",24,R.drawable.qishi);
        insertBook(db,"《人间失格》","《人间失格》（又名《丧失为人的资格》）日本小说家太宰治创作的中篇小说，" +
                "发表于1948年，是一部半自传体的小说。作品中太宰治巧妙地将自己的人生与思想，" +
                "隐藏于主角叶藏的人生遭遇，藉由叶藏的独白，窥探太宰治的内心世界——“充满了可耻的一生”。",25,R.drawable.renjian);
    }

    //定义工具方法 插入数据 参数与数据表的列对应
    private static void insertBook(SQLiteDatabase db,String name,String description,int price,int resourceId){
        ContentValues bookValues=new ContentValues();
        bookValues.put("NAME",name);
        bookValues.put("DESCRIPTION",description);
        bookValues.put("PRICE",price);
        bookValues.put("IMAGE_RESOURCE_ID",resourceId);
        long result=db.insert("BOOK",null,bookValues);
        Log.d("sqlite","insert"+name+"_id"+result);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion<=1){
            db.execSQL("ALTER TABLE BOOK ADD COLUMN FAVORITE NUMERIC;");
        }
        if (oldVersion<=2){
            ContentValues gangtieDesc = new ContentValues();
            gangtieDesc.put("DESCRIPTION","Tasty");
            db.update("BOOK",gangtieDesc,
                    "NAME=?",
                    new String[]{"《钢铁是怎样练成的》"});
        }
    }
}
