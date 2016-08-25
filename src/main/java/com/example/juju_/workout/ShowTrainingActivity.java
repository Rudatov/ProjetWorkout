package com.example.juju_.workout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

/**
 * Created by juju_ on 22/08/2016.
 */
public class ShowTrainingActivity extends AppCompatActivity{
    final String EXTRA_POSITION_CHOICE="0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_training);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ListView listV= (ListView) findViewById(R.id.listView_show_training);

        // parser le doc des workout
        XMLDOMParser parser = new XMLDOMParser(this);
        Intent intent=getIntent();
        FileInputStream stream_file;
        try {

            //stream = getResources().openRawResource(R.raw.total_list_workout);
            // pour ecrire sur sd

            File file = new File(Environment.getExternalStorageDirectory().toString()+"/Workout/total_list_workout.xml");
            stream_file = new FileInputStream(file);

            Document doc = parser.getDocument(stream_file);
            String str_pos=intent.getStringExtra(EXTRA_POSITION_CHOICE);

            int position=Integer.parseInt(intent.getStringExtra(EXTRA_POSITION_CHOICE));

            NodeList nodeList = doc.getElementsByTagName("workout");

            final ArrayList<Workout> final_workouts = parser.getXMLWorkoutValue(nodeList);
            final Workout wo_tmp=final_workouts.get(position);
            TextView txtV_nom=(TextView) findViewById(R.id.textView_lrg_training_name);
            if(txtV_nom!=null) {
                txtV_nom.setText(wo_tmp.toString());
            }

            TrainingAdapter adapter=new TrainingAdapter(ShowTrainingActivity.this,wo_tmp.getList_etape());
            if(listV!=null) {
                listV.setAdapter(adapter);


                listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        view.findViewById(R.id.textView_go).setVisibility(View.GONE);
                        view.setOnClickListener(null);
                        final int pos=position;
                        final TimerView tmr_view=(TimerView) view.findViewById(R.id.time_etape_view);
                        if(tmr_view!=null) {
                            final TimerEtapeAnimation tmr_etp_anim = new TimerEtapeAnimation(tmr_view, 360);
                            tmr_etp_anim.setDuration(wo_tmp.getList_etape().get(position).getTemps() * 1000);
                            tmr_etp_anim.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    tmr_view.setPaint(Color.rgb(254,195,1));
                                    tmr_view.clearAnimation();
                                    tmr_view.setAngle(0);
                                    TimerEtapeAnimation tmr_etp_anim_2 = new TimerEtapeAnimation(tmr_view, 360);
                                    tmr_etp_anim_2.setDuration(wo_tmp.getList_etape().get(pos).getPause() * 1000);
                                    tmr_etp_anim_2.setAnimationListener(new Animation.AnimationListener() {
                                        @Override
                                        public void onAnimationStart(Animation animation) {
                                        }

                                        @Override
                                        public void onAnimationEnd(Animation animation) {
                                            tmr_view.setPaint(Color.rgb(19,142,0));
                                            tmr_view.clearAnimation();
                                            tmr_view.setAngle(0);
                                            TimerEtapeAnimation tmr_etp_anim_3 = new TimerEtapeAnimation(tmr_view, 360);
                                            tmr_etp_anim_3.setDuration(200);
                                            tmr_view.startAnimation(tmr_etp_anim_3);

                                        }

                                        @Override
                                        public void onAnimationRepeat(Animation animation) {
                                        }
                                    });

                                    tmr_view.startAnimation(tmr_etp_anim_2);
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {
                                }
                            });

                            tmr_view.startAnimation(tmr_etp_anim);

                        }
                    }
                });
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }// fin parseur


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
        switch(view.getId()){
            case R.id.button_back_showtrain :
                finish();
                break;
        }
    }
}
