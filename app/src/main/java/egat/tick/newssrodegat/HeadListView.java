package egat.tick.newssrodegat;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class HeadListView extends AppCompatActivity {

    //Explicit
    private ListView headListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_list_view);

        headListView = (ListView) findViewById(R.id.listView);

        // Create ListView
        createListView();

    } // onCreate

    private void createListView() {
        NewsTABLE objNewsTABLE = new NewsTABLE(this);
        final String strHead[] = objNewsTABLE.readAllHead();
        String strDate[] = objNewsTABLE.readAllDate();
        String strDetail[] = objNewsTABLE.readAllDetail();
        final String strImage[] = objNewsTABLE.readAllImage();
        String strOwner[] = objNewsTABLE.readAllOwner();
        final HeadAdapter objHeadAdapter = new HeadAdapter(HeadListView.this, strImage, strHead, strDate, strOwner);
        headListView.setAdapter(objHeadAdapter);

        headListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent objIntent = new Intent(HeadListView.this, DetailActivity.class);
                objIntent.putExtra("Head", strHead[i]);
                objIntent.putExtra("Image", strImage[i]);
                startActivity(objIntent);

            } // Event
        });

    } // createListView

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_head_list_view, menu);
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
