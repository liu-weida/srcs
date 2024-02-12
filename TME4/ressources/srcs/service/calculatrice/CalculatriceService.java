package srcs.service.calculatrice;

import srcs.service.Service;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;

public class CalculatriceService implements Calculatrice , Service {
    @Override
    public int add(int val1, int val2) {
        return val1+val2;
    }

    @Override
    public int sous(int val1, int val2) {
        return val1-val2;
    }

    @Override
    public int mult(int val1, int val2) {
        return val1*val2;
    }

    @Override
    public Object div(int val1, int val2) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        int quotient = val1 / val2;
        int reste = val1 % val2;

        Class<?> resDivClass = Class.forName("srcs.service.calculatrice.Calculatrice$ResDiv");

        Constructor<?> constructor = resDivClass.getDeclaredConstructor(int.class, int.class);

        Object resDivInstance = constructor.newInstance(quotient, reste);

        return resDivInstance;
    }

    @Override
    public void execute(Socket connexion) {

    }
}
