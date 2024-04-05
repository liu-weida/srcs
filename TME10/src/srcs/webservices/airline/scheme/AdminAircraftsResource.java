package srcs.webservices.airline.scheme;

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.data.MediaType;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdminAircraftsResource extends ServerResource {

    private static List<Aircraft> aircrafts = new ArrayList<>();

    @Post("json")
    public Representation addAircraft(JsonRepresentation aircraftJson) {
        try {
            JSONObject json = aircraftJson.getJsonObject();
            String model = json.getString("model");
            int passengerCapacity = json.getInt("passengerCapacity");

            Aircraft aircraft = Aircraft.buildAircraft(model, passengerCapacity);
            aircrafts.add(aircraft);

            return new StringRepresentation("Aircraft added successfully", MediaType.TEXT_PLAIN);
        } catch (Exception e) {
            return new StringRepresentation("Error in adding aircraft", MediaType.TEXT_PLAIN);
        }
    }

    @Get("json")
    public Representation getAircrafts() {
        try {
            JSONArray jsonArray = new JSONArray();
            for (Aircraft aircraft : aircrafts) {
                JSONObject json = new JSONObject();
                json.put("registration", aircraft.getRegistration());
                json.put("model", aircraft.getModel());
                json.put("passengerCapacity", aircraft.getPassengerCapacity());
                jsonArray.put(json);
            }
            return new JsonRepresentation(jsonArray);
        } catch (Exception e) {
            return new StringRepresentation("Error in retrieving aircrafts", MediaType.TEXT_PLAIN);
        }
    }

    public static List<Aircraft> getAircraftsList() {
        return aircrafts;
    }
}
