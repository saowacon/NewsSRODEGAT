package egat.tick.newssrodegat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

/**
 * Created by EGAT-USER on 8/28/2015.
 */
public class HeadAdapter extends BaseAdapter {
    //Explicit
    private Context objContext;
    private String[] sourceImageStrings, headStrings, dateStrings, ownerStrings;

    public HeadAdapter(Context objContext, String[] sourceImageStrings, String[] headStrings, String[] dateStrings, String[] ownerStrings) {
        this.objContext = objContext;
        this.sourceImageStrings = sourceImageStrings;
        this.headStrings = headStrings;
        this.dateStrings = dateStrings;
        this.ownerStrings = ownerStrings;
    }

    @Override
    public int getCount() {
        return headStrings.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater objLayoutInflater = (LayoutInflater) objContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View objView1 = objLayoutInflater.inflate(R.layout.head_listview, viewGroup, false);

        //Show Icon ใช้ Picasso ช่วย เพราะเป็นรูปที่ Link Path จาก Server ที่สามารถเปิดได้บน Web Browser
        ImageView iconImageView = (ImageView) objView1.findViewById(R.id.imvShowIcon);
        Picasso.with(objContext).load(sourceImageStrings[i]).into(iconImageView);

        //Show Head
        TextView headTextView = (TextView) objView1.findViewById(R.id.txtShowHead); // อย่าลืม Cast
        headTextView.setText(headStrings[i]);

        //Show Date
        TextView dateTextView = (TextView) objView1.findViewById(R.id.txtShowDate); // อย่าลืม Cast
        dateTextView.setText(dateStrings[i]);

        //Show Owner
        TextView ownerTextView = (TextView) objView1.findViewById(R.id.txtShowOwner); // อย่าลืม Cast
        ownerTextView.setText(ownerStrings[i]);

        return objView1;
    } // getView
} // Main Class
