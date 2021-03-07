package com.example.yj.bluetoothapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.content.Intent;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created by 강호 리 on 2016-08-22.
 */
public class control_activity extends Activity {
    private static final String TAG = "bluetooth2";

    Button on, off;
    String room_num;
    String btn_number;
    TextView btnText;
    TextView txtArduino;
    RelativeLayout rlayout;
    Handler h;

    final int RECIEVE_MESSAGE = 1;        // Status  for Handler
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder sb = new StringBuilder();
    private static int flag = 0;

    private ConnectedThread mConnectedThread;

    // SPP UUID service
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


    private static String address = "98:D3:31:FC:38:6A";


    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_control);

        btAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
        checkBTState();

        //Intent intent = new Intent(this, control_activity.class);
        //Intent intent = getIntent();

        btn_number = this.getIntent().getStringExtra("btn_num");
        room_num = this.getIntent().getStringExtra("room_num").toString();

        on = (Button) findViewById(R.id.on);
        off = (Button) findViewById(R.id.off);
        btnText = (TextView)findViewById(R.id.lightText);
        btnText.setText("LED "+btn_number);

        on.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            // give the correponding number with the function
                if (room_num.equals("101") == true) {
                    if (btn_number.equals("one") == true) {
                        mConnectedThread.write("1");
                        Toast.makeText(getBaseContext(), "Turn on Room 101 FIRST LED", Toast.LENGTH_SHORT).show();
                    } else if (btn_number.equals("two") == true) {
                        mConnectedThread.write("2");
                        Toast.makeText(getBaseContext(), "Turn on Room 101 SECOND LED", Toast.LENGTH_SHORT).show();
                    } else if (btn_number.equals("three") == true) {
                        mConnectedThread.write("3");
                        Toast.makeText(getBaseContext(), "Turn on Room 101 THIRD LED", Toast.LENGTH_SHORT).show();
                    } else if (btn_number.equals("four") == true) {
                        mConnectedThread.write("4");
                        Toast.makeText(getBaseContext(), "Turn on Room 101 FOURTH LED", Toast.LENGTH_SHORT).show();
                    }
                }

            else if(room_num.equals("102") == true){
                if (btn_number.equals("one") == true) {
                    mConnectedThread.write("11");
                    Toast.makeText(getBaseContext(), "Turn on Room 102 FIRST LED", Toast.LENGTH_SHORT).show();
                } else if (btn_number.equals("two") == true) {
                    mConnectedThread.write("12");
                    Toast.makeText(getBaseContext(), "Turn on Room 102 SECOND LED", Toast.LENGTH_SHORT).show();
                } else if (btn_number.equals("three") == true) {
                    mConnectedThread.write("13");
                    Toast.makeText(getBaseContext(), "Turn on Room 102 THIRD LED", Toast.LENGTH_SHORT).show();
                } else if (btn_number.equals("four") == true) {
                    mConnectedThread.write("14");
                    Toast.makeText(getBaseContext(), "Turn on Room 102 FOURTH LED", Toast.LENGTH_SHORT).show();
                }
            }
            }
        });

        off.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (room_num.equals("101") == true) {
                    if (btn_number.equals("one") == true) {
                        mConnectedThread.write("5");
                        Toast.makeText(getBaseContext(), "Turn off Room 101 FIRST LED", Toast.LENGTH_SHORT).show();
                    } else if (btn_number.equals("two") == true) {
                        mConnectedThread.write("6");
                        Toast.makeText(getBaseContext(), "Turn off Room 101 SECOND LED", Toast.LENGTH_SHORT).show();
                    } else if (btn_number.equals("three") == true) {
                        mConnectedThread.write("7");
                        Toast.makeText(getBaseContext(), "Turn off Room 101 THIRD LED", Toast.LENGTH_SHORT).show();
                    } else if (btn_number.equals("four") == true) {
                        mConnectedThread.write("8");
                        Toast.makeText(getBaseContext(), "Turn off Room 101 FOURTH LED", Toast.LENGTH_SHORT).show();
                    }
                } else if (room_num.equals("102") == true) {
                    if (btn_number.equals("one") == true) {
                        mConnectedThread.write("15");
                        Toast.makeText(getBaseContext(), "Turn off Room 102 FIRST LED", Toast.LENGTH_SHORT).show();
                    } else if (btn_number.equals("two") == true) {
                        mConnectedThread.write("16");
                        Toast.makeText(getBaseContext(), "Turn off Room 102 SECOND LED", Toast.LENGTH_SHORT).show();
                    } else if (btn_number.equals("three") == true) {
                        mConnectedThread.write("17");
                        Toast.makeText(getBaseContext(), "Turn off Room 102 THIRD LED", Toast.LENGTH_SHORT).show();
                    } else if (btn_number.equals("four") == true) {
                        mConnectedThread.write("18");
                        Toast.makeText(getBaseContext(), "Turn off Room 102 FOURTH LED", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


       final TextView tv=(TextView)findViewById(R.id.textView1);
        RatingBar rb=(RatingBar)findViewById(R.id.ratingBar);
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){

            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                tv.setText("밝기"+v);

            }
        });

    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        if(Build.VERSION.SDK_INT >= 10){
            try {
                final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", new Class[] { UUID.class });
                return (BluetoothSocket) m.invoke(device, MY_UUID);
            } catch (Exception e) {
                Log.e(TAG, "Could not create Insecure RFComm Connection",e);
            }
        }
        return  device.createRfcommSocketToServiceRecord(MY_UUID);
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "...onResume - try connect...");

        // Set up a pointer to the remote node using it's address.
        BluetoothDevice device = btAdapter.getRemoteDevice(address);

        // Two things are needed to make a connection:
        //   A MAC address, which we got above.
        //   A Service ID or UUID.  In this case we are using the
        //     UUID for SPP.

        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            errorExit("Fatal Error", "In onResume() and socket create failed: " + e.getMessage() + ".");
        }

        // Discovery is resource intensive.  Make sure it isn't going on
        // when you attempt to connect and pass your message.
        btAdapter.cancelDiscovery();

        // Establish the connection.  This will block until it connects.
        Log.d(TAG, "...Connecting...");
        try {
            btSocket.connect();
            Log.d(TAG, "....Connection ok...");
        } catch (IOException e) {
            try {
                btSocket.close();
            } catch (IOException e2) {
                errorExit("Fatal Error", "In onResume() and unable to close socket during connection failure" + e2.getMessage() + ".");
            }
        }

        // Create a data stream so we can talk to server.
        Log.d(TAG, "...Create Socket...");

        mConnectedThread = new ConnectedThread(btSocket);
        mConnectedThread.start();
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.d(TAG, "...In onPause()...");

        try     {
            btSocket.close();
        } catch (IOException e2) {
            errorExit("Fatal Error", "In onPause() and failed to close socket." + e2.getMessage() + ".");
        }
    }

    private void checkBTState() {
        // Check for Bluetooth support and then check to make sure it is turned on
        // Emulator doesn't support Bluetooth and will return null
        if(btAdapter==null) {
            errorExit("Fatal Error", "Bluetooth not support");
        } else {
            if (btAdapter.isEnabled()) {
                Log.d(TAG, "...Bluetooth ON...");
            } else {
                //Prompt user to turn on Bluetooth
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }

    private void errorExit(String title, String message){
        Toast.makeText(getBaseContext(), title + " - " + message, Toast.LENGTH_LONG).show();
        finish();
    }

    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[256];  // buffer store for the stream
            int bytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);        // Get number of bytes and message in "buffer"
                    h.obtainMessage(RECIEVE_MESSAGE, bytes, -1, buffer).sendToTarget();     // Send to message queue Handler
                } catch (IOException e) {
                    break;
                }
            }
        }

        /* Call this from the main activity to send data to the remote device */
        public void write(String message) {
            Log.d(TAG, "...Data to send: " + message + "...");
            byte[] msgBuffer = message.getBytes();
            try {
                mmOutStream.write(msgBuffer);
            } catch (IOException e) {
                Log.d(TAG, "...Error data send: " + e.getMessage() + "...");
            }
        }
    }

}
