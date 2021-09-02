package techtown.org.droidcafe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

// Spinner를 활용하기 위해서 AdapterView에 해당하는 인터페이스를 구현함
public class OrderActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        // 인텐트로 넘긴 값 받아서 TextView에 띄우는 작업
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = findViewById(R.id.order_textview);
        textView.setText(message);

        // 스피너 생성
        Spinner spinner = findViewById(R.id.label_spinner);
        if(spinner != null){
            // 위에서 불러와서 생성한 리스너를 바탕으로 사용하기 위해서 리스너를 연결함
            spinner.setOnItemSelectedListener(this);
        }
        // 스피너로 활용하기 위한 String-array활용과 보여줄때 안드로이드에서 제공하는 간단한 템플릿을 활용함
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.labels_array, android.R.layout.simple_spinner_item);
        // 스피너의 간단하게 구현을 위해 안드로이드에서 제공하는 드롭다운 템플릿을 활용함
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        if(spinner != null) {
            // 그 다음 위에서 구현한 스피너에 대한 기본세팅을 연결함
            spinner.setAdapter(adapter);
        }
    }

    public void onRadioButtonClicked(View view) {
        // RadioButton이 체크되어 있는지 확인함
        boolean checked = ((RadioButton) view).isChecked();
        // 체크한 상태에 따라서 이벤트 처리ㅡㄹㄹ 함
        switch(view.getId()) {
            case R.id.sameday:
                if (checked)
                    displayToast(getString(R.string.same_day_messenger_service));
                break;
            case R.id.nextday:
                if (checked)
                    displayToast(getString(R.string.next_day_ground_delivery));
                break;
            case R.id.pickup:
                if (checked)
                    displayToast(getString(R.string.pick_up));
                break;
            default:
                break;
        }
    }

    // 토스트 메시지를 띄우기 위한 메소드
    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }

    // 구현한 인터페이스에서 필요한 메소드 오버라이딩 해서 구현
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // 여기서 스피너에 있는 요소를 선택시 해당 요소를 토스트 메시지를 띄우기 위해서 String으로 받아옴
        String spinnerLabel = adapterView.getItemAtPosition(i).toString();
        displayToast(spinnerLabel);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void showDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(),getString(R.string.datepicker));
    }

    public void processDatePickerResult(int year, int month, int day) {
        // 날짜를 String으로 변환하기 위한 메서드
        String month_string = Integer.toString(month+1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        String dateMessage = (month_string + "/" + day_string + "/" + year_string);
        Toast.makeText(this, "Date: " + dateMessage, Toast.LENGTH_SHORT).show();
    }
}