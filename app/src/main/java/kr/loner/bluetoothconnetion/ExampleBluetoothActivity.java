package kr.loner.bluetoothconnetion;

import android.bluetooth.BluetoothAdapter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import eu.basicairdata.bluetoothhelper.BluetoothHelper;

public class ExampleBluetoothActivity extends AppCompatActivity {

    BluetoothAdapter mBluetoothAdapter = null;
    BluetoothHelper mBluetooth = new BluetoothHelper();
    private SeekBar mSeekBar;
    private TextView mTextViewStatus;
    boolean greenLed = false;
    boolean yellowLed = false;
    boolean redLed = false;
    private String DEVICE_NAME = "HC-06";            // The name of the remote device (HC-05)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);  // force portrait screen
        setContentView(R.layout.activity_bluetooth_helper_example);

        mTextViewStatus = (TextView) findViewById(R.id.tv_connetionCheck);

        // Check if Bluetooth is supported by the device
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            finish();
        }

        mTextViewStatus.setText("기기에 연결되었습니다!! 기기명:" + DEVICE_NAME);
        mBluetooth.Connect(DEVICE_NAME);


        // Setup listener for Bluetooth helper;
        mBluetooth.setBluetoothHelperListener(new BluetoothHelper.BluetoothHelperListener() {
            @Override
            public void onBluetoothHelperMessageReceived(BluetoothHelper bluetoothhelper, String message) {
                // Do your stuff with the message received !!!
                // runOnUiThread(new Runnable() {
                //     @Override
                //     public void run() {
                //         // Update here your User Interface
                //     }
                // });
            }

            @Override
            public void onBluetoothHelperConnectionStateChanged(BluetoothHelper bluetoothhelper, boolean isConnected) {
                if (isConnected) {
                    mTextViewStatus.setText("기기가 연결됬습니다 기기명:" + DEVICE_NAME);
                } else {
                    mTextViewStatus.setText("아직 연결된 기기가 없습니다..");
                    // Auto reconnect!
                    mBluetooth.Connect(DEVICE_NAME);
                }
            }
        });
    }

    public void onGreenButtonClick(View view) {
        if (mBluetooth.isConnected()) {
            greenLed = !greenLed;
            mBluetooth.SendMessage(greenLed ? "1" : "a");

        }
    }

    public void onYellowButtonClick(View view) {
        if (mBluetooth.isConnected()) {
            yellowLed = !yellowLed;
            mBluetooth.SendMessage(yellowLed ? "2" : "b");

        }
    }

    public void onRedButtonClick(View view) {
        if (mBluetooth.isConnected()) {
            redLed = !redLed;
            mBluetooth.SendMessage(redLed ? "3" : "c");

        }
    }

}


