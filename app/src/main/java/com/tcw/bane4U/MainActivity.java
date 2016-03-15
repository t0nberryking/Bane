package com.tcw.bane4U;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import java.io.IOException;


public class MainActivity extends Activity{

    private static final String PREFS = "prefs";
    private static final String PREF_NAME = "name";
    SharedPreferences mSharedPreferences;

    private Resources res;

	MediaPlayer mp;
    int counter = 0;

    private int[] buttonIds = {R.id.button01, R.id.button02, R.id.button03,
            R.id.button04, R.id.button05, R.id.button06,
            R.id.button07, R.id.button08, R.id.button09,
            R.id.button10, R.id.button11, R.id.button12,
            R.id.button13, R.id.button14, R.id.button15,
            R.id.button16, R.id.button17,
            R.id.button18, R.id.button19, R.id.button20,
            R.id.button21, R.id.button22, R.id.button23, R.id.button24
            };

    private int[] soundIds =  { R.raw.foryou, R.raw.bane, R.raw.bigguy,
            R.raw.drpavel, R.raw.imcia, R.raw.themaskedman,
            R.raw.callitin, R.raw.crashing, R.raw.extremelypainful,
            R.raw.firsttotalk, R.raw.flightplan, R.raw.flysogood, R.raw.ofcoursh, R.raw.friends,
            R.raw.grats, R.raw.ifipull, R.raw.isaidnothing,
            R.raw.loyalty, R.raw.plan, R.raw.nosurvivors,
            R.raw.planesound, R.raw.tellme,
            R.raw.thefirerises, R.raw.masterplan};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		

		RelativeLayout background = (RelativeLayout) findViewById(R.id.banelayout);
		background.setBackgroundResource(R.drawable.bg);

        mp = new MediaPlayer();
        res = this.getResources();

        for(int i = 0, n = buttonIds.length; i < n; i++) {
            Button button = (Button) findViewById(buttonIds[i]);
            button.setOnClickListener(new SoundClickListener(soundIds[i]));
            Log.d("button", i + " " + buttonIds[i] + " " + soundIds[i]);
        }

//        final Button button1 = (Button) findViewById(R.id.foryou);
//        button1.setOnClickListener(this);
//
//        final Button button2 = (Button) findViewById(R.id.bane);
//        button2.setOnClickListener(this);
//
//        final Button button3 = (Button) findViewById(R.id.bigguy);
//        button3.setOnClickListener(this);
//
//        final Button button4 = (Button) findViewById(R.id.drpavel);
//        button4.setOnClickListener(this);
//
//        final Button button5 = (Button) findViewById(R.id.imcia);
//        button5.setOnClickListener(this);
//
//        final Button button6 = (Button) findViewById(R.id.masked);
//        button6.setOnClickListener(this);

        // 7. Greet the user, or ask for their name if new
        displayWelcome();
	}

    private class SoundClickListener implements OnClickListener {
        private int id;

        public SoundClickListener(int soundId) {
            id = soundId;
        }


        @Override
        public void onClick(View v) {
            mp.reset();
            try {
                AssetFileDescriptor fd = getResources().openRawResourceFd(id);
                mp.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
                Log.d("Id", id + "");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                mp.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mp.start();
        }
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	   @Override
	    protected void onDestroy() {
	        mp.release();
	        super.onDestroy();
	    }


    public void displayWelcome() {

        // Access the device's key-value storage
        mSharedPreferences = getSharedPreferences(PREFS, MODE_PRIVATE);

        // Read the user's name,
        // or an empty string if nothing found
        String name = mSharedPreferences.getString(PREF_NAME, "");

        if (name.length() > 0) {

            // If the name is valid, display a Toast welcoming them
            Toast.makeText(this, "Welcome back, " + name + "!", Toast.LENGTH_LONG).show();
        }

        else {

            // otherwise, show a dialog to ask for their name
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Bane?");
            alert.setMessage("What is your name?");

            // Create EditText for entry
            final EditText input = new EditText(this);
            alert.setView(input);

            // Make an "OK" button to save the name
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int whichButton) {

                    // Grab the EditText's input
                    String inputName = input.getText().toString();

                    // Put it into memory (don't forget to commit!)
                    SharedPreferences.Editor e = mSharedPreferences.edit();
                    e.putString(PREF_NAME, inputName);
                    e.commit();

                    // Welcome the new user
                    Toast.makeText(getApplicationContext(), "Welcome, " + inputName + "!", Toast.LENGTH_LONG).show();
                }
            });

            // Make a "Cancel" button
            // that simply dismisses the alert
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int whichButton) {}
            });

            alert.show();
        }
    }
}
