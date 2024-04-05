package srcs.webservices.airline.scheme;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.data.MediaType;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class AirportsResource extends ServerResource {

    @Get("json")
    public Representation getAirports() {
        try {
            JSONArray jsonArray = new JSONArray();
            List<Airport> airportsList = AdminAirportsResource.getAirportsList();

            for (Airport airport : airportsList) {
                JSONObject json = new JSONObject();
                json.put("codeAITA", airport.getCodeAITA());
                json.put("name", airport.getName());
                json.put("city", airport.getCity());
                json.put("location", airport.getLocation());
                json.put("country", airport.getCountry());
                jsonArray.put(json);
            }
            return new JsonRepresentation(jsonArray);
        } catch (Exception e) {
            // Error handling with proper Restlet types
            return new StringRepresentation("Error in retrieving airports", MediaType.TEXT_PLAIN);
        }
    }
}
