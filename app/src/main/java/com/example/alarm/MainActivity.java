package com.example.alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
	String text;
	int j = 0;
	TextView tv;
	final Handler myHandler = new Handler();
	Spinner dropdown;
	 boolean clickable = true;
	 Timer myTimer;
	Vibrator v;
	boolean vibration = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 tv = findViewById(R.id.textView3);
		int maxTime = 600;
		final Button startButton = (Button)findViewById(R.id.button);
		final Button stopButton = (Button)findViewById(R.id.button2);
		final Button vibButton = (Button)findViewById(R.id.button6);
		Button crashButton = (Button)findViewById(R.id.button5);
		//get the spinner from the xml.
		dropdown = findViewById(R.id.spinner1);
		//create a list of items for the spinner.

		String[] items = new String[maxTime + 1];
		for(int i = 0; i <= maxTime; i++){
			try {
				items[i] = String.valueOf(i)/* + " Seconds"*/;
			}catch(ArrayIndexOutOfBoundsException e) {
				System.out.println("Indexed value out of bounds");
			}catch(Exception e){
				System.out.println("Error: " + e.getMessage());
			}
		}

		//create an adapter to describe how the items are displayed, adapters are used in several places in android.
		//There are multiple variations of this, but this is the basic variant.
		ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

		//set the spinners adapter to the previously created one.
		dropdown.setAdapter(adapter);

		startButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (clickable) {
					clickable = false;
					myTimer = new Timer();
					myTimer.schedule(new TimerTask() {
						@Override
						public void run() {
							UpdateGUI();
						}
					}, 0, 1000);
				}
			}
		});
		text = String.valueOf("0 Seconds");
		tv.setText(text);


		stopButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				int testVal = 0;

				try{
					testVal = Integer.parseInt(text);
				}catch(Exception e){
					System.out.println("Error: " + e.getMessage());
				}
				if(testVal >= 0){


					if(myTimer != null) {
						j = 0;
						clickable = true;
						myTimer.cancel();
						myTimer.purge();
						myTimer = null;
					}
				}

			}
		});


		crashButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				crash();
			}
		});


		vibButton.setOnClickListener(new View.OnClickListener() {
			boolean vibColor = true;
			public void onClick(View v) {
				vibColor = !vibColor;
				vibration = !vibration;
				if(vibColor){
					vibButton.setBackgroundColor(0xFF00C300);
				}else if(!vibColor){
					vibButton.setBackgroundColor(0xFFFF0000);
				}
			}
		});
	}

	private void crash(){
//		throw null;//this is intentional, I want to see how my phone handles the app crashing
		//I will probably remove this at some point

		while(true){
			int i = 0;
			i += 1;
			System.out.println(i);
		}
	}

	private void UpdateGUI() {
		j++;
		//tv.setText(String.valueOf(i));
		myHandler.post(myRunnable);
	}

	final Runnable myRunnable = new Runnable() {
		public void run() {
			int val = 0;
			try {
				 val = Integer.parseInt(dropdown.getSelectedItem().toString());
			}catch(NumberFormatException e){
				System.out.println("NFE: " + e.getMessage());
			}
			text = String.valueOf((val + 1) - j);



			try
			{
				// the String to int conversion happens here
				int l = Integer.parseInt(text);

				// print out the value after the conversion
				System.out.println("int i = " + l);
			}
			catch (NumberFormatException nfe)
			{
				System.out.println("NumberFormatException: " + nfe.getMessage());
			}
			String temp = String.valueOf(text) + " Seconds";
			tv.setText(temp);
			int testVal = 0;

			try{
				testVal = Integer.parseInt(text);
			}catch(Exception e){
				System.out.println("Error: "+e.getMessage());
			}
			if(testVal <= 0){
				v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
				if(vibration == true) {
					//vibrate if the vibration button is not pressed
					try {
						// Vibrate for 500 milliseconds
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
							v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
						} else {
							//deprecated in API 26
							v.vibrate(500);
						}
					}catch(Exception e){
					System.out.println("Error: " + e.getMessage());
				}
				} else{
				//
				}
				if(myTimer != null) {
					j = 0;
					clickable = true;
					myTimer.cancel();
					myTimer.purge();
					myTimer = null;
				}
			}


		}
	};

	}

