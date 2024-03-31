package restlet;

import org.restlet.Application;
import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import restlet.annuaire.Etudiant;

public class EtudiantResource extends ServerResource {
    @Get("xml|json")
    public Etudiant request() {
        Application app = this.getApplication();
        if(! (app instanceof Annuaire)) {
            throw new ResourceException(Status.SERVER_ERROR_INTERNAL);
        }
        Annuaire a = (Annuaire) getApplication();
        Object id = getRequest().getAttributes().get("id");
        if(!a.annuaire.containsKey(id)) {
            throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
        }
        return a.annuaire.get(id);
    }
}
