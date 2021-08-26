package iss.workshops.telemedicinemobile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import iss.workshops.telemedicinemobile.domain.Clinic;


public class PatientClinicListAdapter extends RecyclerView.Adapter<iss.workshops.telemedicinemobile.PatientClinicListAdapter.MyViewHolder> {

    private Context mContext;
    private List<Clinic> clinicList;

    public PatientClinicListAdapter(Context mContext, List<Clinic> clinicList) {
        this.mContext = mContext;
        this.clinicList = clinicList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext()); //from (mContext) here
        v = layoutInflater.inflate(R.layout.activity_patient_clinic_list_item, parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.name.setText(clinicList.get(position).getClinicName());
        holder.address.setText(clinicList.get(position).getAddress());
        holder.phone.setText(clinicList.get(position).getPhoneNo());
        holder.hours.setText(clinicList.get(position).getOpeningHours());

        holder.directions.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                double lat = clinicList.get(position).getLat();
                double lng = clinicList.get(position).getLng();
                String label = clinicList.get(position).getClinicName();
                String latlng = "geo:" + lat + "," + lng + "?q=" + label;
                Uri uri = Uri.parse(latlng);
                Intent intentToMap = new Intent(Intent.ACTION_VIEW, uri);

                // Make the Intent explicit by setting the Google Maps package
                intentToMap.setPackage("com.google.android.apps.maps");

                v.getContext().startActivity(intentToMap);
            }
        });
    }

    @Override
    public int getItemCount() {

        if (clinicList != null) {
            return clinicList.size();
        }
        else {
            return 0;
        }

    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        //instantiate widgets
        TextView name;
        TextView address;
        TextView phone;
        TextView hours;
        Button directions;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //place respective widgets into viewholders
            name = itemView.findViewById(R.id.clinicName);
            address = itemView.findViewById(R.id.address);
            phone = itemView.findViewById(R.id.phone);
            hours = itemView.findViewById(R.id.openingHours);
            directions = itemView.findViewById(R.id.directionsBtn);

        }
    }

}