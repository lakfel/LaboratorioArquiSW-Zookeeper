/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.main;

/**
 *
 * @author Felipe
 */
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import javax.persistence.EntityManager;

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
            webPort = "8082";
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

         try 
        {
            JSONObject datos = new JSONObject();
            datos.put("nombrePagina","Competencias");
            datos.put("urlPagina","localHost:8082");
            JSONObject datos2 = new JSONObject();
            datos2.put("nombrePagina","Competencias");
            datos2.put("nuevoEstado","Activo");
            JSONObject datos3 = new JSONObject();
            datos3.put("nombrePagina","Competencias");
            datos3.put("nombreServicio","Competencias");
            datos3.put("rutaServicio","Competencias");
            JSONObject datos4 = new JSONObject();
            datos4.put("nombrePagina","Competencias");
            datos4.put("nombreServicio","Ganadores");
            datos4.put("rutaServicio","Competencias/winners");
            
            
        
            
            Client client = Client.create();
            WebResource target = client.resource(SERVIDOR_ZK + "inscribirPagina");
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
        
        
        server.setHandler(root);

        server.start();
        server.join();
    }

}
