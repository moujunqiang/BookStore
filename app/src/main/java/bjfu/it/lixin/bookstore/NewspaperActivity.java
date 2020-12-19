package bjfu.it.lixin.bookstore;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NewspaperActivity  extends AppCompatActivity {

    public static final String EXTRA_NEWSPAPERID="newspaperId";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newspaper);

        int newspaperId=getIntent().getExtras().getInt(EXTRA_NEWSPAPERID);
        Newspaper newspaper=Newspaper.newspapers[newspaperId];

        TextView name=findViewById(R.id.name);
        name.setText(newspaper.getName());

        TextView description=findViewById(R.id.description);
        description.setText(newspaper.getDescription());

        ImageView photo=findViewById(R.id.photo);
        photo.setImageResource(newspaper.getImageResourceId());
        photo.setContentDescription(newspaper.getName());
    }
}