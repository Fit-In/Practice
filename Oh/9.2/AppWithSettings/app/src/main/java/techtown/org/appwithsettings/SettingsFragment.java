package techtown.org.appwithsettings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceFragmentCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class SettingsFragment extends PreferenceFragmentCompat {



    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // xml 폴더에 만든 preferences xml을 추가함 Fragment에 보이기 위해서
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }


}