package android.fit.ba.hotelwien.fragments;


import android.content.Intent;
import android.fit.ba.hotelwien.MainActivity;
import android.fit.ba.hotelwien.data.GetSobu;
import android.fit.ba.hotelwien.data.Global;
import android.fit.ba.hotelwien.data.RezervacijaPostVM;
import android.fit.ba.hotelwien.helper.MyApiRequest;
import android.fit.ba.hotelwien.helper.MyRunnable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.fit.ba.hotelwien.R;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link odredjenaSoba#newInstance} factory method to
 * create an instance of this fragment.
 */
public class odredjenaSoba extends Fragment {

    public static final String ARG_PARAM1="key";
    public static final String ARG_PARAM2="keykey";
    public static final String ARG_PARAM3="keykeykey";
    public static final String ARG_PARAM4="s";
    public static final String ARG_PARAM5="a";
    private TextView nazivSobe;
    private GetSobu podaci;
    private int sobaId;
    private TextView trajanjeRezervacije;
    private String datumOD;
    private String datumDO;
    private TextView cijena;
    private String nazivsobe;
    private String cijenaa;

    public static odredjenaSoba newInstance(String ukupnaCijena, int sobaID, String nazivSobe, String datumOD, String datumDO) {
        odredjenaSoba fragment = new odredjenaSoba();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1,sobaID);
        args.putSerializable(ARG_PARAM2,datumOD);
        args.putSerializable(ARG_PARAM3,datumDO);
        args.putSerializable(ARG_PARAM4,nazivSobe);
        args.putSerializable(ARG_PARAM5,ukupnaCijena);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            sobaId = (int) getArguments().getSerializable(ARG_PARAM1);
            datumOD = (String) getArguments().getSerializable(ARG_PARAM2);
            datumDO = (String) getArguments().getSerializable(ARG_PARAM3);
            nazivsobe= (String) getArguments().getSerializable(ARG_PARAM4);
            cijenaa = (String) getArguments().getSerializable(ARG_PARAM5);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rezervisanje_termina, container, false);
        nazivSobe = view.findViewById(R.id.nazivSobe);
        trajanjeRezervacije = view.findViewById(R.id.trajanjeRezervacije);
        cijena = view.findViewById(R.id.cijenaRezervacije);
        PopuniPodatke();
        view.findViewById(R.id.btnRezervisi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RezervisiSobu();
            }
        });


        return view;
    }

    private void RezervisiSobu() {
        int turistID= Global.prijavljeniTurist.TuristID;
        RezervacijaPostVM soba=new RezervacijaPostVM();
        soba.TuristId=turistID;
        soba.SobaId=sobaId;
        soba.OD=datumOD;
        soba.DO=datumDO;
       MyApiRequest.post(getActivity(), "api/sobe/postsoba", soba, new MyRunnable<RezervacijaPostVM>() {
           public void run(RezervacijaPostVM x) {
               Toast.makeText(getActivity(), "Rezervacija uspjesno spasena.", Toast.LENGTH_SHORT).show();
           }
       });
      startActivity(new Intent(getActivity(), MainActivity.class));

    }
    private void PopuniPodatke() {

        nazivSobe.setText(nazivsobe);
        trajanjeRezervacije.setText(datumOD+"   ---   "+datumDO);
        cijena.setText(cijenaa +" KM");

    }

}
