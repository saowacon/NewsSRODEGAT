package egat.tick.newssrodegat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class MainActivity extends AppCompatActivity {
    // Explicit ประกาศตัวแปร
    private UserTABLE objUserTABLE;
    private NewsTABLE objNewsTABLE;
    private EditText userEditText, passwordEditText;
    private Button loginButton;
    private String userString, passwordString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind Widget
        bindWidget();

        //Create & Connected Database
        createConnected(); // เรียกใช้ Method ย่อย

        //Tester Add New Value
        //testerAddValue();

        //Delete All Data เขียนความว่างเปล่าไปในตาราง
        deleteAllData();

        //Synchronize JSON to SQLite
        synJSONtoSQLite(); // ปรับ Protocol ให้เข้าถึง http https ftp ได้ โดยไม่ต้องกลัวไวรัส

        //Button Controller
        buttonController();

    } // onCreate

    private void buttonController() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userString = userEditText.getText().toString().trim();
                passwordString = passwordEditText.getText().toString().trim();

                //Check Zero
                if (userString.equals("")  || passwordString.equals("")) {  // || = or
                    //Have Space
                    errorDialog("Have Space", "Please Fill All Blank");
                } else {
                    //No Space
                    // Check กรณีกรอกครบทั้ง User และ Password
                    checkUser();
                } // if
            } //event
        });

    } // buttonController จัดการปุ่ม Login

    private void checkUser() {
        //ใช้เครื่องมือในการทำ Search Engine
        try { // หา User เจอใน Table
            String strMyResult[] = objUserTABLE.searchUser(userString);

            //Check Password
            if (passwordString.equals(strMyResult[2])) {

                //Intent to ListNews กรณี User & Password ถูก ไปเรียกใช้อีกไฟล์
                startActivity(new Intent(this, HeadListView.class));
            } else {
                errorDialog("Password False", "Please Try Again Password False");
            } // กรณี User ถูก Password ผิด
        } catch (Exception e) {
            errorDialog("ไม่มี User", "ไม่มี User " + userString + " ในฐานข้อมูลของเรา");
        } // กรณี User ผิด
    } //checkUser

    private void errorDialog(String strTitle, String strMessage) { //Method นี้ คนอื่นใช้ได้ด้วย  ใน Argument มีค่า 2 ค่า
        AlertDialog.Builder objBuilder = new AlertDialog.Builder(this); //สร้าง Popup
        objBuilder.setIcon(R.drawable.danger);
        objBuilder.setTitle(strTitle);
        objBuilder.setMessage(strMessage);
        objBuilder.setCancelable(false); // สั่งให้ไม่สามารถกดปุ่ม Undo บนมือถือได้
        objBuilder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() { //เมื่อไรกดปุ่ม ตกลง ให้ทำ OnclickListener
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            } // Event
        });
        objBuilder.show();

    } // errorDialog

    private void bindWidget() {
        userEditText = (EditText) findViewById(R.id.edtUser); //ผูกตัวแปรกับสิ่งที่อยู่บน ui
        passwordEditText = (EditText) findViewById(R.id.edtPassword);
        loginButton = (Button) findViewById(R.id.btnLogin);
    } // Bind Widget

    private void synJSONtoSQLite() {

        //Change Policy
        StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(myPolicy);

        int intTimes = 0;
        while (intTimes <= 1) { // 2 ตาราง intTimes = 0 กับ 1

            //1. Create InputStream
            InputStream objInputStream = null;
            String strJSON = null;
            String userURL = "http://swiftcodingthai.com/egat/php_get_data_tick.php"; // ตำแหน่งที่ Smartphone Connect กับไฟล์ PHP
            String newsURL = "http://swiftcodingthai.com/egat/php_get_data_news.php";
            HttpPost objHttpPost;

            try {
                HttpClient objHttpClient = new DefaultHttpClient();
                if (intTimes != 1) { // ใช้ Switch Case แทน ถ้ามากกว่า 2 รอบ
                    objHttpPost = new HttpPost(userURL);
                } else {
                    objHttpPost = new HttpPost(newsURL);
                }
                HttpResponse objHttpResponse = objHttpClient.execute(objHttpPost);
                HttpEntity objHttpEntity = objHttpResponse.getEntity();
                objInputStream = objHttpEntity.getContent();

            } catch (Exception e) {
                Log.d("egat", "InputStream ==> " + e.toString());
            }

            //2. Create JSON --> String
            try {
                BufferedReader objBuffereader = new BufferedReader(new InputStreamReader(objInputStream, "UTF-8"));
                StringBuilder objStringBuilder = new StringBuilder(); //StringBuilder เป็นตัวต่อ String จากที่ตัดไว้เป็นท่อนๆ (strLine)
                String strLine = null;
                while ((strLine = objBuffereader.readLine())!= null  ) {
                    objStringBuilder.append(strLine);
                } //while
                objInputStream.close();
                strJSON = objStringBuilder.toString();

            } catch (Exception e) {
                Log.d("egat", "strJson ==> " + e.toString());
            }

            //3. Create SQLite --> Change String to Data
            try {
                final JSONArray objJsonArray = new JSONArray(strJSON);

                for (int i = 0; i < objJsonArray.length(); i++) {
                    JSONObject jsonObject = objJsonArray.getJSONObject(i);
                    if (intTimes != 1) {
                        //For User Table
                        String strUser = jsonObject.getString("User");
                        String strPassword = jsonObject.getString("Password");
                        String strName = jsonObject.getString("Name");
                        objUserTABLE.addNewUser(strUser, strPassword, strName);

                    } else {
                        //For News Table
                        String strDate = jsonObject.getString("Date");
                        String strHead = jsonObject.getString("Head");
                        String strDetail = jsonObject.getString("Detail");
                        String strImage = jsonObject.getString("Image");
                        String strOwner = jsonObject.getString("Owner");
                        objNewsTABLE.addNews(strDate, strHead, strDetail, strImage, strOwner);
                    }
                } // วน Loop For ตามจำนวน record ใน Table

            } catch (Exception e) {
                Log.d("egat", "Update ==> " + e.toString());
            }

            intTimes += 1;
        } // while

    } //synJSONtoSQLite

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
