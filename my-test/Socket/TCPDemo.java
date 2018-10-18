package Socket;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPDemo {
}

class TcpClient//客户端
{
    public static void main(String[] args) throws Exception
    {
        //创建客户端的Socket服务，指定目的的主机和端口
        Socket s = new Socket("127.0.0.1",10003);
        //若此步成功，即通路建立了。通路一建立就会有Socket流(即网络流)。
        //此Socket流中有方法可以拿到输入流或输出流，不需要New。

        //为了发送数据需要获取Socket流中的输出流
        //因为数据可能是任何类型，所以用字节流
        OutputStream out = s.getOutputStream();

        out.write("Tcp is Coming".getBytes());

        s.close();//Socket关闭，流也会被关闭
    }
}

class TcpServer
{
    public static void main(String[] args) throws Exception
    {
        //建立服务端Socket服务，并监听一个端口。
        ServerSocket ss = new ServerSocket(10003);

        //通过accept方法获取连接过来的客户端对象。
        Socket s = ss.accept();

        String ip = s.getInetAddress().getHostAddress();
        System.out.println(ip+"...connected");

        //获取客户端发送过来的数据，那么要使用客户端对象的读取流来读取数据。
        InputStream in = s.getInputStream();

        byte[] buf = new byte[1024];
        int len = in.read(buf);

        System.out.println(new String(buf,0,len));

        s.close();//关闭客户端

    }
}