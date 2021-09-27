package techtown.org.hellosharedprefs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener { // 스피너 선택을 위해 인터페이스 구현
    // Intent를 통해서 그리고 Preferences에 있는 Key를 통해서 그 값을 받을 수 있으므로 해당 키 값 선언
    private final String COUNT_SAVE_KEY = "count_save";
    private final String COLOR_KEY = "color";
    private final String SELECTED_SPINNER_ITEM_KEY = "selected_spinner_item";

    // Spinner 관련 값 불러오기 위해 변수 선언
    private String currentSpinnerSetting;
    private String spinner_item;
    private Spinner spinner;
    private int spinnerSelectedColor;

    // SharedPreferences 사용위해 변수 선언
    private SharedPreferences mPreferences;
    private String sharedPrefFile = "techtown.org.hellosharedprefs";

    // 스위치 값도 가져옴
    private Switch countSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        spinner = findViewById(R.id.backgroundSpinner);
        // spinner adapter 초기 설정
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.color_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if(spinner != null) {
            spinner.setOnItemSelectedListener(this);
            spinner.setAdapter(adapter);
        }
        countSwitch = findViewById(R.id.saveCountSwitch);
        // savekey의 boolean안에 따라 스위치 체크
        if(mPreferences.getBoolean(COUNT_SAVE_KEY, true)) {
            countSwitch.setChecked(true);
        } else {
            countSwitch.setChecked(false);
        }
        String saveSpinnerSetting = mPreferences.getString(SELECTED_SPINNER_ITEM_KEY, "Default");
        if(spinner != null) {
            switch(saveSpinnerSetting) {
                // SpinnerSetting String 값에 따라서 Spinner 선택함
                case "Default":
                    spinner.setSelection(0);
                    break;
                case "Black":
                    spinner.setSelection(1);
                    break;
                case "Red":
                    spinner.setSelection(2);
                    break;
                case "Blue":
                    spinner.setSelection(3);
                    break;
                case "Green":
                    spinner.setSelection(4);
                    break;
                default:
                    spinner.setSelection(0);
                    break;
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinner_item = parent.getItemAtPosition(position).toString();
        switch(spinner_item) {
            // 아이템 선택시 Color와 String Setting 설정
            case "Default":
                spinnerSelectedColor = ContextCompat.getColor(this, R.color.default_background);
                currentSpinnerSetting = "Default";
                break;
            case "Black":
                spinnerSelectedColor = ContextCompat.getColor(this, R.color.black);
                currentSpinnerSetting = "Black";
                break;
            case "Red":
                spinnerSelectedColor = ContextCompat.getColor(this, R.color.red_background);
                currentSpinnerSetting = "Red";
                break;
            case "Blue":
                spinnerSelectedColor = ContextCompat.getColor(this, R.color.blue_background);
                currentSpinnerSetting = "Blue";
                break;
            case "Green":
                spinnerSelectedColor = ContextCompat.getColor(this, R.color.green_background);
                currentSpinnerSetting = "Green";
                break;
            default:
                spinnerSelectedColor = ContextCompat.getColor(this, R.color.default_background);
                currentSpinnerSetting = "Default";
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // 아무것도 선택되지 않았을 경우 Default 설정
        spinner_item = getResources().getStringArray(R.array.color_array)[0];
        spinnerSelectedColor = ContextCompat.getColor(this, R.color.default_background);
        currentSpinnerSetting = "Default";
    }

    public void saveSettings(View view) {
        // setting을 sharedPreferences로 저장해서 둠
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(COLOR_KEY, spinnerSelectedColor);
        editor.putString(SELECTED_SPINNER_ITEM_KEY, currentSpinnerSetting);
        if (countSwitch.isChecked()) {
            editor.putBoolean(COUNT_SAVE_KEY, true);
        } else {
            editor.putBoolean(COUNT_SAVE_KEY, false);
        }
        editor.apply();
        Toast.makeText(this, "Settings have been saved!", Toast.LENGTH_LONG).show();
    }

    public void resetSettings(View view) {
        // sharedPreferences를 초기화 함
        spinner.setSelection(0);
        countSwitch.setChecked(true);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean(COUNT_SAVE_KEY, true);
        editor.putString(SELECTED_SPINNER_ITEM_KEY, "Default");
        editor.putInt(COLOR_KEY, ContextCompat.getColor(this, R.color.default_background));
        editor.apply();
        Toast.makeText(this, "Settings were reset!", Toast.LENGTH_LONG).show();
    }


}