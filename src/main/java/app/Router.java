package app;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import app.handlers.EchoHandler;
import app.handlers.GetFileHandler;
import app.handlers.NotFoundHandler;
import app.handlers.RequestHandler;
import app.handlers.RootHandler;
import app.handlers.UserAgentHandler;
import http.Request;


public class Router {

    private Map<String, Class<? extends RequestHandler>> handlers = new HashMap<>();
    FileService fileService;

    public Router(FileService fileService) {
        this.fileService = fileService;
        handlers.put("GET /", RootHandler.class);
        handlers.put("GET /echo/{}", EchoHandler.class);
        handlers.put("GET /user-agent", UserAgentHandler.class);
        handlers.put("GET /files/{}", GetFileHandler.class);
    }


    public RequestHandler getHandler(Request request) throws 
              InstantiationException,
              IllegalAccessException,
              IllegalArgumentException,
              InvocationTargetException,
              SecurityException, 
              NoSuchMethodException {
        String routeKey = request.method + " " + request.path;
        Class<? extends RequestHandler> handler = this.handlers.get(routeKey);
        if (handler != null)
            return handler.getDeclaredConstructor(FileService.class).newInstance(this.fileService);
        for (String handlerKey : this.handlers.keySet()) {
            if (handlerKey.endsWith("{}") && routeKey.startsWith(handlerKey.substring(0, handlerKey.length() - 4))) {
                handler = this.handlers.get(handlerKey);
                return handler.getDeclaredConstructor(FileService.class).newInstance(this.fileService);
            }
        }
        return new NotFoundHandler(this.fileService);
    }
}
