package restlet;

import org.restlet.Restlet;
import org.restlet.data.Status;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.restlet.routing.Router;
import restlet.annuaire.Etudiant;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.restlet.Application;

import javax.xml.bind.annotation.XmlRootElement;

public class Annuaire extends Application {
    public Map<String, Etudiant> annuaire = new ConcurrentHashMap<>();

    public Annuaire() {
        annuaire.put("45652", new Etudiant("45652", "Macron", "Emmanuel", "0956874123"));
        annuaire.put("56423", new Etudiant("56423", "Seize", "Louis", "0864287951"));
        annuaire.put("78951", new Etudiant("78951", "Chirac", "Jacques", "0864287951"));
        annuaire.put("89546", new Etudiant("89546", "Sarkozy", "Nicolas", "0978654123"));
    }

    @Override
    public Restlet createInboundRoot() {
        Router res = new Router();
        res.attach("/etudiants", All.class);
        res.attach("/etudiants/{id}", EtudiantResource.class);
        return res;
    }

    @XmlRootElement
    public static class All extends ServerResource {
        @Get("xml|json")
        public Map<String, Etudiant> request() {
            Application app = this.getApplication();

            if (! (app instanceof Annuaire)) {
                throw new ResourceException(Status.SERVER_ERROR_INTERNAL);
            }

            Annuaire a = (Annuaire) getApplication();
            return a.annuaire;
        }

        @Post("json")
        public void ajouter(Representation r) throws IOException {
            JacksonRepresentation<Etudiant> jr = new JacksonRepresentation<>(r, Etudiant.class);
            Etudiant e = jr.getObject();
            Application app = this.getApplication();
            if(! (app instanceof Annuaire)) {
                throw new ResourceException(Status.SERVER_ERROR_INTERNAL);
            }
            Annuaire a = (Annuaire) getApplication();
            Object id = getRequest().getAttributes().get("id");
            if(!id.equals(e.getId())) {
                throw new ResourceException(Status.CLIENT_ERROR_CONFLICT);
            }
            a.annuaire.put(id.toString(), e);
        }
    }
}
