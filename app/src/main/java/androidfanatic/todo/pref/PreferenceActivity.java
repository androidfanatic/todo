package androidfanatic.todo.pref;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import androidfanatic.todo.R;

public class PreferenceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        getFragmentManager().beginTransaction().replace(R.id.content, new PreferenceFragment()).commit();
    }

}

