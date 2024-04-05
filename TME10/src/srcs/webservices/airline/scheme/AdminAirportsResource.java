package srcs.webservices.airline.scheme;

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.data.MediaType;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class AdminAirportsResource extends ServerResource {

    private static List<Airport> airports = new ArrayList<>();

    @Post("json")
    public Representation addAirport(JsonRepresentation airportJson) {
        try {
            JSONObject json = airportJson.getJsonObject();
            String codeAITA = json.getString("codeAITA");
            String name = json.getString("name");
            String city = json.getString("city");
            String location = json.getString("location");
            String country = json.getString("country");

            Airport airport = new Airport(codeAITA, name, city, location, country);
            airports.add(airport);

            return new StringRepresentation("Airport added successfully", MediaType.TEXT_PLAIN);
        } catch (Exception e) {
            return new StringRepresentation("Error in adding airport: " + e.getMessage(), MediaType.TEXT_PLAIN);
        }
    }

    @Get("json")
    public Representation getAirports() {
        try {
            JSONArray jsonArray = new JSONArray();
            for (Airport airport : airports) {
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
            return new StringRepresentation("Error in retrieving airports: " + e.getMessage(), MediaType.TEXT_PLAIN);
        }
    }

    public static List<Airport> getAirportsList() {
        return airports;
    }
}
