package kr.loner.bluetoothconnetion;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btn_onOff;
    Button btn_sub;
    Button btn_gotoTheSample;
    BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_onOff = findViewById(R.id.btn_onOff);
        btn_sub = findViewById(R.id.btn_sub);
        btn_gotoTheSample = findViewById(R.id.btn_gotoTheSample);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        btn_onOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("onCick", "btEnableCheck");
                enableDisableBT();
            }
        });

        btn_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SubActivity.class));
            }
        });
        btn_gotoTheSample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ExampleBluetoothActivity.class));
            }
        });
    }

    private void enableDisableBT() {
        if (bluetoothAdapter == null) {
            Log.d("blueToothNullCheck", "잡힌 블루투스가 하나도 없습니다.");
        }
        if (!bluetoothAdapter.isEnabled()) {
            Log.d("blueToothNullCheck", "블루투스가 활성화를 시도합니다.");
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBTIntent);

            IntentFilter bTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(mBroadCastReceiver, bTIntent);
        }
        if (bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.disable();
            IntentFilter bTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);

            registerReceiver(mBroadCastReceiver, bTIntent);
            Log.d("blueToothNullCheck", "블루투스가 활성화 되었습니다.");

        }


    }

    private final BroadcastReceiver mBroadCastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action.equals(bluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, bluetoothAdapter.ERROR);

                switch (state) {
                    case BluetoothAdapter.STATE_ON:
                        Log.d("blueToothChangeCheck", "onReceive State Off");
                        break;
                    case BluetoothAdapter.STATE_OFF:
                        Log.d("blueToothChangeCheck", "onReceive State on");
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        Log.d("blueToothChangeCheck", "onReceive turning on");
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        Log.d("blueToothChangeCheck", "onReceive turning off");
                        break;
                }
            }
        }
    };

}


