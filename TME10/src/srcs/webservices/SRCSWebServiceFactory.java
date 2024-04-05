package srcs.webservices;

import org.restlet.Component;
import org.restlet.data.Protocol;

public class SRCSWebServiceFactory {

    public static SRCSWebService buildAirline(String name, int portUser, int portAdmin) {
        Component component = new Component();

        // 用户端口配置
        component.getServers().add(Protocol.HTTP, portUser);
        // 管理端口配置
        component.getServers().add(Protocol.HTTP, portAdmin);

        // 应用实例配置
        AirlineServiceApplication app1 = new AirlineServiceApplication(false);
        component.getDefaultHost().attach("", app1); // 普通用户的应用

        AirlineServiceApplication app2 = new AirlineServiceApplication(true);
        component.getDefaultHost().attach("/admin", app2); // 管理员的应用，注意这里的路径

        // 返回包装后的Component作为SRCSWebService
        return new SRCSWebService() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public void deploy() throws Exception {
                component.start();
            }

            @Override
            public void undeploy() throws Exception {
                component.stop();
            }
        };
    }

}
