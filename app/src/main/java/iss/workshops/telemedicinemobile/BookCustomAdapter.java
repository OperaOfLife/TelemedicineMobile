package iss.workshops.telemedicinemobile;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import iss.workshops.telemedicinemobile.domain.Doctor;

public class BookCustomAdapter extends ArrayAdapter<Doctor> {
    private static final String TAG = "CustomAdapter";
    private Context context;
    int mResource;

    public BookCustomAdapter( Context context, int resource, ArrayList<Doctor> doctors) {
        super(context, resource, doctors);
        this.context = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //responsible for display custom view
        //get information first
        String name = getItem(position).getFirstName();
        String special = getItem(position).getSpeciality();

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(mResource,parent, false);
        TextView tvName = (TextView) convertView.findViewById(R.id.docname);
        TextView tvSpecial = (TextView) convertView.findViewById(R.id.docspecial);

        tvName.setText(name);
        tvSpecial.setText(special);

        return convertView;
    }

}