package srcs.service.annuaire;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import srcs.service.Service;

public class AnnuaireService implements Annuaire, Service{

    Map<String,String> map = new HashMap<>();

    public AnnuaireService(){
        map.put("","");

    }

    @Override
    public String lookup(String name) {

        String val = map.get(name);

        if (map.get(name) == null){
            val = "";
        }

        return val;
    }

    @Override
    public void bind(String name , String val) {

        map.put(name,val);


    }

    @Override
    public void unbind(String name) {
        map.remove(name);
    }

    @Override
    public void execute(Socket connexion) {

    }
}
