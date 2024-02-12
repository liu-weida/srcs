package srcs.service.annuaire;

public interface Annuaire {

    public String lookup(String name);

    public String bind(String val);

    public String unbind(String name);

}
