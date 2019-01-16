package HTTP;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SingleFileHTTPServer extends Thread {
    private byte[] content;
    private byte[] header;
    private int port = 80;

    private SingleFileHTTPServer(String data, String encoding, String MIMEType, int port)
            throws UnsupportedEncodingException {
        this(data.getBytes(encoding), encoding, MIMEType, port);
    }

    public SingleFileHTTPServer(byte[] data, String encoding, String MIMEType, int port)
            throws UnsupportedEncodingException {
        this.content = data;
        this.port = port;
        String header="HTTP/1.0 200 OK\r\n"+
               "Server: OneFile 1.0\r\n"+
               "Content-length: "+this.content.length+"\r\n"+
               "Content-type: "+MIMEType+"\r\n\r\n";
        this.header = header.getBytes("ASCII");
    }

    @Override
    public void run() {
        try {
            ServerSocket server = new ServerSocket(this.port);
            System.out.println("Accepting connections on port "+server.getLocalPort());
            System.out.println("Data to be sent:");
            System.out.write(this.content);

            while (true) {
                Socket connection = null;
                try {
                    connection = server.accept();
                    OutputStream outputStream = new BufferedOutputStream(connection.getOutputStream());
                    InputStream inputStream = new BufferedInputStream(connection.getInputStream());

                    StringBuffer request = new StringBuffer();
                    while (true) {
                        int c = inputStream.read();
                        if (c == '\r' || c == '\n' || c == -1) {
                            break;
                        }
                        request.append((char) c);
                    }

                    //如果检测到是HTTP/1.0及以后的协议，按照规范，需要发送一个MIME首部
                    if (request.toString().indexOf("HTTP/") != -1) {
                        outputStream.write(this.header);
                    }
                    outputStream.write(this.content);
                    outputStream.flush();
                } catch (IOException e) {
                    System.out.println(e);
                } finally {
                    if (connection != null) {
                        connection.close();
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Could not start server. Port Occupied");
        }
    }

    public static void main(String[] args) {
        try {
            String contentType = "text/plain";
            if (args[0].endsWith(".html") || args[0].endsWith(".htm")) {
                contentType = "text/html";
            }

            InputStream inputStream = new FileInputStream(args[0]);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            int b;
            while ((b = inputStream.read()) != -1) {
                outputStream.write(b);
            }
            byte[] data = outputStream.toByteArray();

            //设置监听端口
            int port;
            try {
                port = Integer.parseInt(args[1]);
                if (port < 1 || port > 65535) {
                    port = 80;
                }
            } catch (Exception e) {
                port = 80;
            }

            String encoding = "ASCII";
            if (args.length>2) {
                encoding=args[2];
            }

            Thread t=new SingleFileHTTPServer(data, encoding, contentType, port);
            t.start();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
