package edu.eci.arep.lambda;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import static org.junit.jupiter.api.Assertions.*;

public class ResponseTest {

    @Test
    public void testSendResponse() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintWriter mockPrintWriter = new PrintWriter(byteArrayOutputStream, true);

        Socket mockSocket = mock(Socket.class);

        Response response = new Response(mockPrintWriter, mockSocket);

        String responseMessage = "<html><body><h1>Hello, World!</h1></body></html>";
        response.send(responseMessage);

        String expectedResponse = "HTTP/1.1 200 OK\r\n" +
                "Content-Type: text/html; charset=UTF-8\r\n" +
                "Content-Length: " + responseMessage.getBytes().length + "\r\n" +
                "Connection: close\r\n" +
                "\r\n" + responseMessage;

        String actualResponse = byteArrayOutputStream.toString();

        assertTrue(actualResponse.contains("HTTP/1.1 200 OK"));
        assertTrue(actualResponse.contains("Content-Type: text/html; charset=UTF-8"));
        assertTrue(actualResponse.contains("Content-Length: " + responseMessage.getBytes().length));
        assertTrue(actualResponse.contains(responseMessage));
    }
    @Test
    public void testSendFile() {
        PrintWriter mockPrintWriter = mock(PrintWriter.class);

        Socket mockSocket = mock(Socket.class);

        HttpServer mockHttpServer = mock(HttpServer.class);

        Response response = new Response(mockPrintWriter, mockSocket);

        String filePath = "/path/to/file.html";
        response.sendFile(filePath);

        verify(mockHttpServer).handleStaticFileRequest(filePath, mockPrintWriter, mockSocket);
    }

}
