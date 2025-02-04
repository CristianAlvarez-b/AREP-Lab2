package edu.eci.arep.lambda;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

public class Request {
    private String path;
    private String method;
    private BufferedReader in;
    private Map<String, String> queryParams;

    public Request(String path, String method, BufferedReader in) {
        this.path = path;
        this.method = method;
        this.in = in;
        this.queryParams = new HashMap<>();
        extractQueryParams();
    }

    private void extractQueryParams() {
        int queryIndex = path.indexOf("?");
        if (queryIndex != -1 && queryIndex < path.length() - 1) {
            String[] params = path.substring(queryIndex + 1).split("&");
            for (String param : params) {
                String[] keyValue = param.split("=");
                if (keyValue.length > 1) {
                    queryParams.put(keyValue[0], keyValue[1]);
                }
            }
        }
    }

    public String getValues(String key) {
        return queryParams.get(key);
    }

    public String getPath() {
        return path;
    }

    public String getMethod() {
        return method;
    }

    public BufferedReader getIn() {
        return in;
    }
}
