package com.example;


import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.json.simple.JSONObject;

/**
 *
 * This class launches the web application in an embedded Jetty container.
 * This is the entry point to your application. The Java command that is used for
 * launching should fire this main method.
 *
 */
public class Main {

    public static final String SERVIDOR_ZK = "http://localHost:8080/directorio/";
    
    /**
     * @param args
     */
    public static void main(String[] args) throws Exception{
        String webappDirLocation = "src/main/webapp/";

        // The port that we should run on can be set into an environment variable
        // Look for that variable and default to 8080 if it isn't there.
        String webPort = System.getenv("PORT");
        if (webPort == null || webPort.isEmpty()) {
            webPort = "8081";
        }

        Server server = new Server(Integer.valueOf(webPort));
        WebAppContext root = new WebAppContext();

        root.setContextPath("/");
        root.setDescriptor(webappDirLocation + "/WEB-INF/web.xml");
        root.setResourceBase(webappDirLocation);
        
        PersistenceManager.getInstance().getEntityManagerFactory();

        // Parent loader priority is a class loader setting that Jetty accepts.
        // By default Jetty will behave like most web containers in that it will
        // allow your application to replace non-server libraries that are part of the
        // container. Setting parent loader priority to true changes this behavior.
        // Read more here: http://wiki.eclipse.org/Jetty/Reference/Jetty_Classloading
        root.setParentLoaderPriority(true);

        server.setHandler(root);

        try 
        {
            JSONObject datos = new JSONObject();
            datos.put("nombrePagina","Competitors");
            datos.put("urlPagina","localHost:8081");
            JSONObject datos2 = new JSONObject();
            datos2.put("nombrePagina","Competitors");
            datos2.put("nuevoEstado","Activo");
            JSONObject datos3 = new JSONObject();
            datos3.put("nombrePagina","Competitors");
            datos3.put("nombreServicio","Competidores");
            datos3.put("rutaServicio","competitors");
            JSONObject datos4 = new JSONObject();
            datos4.put("nombrePagina","Competitors");
            datos4.put("nombreServicio","PorId");
            datos4.put("rutaServicio","competitors/{id}");
            
            
        
            
            Client client = Client.create();
            WebResource target = client.resource(SERVIDOR_ZK + "pagina");
            target.post(JSONObject.class,datos);
            target = client.resource(SERVIDOR_ZK+ "estadoPagina");
            target.post(JSONObject.class,datos2);
            target = client.resource(SERVIDOR_ZK+ "servicioPagina");
            target.post(JSONObject.class,datos3);
            target = client.resource(SERVIDOR_ZK+ "servicioPagina");
            target.post(JSONObject.class,datos4);
            
            
            
            client.destroy();
            
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        
        server.start();
        server.join();
        
        
        
    }

}
