package srcs.service.calculatrice;

import java.lang.reflect.InvocationTargetException;

public interface Calculatrice {

    public int add(int val1 , int val2);
    public int sous(int val1 , int val2);

    public int mult (int val1 , int val2);

    public Object div (int val1 , int val2) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;


    public static class ResDiv{

        private int quotient;
        private int reste;

        public ResDiv(int quotient,int reste){
            this.quotient = quotient;
            this.reste = reste;

        }
        public int getQuotient(){
            return quotient;
        }
        public int getReste(){
            return reste;
        }

    }
}

