package id.co.imastudio.firebaseappandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.etMakanan)
    EditText etMakanan;
    @BindView(R.id.etHarga)
    EditText etHarga;
    @BindView(R.id.etRestoran)
    EditText etRestoran;
    @BindView(R.id.btnTambah)
    Button btnTambah;
    DatabaseReference myRef;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");

//        myRef.setValue("Hello, World!");


        etMakanan.addTextChangedListener(onTextChangedListener());


      //  etMakanan.addTextChangedListener(new FourDigitCardFormatWatcher());


        getData();

    }

    private TextWatcher onTextChangedListener() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                etMakanan.removeTextChangedListener(this);

                try {
                    String originalString = s.toString();

                    Long longval;
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalString);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    formatter.applyPattern("#,###,###,###");
                    String formattedString = formatter.format(longval);

                    //setting text after format to EditText
                    etMakanan.setText(formattedString);
                    etMakanan.setSelection( etMakanan.getText().length());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                etMakanan.addTextChangedListener(this);
            }
        };
    }

    //ambil data from firebase berdasarkan child
    private void getData() {
        //bikin array untuk nampung hasil looping dari firebase
        final ArrayList<Makanan> data = new ArrayList<>();

        final Makanan m = new Makanan();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                    Makanan makan = dataSnapshot1.getValue(Makanan.class);
                    m.setName(makan.name);
                    m.setRestoran(makan.restoran);
                    m.setHarga(makan.harga);


                    //masukin data object dari firebase ke array
                    data.add(m);
                }
                //array masukin k adapter
                MyAdapter adapter = new MyAdapter(data);
                //adapter masukin k recyclerview
                recyclerview.setAdapter(adapter);
                //settin layout manager
                recyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this));


                //recyclerview

                Log.d("data firebse", data.toString());


                // Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                // Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }


    @OnClick(R.id.btnTambah)
    public void onViewClicked() {

        //ambil inputan user
        String makanan = etMakanan.getText().toString();
        String harga = etHarga.getText().toString();
        String restoran = etRestoran.getText().toString();

        //masukin k object
        Makanan makan = new Makanan();
        makan.setHarga(harga);
        makan.setName(makanan);
        makan.setRestoran(restoran);

        //ambil key.atau id,biar nanti kalau mau update sama hapus lebih gmpang
        //update berdasarkan key nya aja
        String key = myRef.push().getKey();

        //insert to firebase
        myRef.child(key).setValue(makan);
    }
}
