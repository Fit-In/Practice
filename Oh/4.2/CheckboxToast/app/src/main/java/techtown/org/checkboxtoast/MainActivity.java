package techtown.org.checkboxtoast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private String message ="Toppings: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // 토스트 메시지 출력함
    public void toastMessage(View view) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    public void onSubmit(View view) {
        // 체크박스의 체크가 된 상태 확인 + String으로 추가하기 위해서 변수 선언
        boolean checked = ((CheckBox) view).isChecked();
        String topping;
        switch(view.getId()) {
            // 각각 케이스에 해당하는 경우 즉 체크된 경우
            case R.id.check_syrup:
                topping = getString(R.string.chocolate_syrup);
                if(checked){
                    // 만일 체크가 되었는데 message String에 존재하지 않는다면 추가함
                    if(!message.contains(topping)){
                        message = message + " " + topping;
                    }
                } else {
                    // 만일 체크가 풀렸는데 message String에 존재한다면 해당 부분을 교체함
                    if(message.contains(topping)){
                        message = message.replace(" " + topping, "");
                    }
                }
                break;
            case R.id.check_sprinkles:
                topping = getString(R.string.sprinkles);
                if(checked){
                    if(!message.contains(topping)){
                        message = message + " " + topping;
                    }
                } else {
                    if(message.contains(topping)){
                        message = message.replace(" " + topping, "");
                    }
                }
                break;
            case R.id.check_crushed_nuts:
                topping = getString(R.string.crushed_nuts);
                if(checked){
                    if(!message.contains(topping)){
                        message = message + " " + topping;
                    }
                } else {
                    if(message.contains(topping)){
                        message = message.replace(" " + topping, "");
                    }
                }
                break;
            case R.id.check_cherries:
                topping = getString(R.string.cherries);
                if(checked){
                    if(!message.contains(topping)){
                        message = message + " " + topping;
                    }
                } else {
                    if(message.contains(topping)){
                        message = message.replace(" " + topping, "");
                    }
                }
                break;
            case R.id.check_oreo:
                topping = getString(R.string.orio_cookie_crumbles);
                if(checked){
                    if(!message.contains(topping)){
                        message = message + " " + topping;
                    }
                } else {
                    if(message.contains(topping)){
                        message = message.replace(" " + topping, "");
                    }
                }
                break;

        }
    }
}