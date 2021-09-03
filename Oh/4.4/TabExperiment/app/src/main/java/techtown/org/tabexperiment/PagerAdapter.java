package techtown.org.tabexperiment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

// ViewPager에 Fragment를 담아서 보여주기 위해서 선언한 PagerAdapter

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    // 생성자 생성해서 Tab의 개수를 받음
    public PagerAdapter(@NonNull FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @NonNull
    @Override
    // 특정 position과 연관된 Fragment를 반환함
    public Fragment getItem(int position) {
        switch(position) {
            case 0: return new TabFragment1();
            case 1: return new TabFragment2();
            case 2: return new TabFragment3();
            default: return null;
        }

    }

    @Override
    public int getCount() {
        // Tab의 개수를 반환함
        return mNumOfTabs;
    }
}
