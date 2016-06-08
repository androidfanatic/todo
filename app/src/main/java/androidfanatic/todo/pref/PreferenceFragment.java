package androidfanatic.todo.pref;

import android.os.Bundle;

import androidfanatic.todo.R;

public class PreferenceFragment extends android.preference.PreferenceFragment {
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
