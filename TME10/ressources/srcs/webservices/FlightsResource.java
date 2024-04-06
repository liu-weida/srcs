package srcs.webservices;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.restlet.Application;
import org.restlet.data.Status;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.*;
import srcs.webservices.airline.scheme.Airport;
import srcs.webservices.airline.scheme.Flight;
import srcs.webservices.airline.scheme.Passenger;

import java.security.cert.TrustAnchor;
import java.util.ArrayList;
import java.util.List;

public class FlightsResource extends ServerResource {
    @Get("json")
    public Flight[] request() {
        String from = getQueryValue("from");
        String to = getQueryValue("to");

        Application app = this.getApplication();
        if (!(app instanceof AirlineApplication))
            throw new ResourceException(Status.SERVER_ERROR_INTERNAL);
        AirlineApplication a = (AirlineApplication) getApplication();

        List<Flight> flights = new ArrayList<>();
        for (Flight flight : a.allflights) {
            if ((from == null || flight.getFrom().getCodeAITA().equals(from)) &&
                    (to == null || flight.getTo().getCodeAITA().equals(to)))
                flights.add(flight);
        }
        return flights.toArray(new Flight[flights.size()]);
    }

    @Put("json")
    @Post("json")
    public void add(Representation r) throws Exception {
        Application app = this.getApplication();
        if(! (app instanceof AirlineApplication))
            throw new ResourceException(Status.SERVER_ERROR_INTERNAL);
        AirlineApplication a = (AirlineApplication) getApplication();

        JacksonRepresentation jr = new JacksonRepresentation(r, Flight.class);
        Flight flight = (Flight) jr.getObject();
        a.allflights.add(flight);
    }

    public static class FlightsPassengersResource extends ServerResource {
        @Get("json")
        public Passenger[] request() {
            Object idVol = getRequest().getAttributes().get("idVol");

            Application app = this.getApplication();
            if (!(app instanceof AirlineApplication))
                throw new ResourceException(Status.SERVER_ERROR_INTERNAL);
            AirlineApplication a = (AirlineApplication) getApplication();

            for (Flight flight: a.allflights) {
                if (flight.getId().equals(idVol))
                    return flight.getPassengers().toArray(new Passenger[flight.getPassengers().size()]);
            }
            return null;
        }
    }

    public static class FlightsPassengerResource extends ServerResource {
        @Post("json")
        public Boolean add(Representation r) throws Exception {
            String place = getQueryValue("place");
            Object idVol = getRequest().getAttributes().get("idVol");

            Application app = this.getApplication();
            if(! (app instanceof AirlineApplication))
                throw new ResourceException(Status.SERVER_ERROR_INTERNAL);
            AirlineApplication a = (AirlineApplication) getApplication();

            JacksonRepresentation jr = new JacksonRepresentation(r, Passenger.class);
            Passenger passenger = (Passenger) jr.getObject();
            for (Flight flight: a.allflights) {
                if (flight.getId().equals(idVol) && !flight.full()) {
                    flight.addPassenger(passenger ,place);
                    return true;
                }
            }
            return false;
        }
    }

    public static class FlightsPlaceResource extends ServerResource {
        @Get("json")
        public JacksonRepresentation request() {
            String prenom = getQueryValue("firstname");
            String nom = getQueryValue("lastname");
            Passenger passenger = new Passenger(prenom, nom);
            Object idVol = getRequest().getAttributes().get("idVol");

            Application app = this.getApplication();
            if (!(app instanceof AirlineApplication)) {
                throw new ResourceException(Status.SERVER_ERROR_INTERNAL);
            }
            AirlineApplication a = (AirlineApplication) getApplication();

            for (Flight flight: a.allflights) {
                if (flight.getId().equals(idVol) && flight.getPassengers().contains(passenger))
                    return new JacksonRepresentation<>(flight.getPlace(passenger));
            }
            return null;
        }
    }
}
