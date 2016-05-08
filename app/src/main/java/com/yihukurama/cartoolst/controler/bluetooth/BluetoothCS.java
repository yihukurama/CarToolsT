package com.yihukurama.cartoolst.controler.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by dengshuai on 16/4/12.
 */
public class BluetoothCS {
    public static final String PROTOCOL_SCHEME_RFCOMM = "btspp";
    private Handler LinkDetectedHandler;
    private BluetoothServerSocket mserverSocket = null;
    private ServerThread startServerThread = null;
    private clientThread clientConnectThread = null;
    private BluetoothSocket socket = null;
    private BluetoothDevice device = null;
    private readThread mreadThread = null;;
    private String address = "null";
    private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    //client address  C0:EE:FB:46:90:33
    //server address  1C:87:2C:9D:2A:38
    public BluetoothCS(String address, Handler linkDetectedHandler) {
        this.address = address;
        LinkDetectedHandler = linkDetectedHandler;
    }


    //发送数据
    public void sendMessageHandle(String msg)
    {
        if (socket == null)
        {
            Log.i("bluetoothC","无连接");
            return;
        }
        try {
            OutputStream os = socket.getOutputStream();
            os.write(msg.getBytes());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    //开启服务端线程
    public void startServer(){
        startServerThread = new ServerThread();
        startServerThread.start();
    }

    //开启客户端线程
    public void startClient() {
        if (!address.equals("null")) {
            device = mBluetoothAdapter.getRemoteDevice(address);
            clientConnectThread = new clientThread();
            clientConnectThread.start();
        }
    }


    //读取数据
    private class readThread extends Thread {
        public void run() {

            byte[] buffer = new byte[1024];
            int bytes;
            InputStream mmInStream = null;

            try {
                mmInStream = socket.getInputStream();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            while (true) {
                try {
                    // Read from the InputStream
                    if( (bytes = mmInStream.read(buffer)) > 0 )
                    {
                        byte[] buf_data = new byte[bytes];
                        for(int i=0; i<bytes; i++)
                        {
                            buf_data[i] = buffer[i];
                        }
                        String s = new String(buf_data);
                        Message msg = new Message();
                        msg.obj = s;
                        msg.what = 1;
                        LinkDetectedHandler.sendMessage(msg);
                    }
                } catch (IOException e) {
                    try {
                        mmInStream.close();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    break;
                }
            }
        }
    }

    //开启客户端
    private class clientThread extends Thread {
        public void run() {
            try {
                //创建一个Socket连接：只需要服务器在注册时的UUID号
                // socket = device.createRfcommSocketToServiceRecord(BluetoothProtocols.OBEX_OBJECT_PUSH_PROTOCOL_UUID);
                socket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                //连接
                Message msg2 = new Message();
                msg2.obj = "正在连接平板:"+address;
                LinkDetectedHandler.sendMessage(msg2);

                socket.connect();

                Message msg = new Message();
                msg.obj = "已连接。";
                msg.what = 0;
                LinkDetectedHandler.sendMessage(msg);
                //启动接受数据
                mreadThread = new readThread();
                mreadThread.start();
            }
            catch (IOException e)
            {
                Log.e("connect", "", e);
                Message msg = new Message();
                msg.obj = "连接异常！点击平板重连。";
                msg.what = 0;
                LinkDetectedHandler.sendMessage(msg);
            }
        }
    };

    //开启服务器
    private class ServerThread extends Thread {
        public void run() {

            try {
				/* 创建一个蓝牙服务器
				 * 参数分别：服务器名称、UUID	 */
                mserverSocket = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(PROTOCOL_SCHEME_RFCOMM,
                        UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));

                Log.d("server", "wait cilent connect...");

                Message msg = new Message();
                msg.obj = "等待手机连接";
                msg.what = 0;
                LinkDetectedHandler.sendMessage(msg);

                while (mBluetoothAdapter.getState() != mBluetoothAdapter.STATE_CONNECTED) {
                    /* 接受客户端的连接请求 */
                    socket = mserverSocket.accept();
                    Log.d("server", "accept success !");

                    Message msg2 = new Message();
                    String info = "已连接，点击可断开重连。";
                    msg2.obj = info;
                    msg.what = 0;
                    LinkDetectedHandler.sendMessage(msg2);
                    //启动接受数据
                    mreadThread = new readThread();
                    mreadThread.start();
                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    };

    /* 停止服务器 */
    public void shutdownServer() {
        new Thread() {
            public void run() {
                if(startServerThread != null)
                {
                    startServerThread.interrupt();
                    startServerThread = null;
                }
                if(mreadThread != null)
                {
                    mreadThread.interrupt();
                    mreadThread = null;
                }
                try {
                    if(socket != null)
                    {
                        socket.close();
                        socket = null;
                    }
                    if (mserverSocket != null)
                    {
                        mserverSocket.close();/* 关闭服务器 */
                        mserverSocket = null;
                    }
                } catch (IOException e) {
                    Log.e("server", "mserverSocket.close()", e);
                }
            };
        }.start();
    }
    /* 停止客户端连接 */
    public void shutdownClient() {
        new Thread() {
            public void run() {
                if(clientConnectThread!=null)
                {
                    clientConnectThread.interrupt();
                    clientConnectThread= null;
                }
                if(mreadThread != null)
                {
                    mreadThread.interrupt();
                    mreadThread = null;
                }
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    socket = null;
                }
            };
        }.start();
    }
}
