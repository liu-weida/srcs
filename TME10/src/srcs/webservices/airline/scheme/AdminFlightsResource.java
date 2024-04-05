package srcs.webservices.airline.scheme;

import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.data.MediaType;
import org.restlet.ext.json.JsonRepresentation;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdminFlightsResource extends ServerResource {
    private static List<Flight> flights = new ArrayList<>();

    @Post("json")
    public Representation addFlight(Representation entity) {
        try {
            JsonRepresentation represent = new JsonRepresentation(entity);
            JSONObject json = represent.getJsonObject();
            String id = json.getString("id");

            JSONObject fromJson = json.getJSONObject("from");
            Airport from = new Airport(
                    fromJson.getString("codeAITA"),
                    fromJson.getString("name"),
                    fromJson.getString("city"),
                    fromJson.getString("location"),
                    fromJson.getString("country"));

            JSONObject toJson = json.getJSONObject("to");
            Airport to = new Airport(
                    toJson.getString("codeAITA"),
                    toJson.getString("name"),
                    toJson.getString("city"),
                    toJson.getString("location"),
                    toJson.getString("country"));

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date departure = sdf.parse(json.getString("departure"));
            Date arrival = sdf.parse(json.getString("arrival"));

            JSONObject aircraftJson = json.getJSONObject("aircraft");
            Aircraft aircraft = new Aircraft(
                    aircraftJson.getString("registration"),
                    aircraftJson.getString("model"),
                    aircraftJson.getInt("passengerCapacity"));

            Flight flight = new Flight(id, from, to, departure, arrival, aircraft);
            flights.add(flight);

            return new StringRepresentation("Flight added successfully", MediaType.TEXT_PLAIN);
        } catch (Exception e) {
            return new StringRepresentation("Error in adding flight: " + e.getMessage(), MediaType.TEXT_PLAIN);
        }
    }

    public static List<Flight> getFlights() {
        return flights;
    }
}
