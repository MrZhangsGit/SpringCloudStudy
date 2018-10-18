package Socket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPDemo {

}

class UDPSend {
    public static void main(String[] args) throws Exception {
        //创建udp服务，通过DatagramSocket对象
        DatagramSocket datagramSocket = new DatagramSocket(7891);

        //确定数据并封装成数据包
        byte[] buf = "udp data is coming".getBytes();

        DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length, InetAddress.getByName("127.0.0.1"), 10000);

        //通过socket服务用send方法将已有数据包发出
        datagramSocket.send(datagramPacket);

        datagramSocket.close();
    }
}

class UDPReceive {
    public static void main(String[] args) throws Exception {
        //创建UDPSocket服务，建立端点
        DatagramSocket datagramSocket = new DatagramSocket(10000);

        while (true) {
            byte[] buf = new byte[1024];
            DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length);

            datagramSocket.receive(datagramPacket);

            String ip = datagramPacket.getAddress().getHostAddress();
            String data = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
            int port = datagramPacket.getPort();

            datagramSocket.close();
        }
    }
}