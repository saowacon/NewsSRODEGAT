package egat.tick.newssrodegat;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.PriorityQueue;

public class MainActivity extends AppCompatActivity {
    // Explicit ประกาศตัวแปร
    private UserTABLE objUserTABLE;
    private NewsTABLE objNewsTABLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Create & Connected Database
        createConnected(); // เรียกใช้ Method ย่อย

        //Tester Add New Value
        //testerAddValue();

        //Delete All Data เขียนความว่างเปล่าไปในตาราง
        deleteAllData();

    } // onCreate

    private void deleteAllData() {
        SQLiteDatabase objSqLiteDatabase = openOrCreateDatabase("srod.db", MODE_PRIVATE, null);
        objSqLiteDatabase.delete("userTABLE", null, null);
        objSqLiteDatabase.delete("newsTABLE", null, null);
    } // Delete All Data MODE_PRIVATE คือ ห้าม Drop Table ทิ้ง

    private void testerAddValue() {
        objUserTABLE.addNewUser("testUser", "tesPassword", "ทดสอบชื่อ");
        objNewsTABLE.addNews("27Aug2015", "testHead", "testDetail", "http//image", "EGAT");
    }

    private void createConnected() {
        objUserTABLE = new UserTABLE(this);
        objNewsTABLE = new NewsTABLE(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
