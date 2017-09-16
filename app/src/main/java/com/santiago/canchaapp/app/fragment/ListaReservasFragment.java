package com.santiago.canchaapp.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.santiago.canchaapp.R;
import com.santiago.canchaapp.app.otros.TipoReservas;

public class ListaReservasFragment extends Fragment {

    private static String ARG_RESERVES_TYPE = "reserves_type";
/*
    private RecyclerView reservesRecyclerView;

    private RecyclerView.LayoutManager layoutManager;

    private ReserveAdapter adapter;

    private List<Reserve> reserves;
*/
    public ListaReservasFragment() {
    }

    public static ListaReservasFragment newInstance(TipoReservas type) {
        ListaReservasFragment fragment = new ListaReservasFragment();
        Bundle args = new Bundle();
        args.putCharSequence(ARG_RESERVES_TYPE, type.toString());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //loadReservesData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lista_reservas, container, false);
        ((TextView)rootView.findViewById(R.id.tab_label)).setText(getArguments().getCharSequence(ARG_RESERVES_TYPE));
        //loadReservesView(rootView);

        return rootView;
    }
/*
    private void loadReservesView(View rootView) {
        // General
        reservesRecyclerView = rootView.findViewById(R.id.reserves_recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        reservesRecyclerView.setLayoutManager(layoutManager);

        // Adapter
        adapter = new ReserveAdapter(reserves);
        reservesRecyclerView.setAdapter(adapter);
    }

    private void loadReservesData() {
        ReservePageType type = ReservePageType.valueOf(getArguments().getString(ARG_RESERVES_TYPE));
        switch(type) {
            case APPROVED:
                reserves = Arrays.asList(
                        new Reserve("Da Vinci", FUTBOL5, "Buenos Aires 2358", "27/09/17", 16),
                        new Reserve("Da Vinci", FUTBOL5, "Buenos Aires 2358", "01/10/17", 16),
                        new Reserve("Da Vinci", FUTBOL5, "Buenos Aires 2358", "02/10/17", 19),
                        new Reserve("Los Troncos", TENNIS, "Italia 2580", "03/10/17", 15),
                        new Reserve("Da Vinci", FUTBOL5, "Buenos Aires 2358", "03/10/17", 21),
                        new Reserve("Club Mitre", FUTBOL7, "Avellaneda 4433", "04/10/17", 11),
                        new Reserve("Los Troncos", TENNIS, "Italia 2580", "04/10/17", 16),
                        new Reserve("Da Vinci", FUTBOL5, "Buenos Aires 2358", "04/10/17", 22),
                        new Reserve("Club Mitre", FUTBOL7, "Avellaneda 4433", "30/10/17", 14),
                        new Reserve("Da Vinci", FUTBOL5, "Buenos Aires 2358", "1/11/17", 22)
                );
                break;
            case CANCELLED:
                reserves = Arrays.asList(
                        new Reserve("Da Vinci", FUTBOL5, "Buenos Aires 2358", "29/09/17", 20)
                );
                break;
            case PENDING:
                reserves = Arrays.asList(
                        new Reserve("Da Vinci", FUTBOL5, "Buenos Aires 2358", "29/09/17", 21),
                        new Reserve("Los Troncos", TENNIS, "Italia 2580", "06/10/17", 17),
                        new Reserve("Los Troncos", TENNIS, "Italia 2580", "06/10/17", 18)
                );
                break;
        }
    }*/
}