package srcs.webservices;

import org.restlet.*;
import org.restlet.routing.Router;
import srcs.webservices.airline.scheme.Aircraft;
import srcs.webservices.airline.scheme.Airport;
import srcs.webservices.airline.scheme.Flight;
import srcs.webservices.airline.scheme.Passenger;

import java.util.ArrayList;
import java.util.List;

public class AirlineApplication extends Application {
    public List<Airport> allairports = new ArrayList<>();
    public List<Aircraft> allaircrafts = new ArrayList<>();
    public List<Flight> allflights = new ArrayList<>();
    public List<Passenger> allpassenger = new ArrayList<>();
    private final int portUser;
    private final int portAdmin;

    public AirlineApplication(int portUser, int portAdmin) {
        this.portUser = portUser;
        this.portAdmin = portAdmin;
    }

    public int getPortUser() {
        return portUser;
    }

    public int getPortAdmin() {
        return portAdmin;
    }

    @Override
    public Restlet createInboundRoot() {
        Router router = new Router(getContext());

        router.attach("/admin/airports", AirportsResource.class);
        router.attach("/admin/aircrafts", AircraftsResource.class);
        router.attach("/admin/flights", FlightsResource.class);
        router.attach("/admin/flight/{idVol}/passenger", FlightsResource.FlightsPassengerResource.class);
        router.attach("/admin/flight/{idVol}/passengers", FlightsResource.FlightsPassengersResource.class);
        router.attach("/admin/flight/{idVol}/place", FlightsResource.FlightsPlaceResource.class);

        router.attach("/airports", AirportsResource.class);
        router.attach("/aircrafts", AircraftsResource.class);
        router.attach("/flights", FlightsResource.class);

        return router;
    }

//    public static class PortFilter extends Filter {
//        private final int portUser;
//        private final int portAdmin;
//
//        public PortFilter(Context context, Restlet next, int portUser, int portAdmin) {
//            super(context, next);
//            this.portUser = portUser;
//            this.portAdmin = portAdmin;
//        }
//
//        @Override
//        protected int beforeHandle(Request request, Response response) {
//            int serverPort = response.getServerInfo().getPort();
//            Method method = request.getMethod();
//            String path = request.getResourceRef().getPath();
//
//            System.out.println("Server port: " + serverPort);
//            System.out.println("Method: " + method);
//            System.out.println("Path: " + path);
//
//            if (serverPort == portUser) {
//                System.out.println(path);
//                if (path.equals("/airports") && method.equals(Method.POST)) {
//                    response.setStatus(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);
//                    return STOP;
//                }
//                if (path.startsWith("/admin")){
//                    response.setStatus(Status.CLIENT_ERROR_NOT_FOUND);
//                    return STOP;
//                }
//            }
//
//            return CONTINUE;
//        }
//    }

}
