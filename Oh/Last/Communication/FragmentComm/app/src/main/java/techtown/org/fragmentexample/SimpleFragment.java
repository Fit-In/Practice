package techtown.org.fragmentexample;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;


public class SimpleFragment extends Fragment {

    // RadioButton 선택시 경우의 수
    private static final int YES = 0;
    private static final int NO = 1;
    private static final int NONE = 2;
    public int mRadioButtonChoice = NONE; // 라디오버튼 선택 체크
    OnFragmentInteractionListener mListener;
    // 인스턴스 생성시 선택값을 번들로 보내기 위한 키값
    private static final String CHOICE = "choice";

    public SimpleFragment() {
        // Required empty public constructor
    }

    interface OnFragmentInteractionListener { // 라디오 버튼을 선택한 데이터를 가져오기 위해 인터페이스 선언
        void onRadioButtonChoice(int choice);
    }

    @Override
    public void onAttach(@NonNull Context context) { // 해당 프래그먼트를 부른 host 액티비티를 잡아서 interface 사용
        super.onAttach(context);
        if(context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new ClassCastException(context.toString() + getResources().getString(R.string.exception_message));
        }
    }

    // xml 요소로 추가가 아닌 코드로 메인에서 불러와서 추가하기 위해서 인스턴스 리턴하는 함수 구현
    public static SimpleFragment newInstance(int choice) {
        SimpleFragment fragment = new SimpleFragment();
        Bundle arguments = new Bundle();
        // 선택한 것에 대해서 번들로 넘김
        arguments.putInt(CHOICE, choice);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        // fragment xml에서 View를 가져오기 위한 rootView 선언
        final View rootView = inflater.inflate(R.layout.fragment_simple, container, false);
        final RadioGroup radioGroup = rootView.findViewById(R.id.radio_group);

        // Bundle을 통해 Choice 상태를 알 수 있으므로 이 상태를 체크함
        if(getArguments().containsKey(CHOICE)) {
            mRadioButtonChoice = getArguments().getInt(CHOICE);
            if(mRadioButtonChoice != NONE) {
                radioGroup.check(radioGroup.getChildAt(mRadioButtonChoice).getId());
            }
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            // 라디오 버튼 눌러서 바뀔 때 텍스트 바뀌게끔 설정함
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = radioGroup.findViewById(checkedId);
                int index = radioGroup.indexOfChild(radioButton);
                TextView textView = rootView.findViewById(R.id.fragment_header);

                switch(index) {
                    // 라디오 버튼 선택시 리스너와 선택 케이스를 저장, 연결함
                    case YES:
                        textView.setText(R.string.yes_message);
                        mRadioButtonChoice = YES;
                        mListener.onRadioButtonChoice(YES);
                        break;
                    case NO:
                        textView.setText(R.string.no_message);
                        mRadioButtonChoice = NO;
                        mListener.onRadioButtonChoice(NO);
                        break;
                    default:
                        mRadioButtonChoice = NONE;
                        mListener.onRadioButtonChoice(NONE);
                        break;
                }
            }
        });


        return rootView;
    }
}