package uwb.aaron.com.otherappservicetest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import uwb.aaron.com.servicestarterlib.serviceConnection;

public class MainActivity extends Activity implements View.OnClickListener {

    private serviceConnection sc;
    private Button start;
    private Button stop;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sc = new serviceConnection(this);
        start = (Button)findViewById(R.id.button);
        stop = (Button)findViewById(R.id.button2);

        start.setOnClickListener(this);
        stop.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:{
                Log.d("LOOK","start clicked");
                sc.startDroneService();
                break;
            }

            case R.id.button2:{
                Log.d("LOOK","stop clicked");
                sc.stopDroneService();
                break;
            }
        }
    }
}
