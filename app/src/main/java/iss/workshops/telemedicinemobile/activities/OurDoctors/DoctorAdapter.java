package iss.workshops.telemedicinemobile.activities.OurDoctors;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import iss.workshops.telemedicinemobile.R;
import iss.workshops.telemedicinemobile.activities.HealthNews.DetailActivity;
import iss.workshops.telemedicinemobile.activities.HealthNews.ParseItem;
import iss.workshops.telemedicinemobile.domain.Doctor;
import iss.workshops.telemedicinemobile.domain.Patient;


public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder> {

    private Context context;
    private List<Doctor> doctorList;
    private Patient patient;

    public DoctorAdapter(Context context, List<Doctor> doctorList,Patient patient) {
        this.context = context;
        this.doctorList = doctorList;
        this.patient=patient;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctors_list, parent, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Doctor doctor = doctorList.get(position);
        holder.Name.setText(doctor.getFirstName() + " " + doctor.getLastName());
        holder.speciality.setText(doctor.getSpeciality());


    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView Name, speciality;
        ImageView photo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.Name);
            speciality = itemView.findViewById(R.id.speciality);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View itemView) {
            int position = getAdapterPosition();
            Doctor doctor = doctorList.get(position);

            Intent intent = new Intent(context, DoctorDetailsActivity.class);
            intent.putExtra("name", doctor.getFirstName() + " " + doctor.getLastName());
            intent.putExtra("speciality", doctor.getSpeciality());
            intent.putExtra("description", doctor.getDescription());
            intent.putExtra("patient",patient);
            intent.putExtra("doctor",doctor);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
            context.startActivity(intent);
        }
    }
}
