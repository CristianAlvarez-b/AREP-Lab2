package edu.eci.arep.lambda;

import java.io.PrintWriter;
import java.net.Socket;

public class Response {
    private PrintWriter out;
    private Socket clientSocket;

    public Response(PrintWriter out, Socket clientSocket) {
        this.out = out;
        this.clientSocket = clientSocket;
    }

    public void send(String response) {
        String httpResponse = "HTTP/1.1 200 OK\r\n" +
                "Content-Type: text/html; charset=UTF-8\r\n" +
                "Content-Length: " + response.getBytes().length + "\r\n" +
                "Connection: close\r\n" +
                "\r\n" +
                response;
        out.print(httpResponse);
        out.flush();
    }

    public void sendFile(String filePath) {
        HttpServer.handleStaticFileRequest(filePath, out, clientSocket);
    }
}