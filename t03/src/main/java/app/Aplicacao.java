package app;

import static spark.Spark.*;
import service.SapatoService;


public class Aplicacao {
	
	private static SapatoService sapatoService = new SapatoService();
	
    public static void main(String[] args) {
        port(6789);
        
        staticFiles.location("/public");
        
        post("/sapato/insert", (request, response) -> sapatoService.insert(request, response));

        get("/sapato/:id", (request, response) -> sapatoService.get(request, response));
        
        get("/sapato/list/:orderby", (request, response) -> sapatoService.getAll(request, response));

        get("/sapato/update/:id", (request, response) -> sapatoService.getToUpdate(request, response));
        
        post("/sapato/update/:id", (request, response) -> sapatoService.update(request, response));
           
        get("/sapato/delete/:id", (request, response) -> sapatoService.delete(request, response));

             
        
    }
}