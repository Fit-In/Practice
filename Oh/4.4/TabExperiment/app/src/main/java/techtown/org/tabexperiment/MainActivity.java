package techtown.org.tabexperiment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar 만들어서 인스턴스를 생성하고 xml에서 불러와 연결함
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 인스턴스를 생성한 TabLayout 사용하기 위해서
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        // TabLayout에서 새로운 탭을 추가함
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_label1));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_label2));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_label3));
        // tab을 레이아웃에 다 적용함, Gravity 설정을 함
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // PagerAdapter를 통해서 fragments를 연결하고 각 page에 나타나게끔 함
        final ViewPager viewPager = findViewById(R.id.pager);
        // adapter에 FragmentManager와 현재 tabLayout을 넘김
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        // ViewPager가 변할때마다 Tab누를 때 변하도록 설정을 하고 현재 ViewPagerItem을 선택해서 화면이 바뀌게 함함
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}