package com.flink.flumeSocket;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.Random;

public class SendEMMSFlume {

    public static void main(String[] args) {
        try {
//模拟发送数据到9998端口，然后用集群flume读取！
            Socket s = new Socket();
            s.connect(new InetSocketAddress("10.10.41.251", 9993), 2000);
            new Thread(() -> {
                try {
                    OutputStream out = s.getOutputStream();
                    short[] short1 = new short[1000];
                    for (int ii = 0; ii < 1000; ii++) {
                        short1[ii] = nextSort();
                    }
                    byte[] short2Bytes = short2Bytes(short1);
                    while (true) {
                        long currentTimeMillis = System.currentTimeMillis();
                        //发送迹线数据
                        String line = "trace|" + currentTimeMillis + "|169|100|300|0.015750|97|0|-1|" + 1000
                                + "|0|NoBack|0|2020-04-14-trace|e67ff4026e2c5374|short|[";
//						String line = "EMMS|00|SingalList|1631170279475|2|0|49|2021-09-09-SingalList
//						|sssaa[1631170279475,1631169999533,88.846875, 0.433125, 20.90, 11.0, 0, 0
//						#1631170279475,1631169999533,89.9925, 0.210000, 27.50, 11.0, 0, 0
//						#1631170279475,1631169999533,90.49875, 0.172500, 19.60, 11.0, 0, 0
//						#1631170279475,1631170037924,91.4775, 0.255000, 19.50, 11.0, 0, 0
//						#1631170279475,1631169999533,94.48875, 0.172500, 25.70, 11.0, 0, 0
//						#1631170279475,1631169999533,96.61875, 0.172500, 27.40, 11.0, 0, 0
//						#1631170279475,1631169999533,97.41, 0.240000, 30.90, 11.0, 0, 0
//						#1631170279475,1631170037924,99.68625, 0.307500, 28.60, 11.0, 0, 0
//						#1631170279475,1631170049905,101.79375, 0.217500, 21.00, 11.0, 0, 0
//						#1631170279475,1631170042667,103.8975, 0.225000, 23.90, 11.0, 0, 0
//						#1631170279475,1631170053259,106.155, 0.300000, 22.60, 11.0, 0, 0
//						#1631170279475,1631170070169,470.062433, 0.120000, 30.20, 14.0, 0, 0
//						#1631170279475,1631170039515,480.0674, 1.034997, 31.50, 14.0, 0, 0
//						#1631170279475,1631170070169,490.064866, 0.105000, 28.30, 14.0, 0, 0
//						#1631170279475,1631170037924,500.272332, 1.529995, 30.10, 13.0, 0, 0
//						#1631170279475,1631169999533,504.06732, 1.064996, 23.90, 13.0, 0, 0
//						#1631170279475,1631170037924,510.0673, 0.517498, 27.30, 13.0, 0, 0
//						#1631170279475,1631170037924,519.36125, 0.787500, 27.10, 13.0, 0, 0
//						#1631170279475,1631169999533,528.07474, 0.509998, 24.70, 14.0, 0, 0
//						#1631170279475,1631170073321,530.069733, 0.097500, 25.50, 14.0, 0, 0
//						#1631170279475,1631169999533,545.99375, 8.182500, 30.90, 15.0, 0, 0
//						#1631170279475,1631170037924,560.219633, 1.454995, 30.10, 13.0, 0, 0
//						#1631170279475,1631170041793,564.07462, 0.517498, 19.60, 13.0, 0, 0
//						#1631170279475,1631170040576,610.079466, 0.149999, 21.20, 14.0, 0, 0
//						#1631170279475,1631170037924,614.399452, 0.142500, 23.60, 14.0, 0, 0
//						#1631170279475,1631170044195,620.084433, 0.187499, 26.10, 14.0, 0, 0
//						#1631170279475,1631170040576,626.00375, 7.942500, 26.60, 14.0, 0, 0
//						#1631170279475,1631170040576,648.08934, 0.532498, 27.20, 13.0, 0, 0
//						#1631170279475,1631170040576,660.0893, 0.149999, 21.00, 14.0, 0, 0
//						#1631170279475,1631169999533,672.08926, 0.239999, 23.70, 13.0, 0, 0
//						#1631170279475,1631170040576,680.174233, 0.239999, 21.60, 14.0, 0, 0
//						#1631170279475,1631170037924,696.09668, 0.164999, 22.60, 14.0, 0, 0
//						#1631170279475,1631170042667,1739.499219, 0.728437, 55.40, 321.0, 0, 0
//						#1631170279475,1631169999533,1746.179844, 0.362813, 22.30, 321.0, 0, 0
//						#1631170279475,1631169999533,1845.631055, 69.162977, 22.10, 313.0, 0, 0
//						#1631170279475,1631169999533,2323.842504, 7.946224, 41.20, 312.0, 0, 0
//						#1631170279475,1631170042667,2402.824741, 1.826243, 23.30, 40.0, 0, 0
//						#1631170279475,1631170040576,2412.267209, 10.541215, 16.00, 41.0, 0, 0
//						#1631170279475,1631170066956,2420.914681, 6.416228, 27.10, 41.0, 0, 0
//						#1631170279475,1631170048314,2430.660897, 1.927493, 35.40, 40.0, 0, 0
//						#1631170279475,1631170049905,2436.323379, 7.784974, 21.40, 41.0, 0, 0
//						#1631170279475,1631169999533,2441.98586, 5.219983, 15.60, 41.0, 0, 0
//						#1631170279475,1631170039515,2457.623308, 4.747484, 32.40, 41.0, 0, 0
//						#1631170279475,1631170063243,2463.233289, 17.782441, 15.90, 38.0, 0, 0
//						#1631170279475,1631170070169,2469.953267, 12.569958, 20.40, 38.0, 0, 0
//						#1631170279475,1631170041793,2474.97825, 0.787497, 26.50, 40.0, 0, 0
//						#1631170279475,1631170054788,2338.246206, 2.654991, 35.60, 312.0, 0, 0
//						#1631170279475,1631170048751,5807.482601, 13.874967, 24.30, 39.0, 0, 0
//						#1631170279475,1631170070169,2479.980733, 1.079996, 25.60, 40.0, 0, 0";
                        System.out.println(line.getBytes().length + short2Bytes.length + 16);
                        out.write(line.getBytes());
                        out.write(short2Bytes);
                        out.write((new byte[]{(byte) 255, (byte) 255, (byte) 255, (byte) 252, (byte) 255, (byte) 255,
                                (byte) 255, (byte) 252, (byte) 255, (byte) 255, (byte) 255, (byte) 252, (byte) 255,
                                (byte) 255, (byte) 255, (byte) 252}));
                        out.flush();
                        System.out.println(line.getBytes().length + short2Bytes.length + 16);
                        Thread.sleep(5000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
            new Thread(() -> {
                try {
                    InputStream in = s.getInputStream();
                    while (true) {
                        if (in.available() <= 0) {
                            continue;
                        }
                        byte[] bytes = new byte[1024];
                        int len = 0;
                        while ((len = in.read(bytes)) > 0) {
                            System.out.println("客户端：" + new String(bytes));
                        }
                        Thread.sleep(2000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 将字节数组前2字节转换为short整型数值
     *
     * @param bytes
     * @return
     */
    public static short getShort(byte[] bytes) {
        return (short) ((0xff00 & (bytes[0] << 8)) | (0xff & bytes[1]));
    }

    /**
     * 将short整型数值转换为字节数组
     *
     * @param data
     * @return
     */
    public static byte[] getBytes(short data) {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) ((data & 0xff00) >> 8);
        bytes[1] = (byte) (data & 0xff);
        return bytes;
    }

    public static byte[] short2Bytes(short[] data) {
        byte[] byteRet = new byte[2 * data.length];

        for (int i = 0; i < data.length; i++) {
            byteRet[2 * i] = (byte) ((data[i] & 0xff00) >> 8);
            byteRet[2 * i + 1] = (byte) (data[i] & 0xff);
        }
        return byteRet;
    }

    public static byte[] double2Bytes(double[] d) {
        byte[] byteRet = new byte[8 * d.length];
        for (int j = 0; j < d.length; j++) {
            long value = Double.doubleToRawLongBits(d[j]);
            for (int i = 0; i < 8; i++) {
                byteRet[8 * j + i] = (byte) ((value >> 8 * i) & 0xff);
            }
        }
        return byteRet;
    }


    public static Short nextSort() {
        double result = ((100) * new Random().nextDouble());
        DecimalFormat df = new DecimalFormat("#");
        return Short.valueOf(df.format(result));
    }
}
