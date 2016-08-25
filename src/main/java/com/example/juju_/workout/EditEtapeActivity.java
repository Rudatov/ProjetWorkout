package com.example.juju_.workout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by juju_ on 22/08/2016.
 */
public class EditEtapeActivity extends AppCompatActivity {
    final String EXTRA_NOM_ETAPE="etape_string";
    final String EXTRA_DESC_ETAPE="desc_string";
    final String EXTRA_TEMPS_ETAPE="temps_string";
    final String EXTRA_PAUSE_ETAPE="pause_string";

    public Etape etape_tmp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_editetape);

        Intent intent = getIntent();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void buttonPressed(View view){
        switch (view.getId()){
            case R.id.button_back_edit :
                finish();
                break;

            case R.id.button_etape_add :
                EditText titre_tmp = (EditText) findViewById(R.id.edit_etape_nom);
                EditText desc_tmp = (EditText) findViewById(R.id.edit_etape_desc);
                EditText temps_tmp = (EditText) findViewById(R.id.edit_etape_temps);
                EditText pause_tmp = (EditText) findViewById(R.id.edit_etape_pause);
                Intent intent = new Intent(EditEtapeActivity.this, AddEtapeActivity.class);
                Toast toast = Toast.makeText(getApplicationContext(),"Data missing, please check again",Toast.LENGTH_LONG);

                if((titre_tmp==null)||(temps_tmp==null)||(pause_tmp==null)){
                    toast.setText("Data missing, please check again");
                    toast.show();
                    break;
                }
                else {
                    if(titre_tmp.getText().toString().equals("")){
                        toast.setText("Exercice name is missing");
                        toast.show();
                        break;
                    }
                    if(temps_tmp.getText().toString().equals("")){
                        toast.setText("Timer is missing.\n If you don\'t want it, please set it to 0");
                        toast.show();
                        break;
                    }
                    if(pause_tmp.getText().toString().equals("")){
                        toast.setText("Pause is missing.\n" +
                                " If you don't want it, please set it to 0");
                        toast.show();
                        break;
                    }
                    intent.putExtra(EXTRA_NOM_ETAPE, titre_tmp.getText().toString());
                    if(desc_tmp==null){
                        intent.putExtra(EXTRA_DESC_ETAPE, "");
                    }
                    else {
                        intent.putExtra(EXTRA_DESC_ETAPE, desc_tmp.getText().toString());
                    }
                    intent.putExtra(EXTRA_TEMPS_ETAPE, temps_tmp.getText().toString());
                    intent.putExtra(EXTRA_PAUSE_ETAPE, pause_tmp.getText().toString());
                    System.out.println(temps_tmp.getText().toString() + ":::" + pause_tmp.getText().toString());
                    setResult(1, intent);
                    finish();
                    break;
                }
        }
    }

}

