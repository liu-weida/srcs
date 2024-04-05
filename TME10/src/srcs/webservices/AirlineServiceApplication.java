package srcs.webservices;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;
import srcs.webservices.airline.scheme.*;

public class AirlineServiceApplication extends Application  {

    private boolean isAdmin;


    public AirlineServiceApplication(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Override
    public Restlet createInboundRoot() {
        Router router = new Router(getContext());

        if (isAdmin) {
            // 配置管理员端口的路由
            router.attach("/admin/airports", AdminAirportsResource.class);
            router.attach("/admin/aircrafts", AdminAircraftsResource.class);
            router.attach("/admin/flights", AdminFlightsResource.class);

        } else {
            // 配置普通用户端口的路由
            router.attach("/airports", AirportsResource.class);
            router.attach("/aircrafts", AircraftsResource.class);
            router.attach("/flights", FlightsResource.class);
        }

        return router;
    }



}
