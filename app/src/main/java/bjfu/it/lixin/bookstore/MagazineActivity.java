package bjfu.it.lixin.bookstore;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MagazineActivity  extends AppCompatActivity {

    public static final String EXTRA_MAGAZINEID="magazineId";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magazine);

        int magazineId=getIntent().getExtras().getInt(EXTRA_MAGAZINEID);
        Magazine magazine=Magazine.magazines[magazineId];

        TextView name=findViewById(R.id.name);
        name.setText(magazine.getName());

        TextView description=findViewById(R.id.description);
        description.setText(magazine.getDescription());

        ImageView photo=findViewById(R.id.photo);
        photo.setImageResource(magazine.getImageResourceId());
        photo.setContentDescription(magazine.getName());
    }
}