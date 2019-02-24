package android.fit.ba.hotelwien.fragments;


import android.content.Context;
import android.fit.ba.hotelwien.Util.Util;
import android.fit.ba.hotelwien.data.GetSobu;
import android.fit.ba.hotelwien.data.RezervacijeResultVM;
import android.fit.ba.hotelwien.helper.MyApiRequest;
import android.fit.ba.hotelwien.helper.MyRunnable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.fit.ba.hotelwien.R;

import java.text.SimpleDateFormat;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link listaRezervacija#newInstance} factory method to
 * create an instance of this fragment.
 */
public class listaRezervacija extends Fragment {

    public static final String ARG_PARAM1="keykey";
    private ListView listaRezervacija;
    private String turistID;
    private RezervacijeResultVM podaci;

    public static listaRezervacija newInstance(int TuristID) {
        listaRezervacija fragment = new listaRezervacija();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, String.valueOf(TuristID));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            turistID = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.rezervacije,container,false);

        listaRezervacija = view.findViewById(R.id.listaRezervacija);

        listaRezervacija.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RezervacijeResultVM.Row x=podaci.rows.get(position);
                int rezervacijaID=x.RezervacijaID;
                Util.otvoriFragmentKaoReplace(getActivity(),R.id.mjestoZaFragment, odredjenaRezervacija.newInstance(rezervacijaID));
            }
        });

        PopuniPolja();

        return view;
    }

    private void PopuniPolja() {
        MyApiRequest.get(getActivity(), "api/Rezervacije/getRezervacijeByTuristID/" + turistID, new MyRunnable<RezervacijeResultVM>() {
            public void run(RezervacijeResultVM x){
                podaci = x;
                PopuniPodatke();
            }
        });

    }

    private void PopuniPodatke() {
        if(podaci.rows.size()>0){
            listaRezervacija.setAdapter(new BaseAdapter() {
                @Override
                public int getCount() {
                    return podaci.rows.size();
                }

                @Override
                public Object getItem(int position) {
                    return null;
                }

                @Override
                public long getItemId(int position) {
                    return 0;
                }

                @Override
                public View getView(int position, View view, ViewGroup parent) {
                    if(view==null){
                        LayoutInflater inflater= (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        view=inflater.inflate(R.layout.stavka_rezervacije,parent,false);
                    }
                    TextView nazivSobe = view.findViewById(R.id.nazivSobe);
                    TextView vrijemeTermina = view.findViewById(R.id.vrijemeRezervacije);
                    TextView ukupno = view.findViewById(R.id.cijenaRezervacije);

                    RezervacijeResultVM.Row x=podaci.rows.get(position);

                    SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
                    String OD=sdf.format(x.DatumPocetka);
                    String DO=sdf.format(x.DatumZavrsetka);

                    nazivSobe.setText(x.NazivSobe);

                    vrijemeTermina.setText(OD+"   -   "+DO);
                    ukupno.setText(x.Cijena+"KM");
                    return view;
                }
            });
        }


    }



}
