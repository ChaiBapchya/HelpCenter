package com.gpnv.helpcenter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gpnv.helpcenter.main_activity_fragments.Medicine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GANESH on 3/17/2016.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MedicineViewHolder> {

    @Override
    public CustomAdapter.MedicineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_medication, parent, false);
        MedicineViewHolder mMedicineViewHolder = new MedicineViewHolder(v);

        return mMedicineViewHolder;
    }

    @Override
    public void onBindViewHolder(CustomAdapter.MedicineViewHolder holder, int position) {
        holder.medicineName.setText(medicines.get(position).name);
        holder.medicineTime.setText(medicines.get(position).time);
        holder.medicineStock.setText(medicines.get(position).stock);

    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    @Override
    public int getItemCount() {
        return medicines.size();
    }
    List<Medicine> medicines;

    public CustomAdapter(ArrayList<Medicine> medicines){
        this.medicines = medicines;
    }
    public static class MedicineViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView medicineName;
        TextView medicineTime;
        TextView medicineStock;

        MedicineViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.medication_card_view);
            medicineName = (TextView)itemView.findViewById(R.id.medication_medicine_name);
            medicineTime = (TextView)itemView.findViewById(R.id.medication_medicine_time);
            medicineStock = (TextView)itemView.findViewById(R.id.medication_medicine_stock);
        }
    }

}