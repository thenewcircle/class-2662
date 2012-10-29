package com.marakana.fibnative;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	EditText input;
	TextView output;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        input = (EditText) findViewById(R.id.input);
        output = (TextView) findViewById(R.id.output);
    }

    public void onClickGo(View v) {
    		long n = Long.parseLong( input.getText().toString() );
    		
    		FibLib.version("FibLib v.1.2");
    		
    		long start = System.nanoTime();
    		long resultJ = FibLib.fibJI(n);
    		long timeJ = System.nanoTime() - start;
    		output.append( String.format("\nfibJI(%d)=%d (%d ns)", n, resultJ, timeJ) );
    		
    		start = System.nanoTime();
    		long resultN = FibLib.fibNI(n);
    		long timeN = System.nanoTime() - start;
    		output.append( String.format("\nfibNI(%d)=%d (%d ns)", n, resultN, timeN) );
    		
    }
}
