package srcs.interpretor;

import travail.Command;

import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class CommandInterpretor {
    private Map<String, Class<? extends Command>> map = new HashMap<String, Class<? extends Command>>();

    public CommandInterpretor(){
        map.put("cat", Cat.class);
        map.put("echo", Echo.class);
    }

    public Object getClassOf(String nomCommande) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        String test = "class ";
        String newNomCommande = test + nomCommande;

        System.out.println("456" + newNomCommande);

        for (Class<? extends Command> commande : map.values()){
            if(newNomCommande.equals(commande.toString())) {


                System.out.println("123" + commande);

                Class<? extends Command> commandeClass = map.get(commande);
                Constructor<? extends Command> constructor = commandeClass.getConstructor();
                return constructor.newInstance();
            }
        }

        System.out.println("123123123");
        return null;
    }



    public void perform(String command, PrintStream out) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        // Partie 1
        StringTokenizer tokenizer = new StringTokenizer(command);
        List<String> words = new ArrayList<>();
        while (tokenizer.hasMoreTokens()) {
            words.add(tokenizer.nextToken());
        }

        // Partie 2
        String commande = words.get(0);
        if ( !map.containsKey(commande) ){
            throw new CommandNotFoundException();
        }

        // Partie 3
        Class<? extends Command> classCommand = map.get(commande);
        String className = classCommand.getName();


        Object obj = getClassOf(className);
        Method m = obj.getClass().getMethod("execute");
        m.invoke(out);
    }


}
