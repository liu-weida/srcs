package srcs.service.annuaire;

import java.util.HashMap;
import java.util.Map;

public class AnnuaireService implements Annuaire{

    Map<String,String> map = new HashMap<>();

    public AnnuaireService(){
        map.put("","");

    }

    @Override
    public String lookup(String name) {
        return null;
    }

    @Override
    public String bind(String val) {
        return null;
    }

    @Override
    public String unbind(String name) {
        return null;
    }
}
