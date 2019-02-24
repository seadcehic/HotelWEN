package android.fit.ba.hotelwien.fragments;


import android.fit.ba.hotelwien.data.GetRezervacijuResultVM;
import android.fit.ba.hotelwien.helper.MyApiRequest;
import android.fit.ba.hotelwien.helper.MyRunnable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.fit.ba.hotelwien.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link odredjenaRezervacija#newInstance} factory method to
 * create an instance of this fragment.
 */
public class odredjenaRezervacija extends Fragment {

    public static final String ARG_PARAM1="key";
    private String rezervacijaID;
    private GetRezervacijuResultVM x;
    private TextView trajanjeRezervacije;
    private TextView nazivSobe;
    private TextView cijenaRezervacije;

    public static odredjenaRezervacija newInstance(int rezervacijaID) {
        odredjenaRezervacija fragment = new odredjenaRezervacija();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, String.valueOf(rezervacijaID));

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            rezervacijaID = getArguments().getString(ARG_PARAM1);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.detalji_rezervacije,container,false);

        nazivSobe = view.findViewById(R.id.nazivSobe);
        trajanjeRezervacije = view.findViewById(R.id.trajanjeRezervacije);
        cijenaRezervacije = view.findViewById(R.id.cijenaRezervacije);

        PopuniPodatke();
        return view;
    }

    private void PopuniPodatke() {
        MyApiRequest.get(getActivity(), "api/GetRezervaciju/"+rezervacijaID, new MyRunnable<GetRezervacijuResultVM>() {



            public void run(GetRezervacijuResultVM s){
                x=s;
                PopuniPolja();
            }
        });
    }

    private void PopuniPolja() {

        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
        String OD=sdf.format(x.DatumPocetka);
        String DO=sdf.format(x.DatumZavrsetka);

        nazivSobe.setText(x.NazivSobe);
        trajanjeRezervacije.setText(OD+"   -   "+DO);
        cijenaRezervacije.setText(x.Cijena+" KM");
    }

}
