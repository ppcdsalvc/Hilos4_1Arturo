package com.example.l_z0k.hilounidad4;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  {


    Button btnhilo,btnasynk;
    TextView txtContador,txtContador2;
    EditText edtNumero;
    ProgressBar progressBar1,progressBar2;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnhilo =  findViewById(R.id.botonhilo);
        btnasynk =  findViewById(R.id.botonasynk);
        txtContador =  findViewById(R.id.textView);
        txtContador2 =  findViewById(R.id.textView2);
        edtNumero =  findViewById(R.id.edtNumero);
        progressBar1 =  findViewById(R.id.progressBar1);
        progressBar2 =  findViewById(R.id.progressBar2);

        btnhilo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    try{
                        hilos(Integer.parseInt(edtNumero.getText().toString()));

                    }catch (Exception e){
                        Toast.makeText(MainActivity.this, ""+e, Toast.LENGTH_SHORT).show();
                    }

            }
        });
        btnasynk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    AsyncTarea asyncTarea = new AsyncTarea();
                    asyncTarea.execute(Integer.parseInt(edtNumero.getText().toString()));
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, ""+e, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    public void hilos(final int numero) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                i =0;
                progressBar2.setMax(numero);
                progressBar2.setProgress(0);
                while(i<=numero-1){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    i++;
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            progressBar2.setProgress(i);
                            txtContador2.setText("progress "+i);
                        }
                    });
                }
            }
        }).start();
    }

    private class  AsyncTarea extends AsyncTask<Integer, Integer,Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Boolean doInBackground(Integer... params) {
            progressBar1.setMax(params[0]);
            for (int i=1; i<=params[0]; i++){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                publishProgress(i);

                if (isCancelled()){
                    break;
                }
            }
            return true;
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar1.setProgress(values[0].intValue());
            txtContador.setText("progress "+((values[0].intValue())));
        }

    }
}