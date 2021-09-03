package techtown.org.navdrawerexperiment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        // DrawerLayout을 통해서 네비게이션 뷰를 그릴 수 있게 됨, 주로 그러한 역할을 하게 함
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        // ActionBar로 연결해서 쓸 Activity, toolbar, drawer를 활용하고 String으로 toggle을 만듬
        // 해당 toggle을 통해서 drawer 즉 네비게이션 뷰와 이를 toolbar에 얻어서 만듬, 그러면 햄버거 모양의 toggle이 생김
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        // 네비게이션 뷰로 그러진게 있다면 toggle을 연결시켜서 작동하게 함, 네비게이션 뷰가 담기게 됨
        if(drawer != null) {
            drawer.addDrawerListener(toggle);
        }
        // 그리고 이를 최종적으로 햄버거 모양의 toggle가 연결시킴
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        if(navigationView != null) {
            // 네비게이션 뷰에 있는 menu를 연결시킴
            navigationView.setNavigationItemSelectedListener(this);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawer != null) {
            // Drawer가 열려있다면 닫게끔 하고 닫혀 있으면 열 수 있게 설정함
            if(drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        switch(item.getItemId()) {
            // 각각 네비게이션 뷰에 있는 게 있다면 해당 메뉴 아이템 선택시 토스트 메시지 띄우고 꺼짐
            case R.id.nav_home:
                drawer.closeDrawer(GravityCompat.START);
                displayToast(getString(R.string.menu_home));
                return true;
            case R.id.nav_gallery:
                drawer.closeDrawer(GravityCompat.START);
                displayToast(getString(R.string.menu_gallery));
                return true;
            case R.id.nav_slideshow:
                drawer.closeDrawer(GravityCompat.START);
                displayToast(getString(R.string.menu_slideshow));
                return true;
            default:
                return false;
        }
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}