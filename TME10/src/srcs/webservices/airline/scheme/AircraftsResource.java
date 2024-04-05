package srcs.webservices.airline.scheme;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.representation.Representation;
import org.restlet.ext.json.JsonRepresentation;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class AircraftsResource extends ServerResource {

    @Get("json")
    public Representation getAircrafts() {
        JSONArray jsonArray = new JSONArray();
        List<Aircraft> aircraftsList = AdminAircraftsResource.getAircraftsList();

        for (Aircraft aircraft : aircraftsList) {
            JSONObject json = new JSONObject();
            json.put("registration", aircraft.getRegistration());
            json.put("model", aircraft.getModel());
            json.put("passengerCapacity", aircraft.getPassengerCapacity());
            jsonArray.put(json);
        }
        return new JsonRepresentation(jsonArray);

    }


}

