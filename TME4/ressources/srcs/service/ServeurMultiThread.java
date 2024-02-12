package srcs.service;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;

// TME4 Exercice1 Question3
public class ServeurMultiThread {
    private final int port;
    private final Class<?> serviceType;
    private Service service = null;

    public ServeurMultiThread(int port, Class<?> serviceType){
        this.port = port;
        this.serviceType = serviceType;
    }

    public void listen() {
        try (ServerSocket ss = new ServerSocket(port)){
            while (true){
                try(Socket s = ss.accept()) {
                    Thread thread = new Thread(new MyRunnable(getService(), s));
                    thread.start();
                }
            }
        } catch (IOException | NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private Service getService() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Annotation[] annotations = serviceType.getAnnotations();
        if (annotations.length == 0)
            throw new IllegalStateException();
        for (Annotation annotation: annotations){
            if (annotation instanceof SansEtat || (annotation instanceof EtatGlobal && service == null))
                service = serviceType.asSubclass(Service.class).getDeclaredConstructor().newInstance();
        }
        return service;
    }
}
