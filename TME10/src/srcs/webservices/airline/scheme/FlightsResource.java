package srcs.webservices.airline.scheme;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.representation.Representation;
import org.restlet.ext.json.JsonRepresentation;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FlightsResource extends ServerResource {

    @Get("json")
    public Representation getFlights() {
        JSONArray jsonArray = new JSONArray();
        for (Flight flight : AdminFlightsResource.getFlights()) { 
            JSONObject json = new JSONObject();
            json.put("id", flight.getId());
            jsonArray.put(json);
        }
        return new JsonRepresentation(jsonArray);
    }


}
