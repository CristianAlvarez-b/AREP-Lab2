package edu.eci.arep.lambda;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpServerTest {

    @Test
    public void testGetMimeTypeHtml() {
        String mimeType = HttpServer.getMimeType("/index.html");
        assertEquals("text/html", mimeType);
    }

    @Test
    public void testGetMimeTypeCss() {
        String mimeType = HttpServer.getMimeType("/style.css");
        assertEquals("text/css", mimeType);
    }
    @Test
    public void testGetMimeTypeJs() {
        assertEquals("application/javascript", HttpServer.getMimeType("/script.js"));
    }


    @Test
    public void testGetMimeTypeUnknown() {
        assertEquals("application/octet-stream", HttpServer.getMimeType("/file.unknown"));
    }
    @Test
    public void testStaticfiles() {
        HttpServer.staticfiles("webroot/public");

        assertEquals("target/classes/webroot/public", HttpServer.staticFilesLocation);
    }

    @Test
    public void testGetRouteRegistration() {
        HttpServer.get("/hello", (req, res) -> "Hello " + req.getValues("name"));

        Request mockRequest = mock(Request.class);
        Response mockResponse = mock(Response.class);

        Map<String, String> mockParams = new HashMap<>();
        mockParams.put("name", "John");
        when(mockRequest.getValues("name")).thenReturn("John");

        assertTrue(HttpServer.routes.containsKey("/hello"));
        assertEquals("Hello John", HttpServer.routes.get("/hello").apply(mockRequest, mockResponse));
    }
    @Test
    public void testRouteNotFound() {
        Request mockRequest = mock(Request.class);
        Socket mockSocket = mock(Socket.class);
        when(mockRequest.getPath()).thenReturn("/nonexistent");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintWriter out = new PrintWriter(outputStream, true);

        HttpServer.handleStaticFileRequest("/nonexistent", out, mockSocket);

        String response = outputStream.toString();
        System.out.println(response);
        assertTrue(response.contains("HTTP/1.1 404 Not Found"));
        assertTrue(response.contains("<html><body><h1>404 Not Found</h1></body></html>"));
    }
}
