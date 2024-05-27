package app;

import java.util.HashMap;
import java.util.Map;

import app.handlers.EchoHandler;
import app.handlers.NotFoundHandler;
import app.handlers.RequestHandler;
import app.handlers.RootHandler;
import http.Request;


public class Router {

    private Map<String, RequestHandler> handlers = new HashMap<>();

    public Router() {
        handlers.put("GET /", new RootHandler());
        handlers.put("GET /echo/{}", new EchoHandler());
    }


    public RequestHandler getHandler(Request request) {
        String routeKey = request.method + " " + request.path;
        RequestHandler handler = this.handlers.get(routeKey);
        if (handler != null)
            return handler;
        for (String handlerKey : this.handlers.keySet()) {
            if (handlerKey.endsWith("{}") && routeKey.startsWith(handlerKey.substring(0, handlerKey.length() - 4))) {
                return this.handlers.get(handlerKey);
            }
        }
        return new NotFoundHandler();
    }
}
