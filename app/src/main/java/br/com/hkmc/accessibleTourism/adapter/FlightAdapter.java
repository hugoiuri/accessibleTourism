package br.com.hkmc.accessibleTourism.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.hkmc.accessibleTourism.R;
import br.com.hkmc.accessibleTourism.models.Flight;

public class FlightAdapter extends RecyclerView.Adapter<FlightAdapter.MyViewHolder>{

    private List<Flight> lista;

    public FlightAdapter(List<Flight> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.flight_item, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Flight item = lista.get(position);
        holder.txtCia.setText(item.getCompany());
        holder.txtValue.setText(item.getValue());
        holder.txtGoingTime.setText(item.getGoingTime());
        holder.txtReturnsTime.setText(item.getReturnsTime());
        holder.txtCurrency.setText(item.getCurrency());
    }

    @Override
    public int getItemCount() {
        return this.lista.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txtCia;
        TextView txtValue;
        TextView txtReturnsTime;
        TextView txtGoingTime;
        TextView txtCurrency;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtCia = itemView.findViewById(R.id.txtCompanyId);
            txtValue = itemView.findViewById(R.id.txtValueId);
            txtGoingTime = itemView.findViewById(R.id.txtGoingTimeId);
            txtReturnsTime = itemView.findViewById(R.id.txtReturnsTimeId);
            txtCurrency = itemView.findViewById(R.id.txtCurrencyId);
        }
    }
}