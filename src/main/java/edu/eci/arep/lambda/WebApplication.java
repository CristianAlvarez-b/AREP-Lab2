package edu.eci.arep.lambda;

import java.io.IOException;
import java.net.URISyntaxException;

import static edu.eci.arep.lambda.HttpServer.staticfiles;
import static edu.eci.arep.lambda.HttpServer.get;

public class WebApplication {
    public static void main(String[] args) throws IOException, URISyntaxException {
        staticfiles("webroot/public");
        get("/app/hello", (req, res) -> {
            String name = req.getValues("name");
            return (name != null) ? "Hello " + name + "!" : "hello world!";
        });
        get("/app/pi", (req, res) -> String.valueOf(Math.PI));
        get("/app/e", (req, res) -> String.valueOf(Math.E));
        HttpServer.start(args);
    }
}
