package fr.ulille.iut.agile.resources;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("interface")
public class APIResource {
	
	private final String TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2MTY1OTEyOTgsIm5iZiI6MTYxNjU5MTI5OCwianRpIjoiYzIyY2JiYTQtNWZmYi00MTcxLWFkMDQtOTdjY2U5MTg4NGUzIiwiZXhwIjoxNjE5MTgzMjk4LCJpZGVudGl0eSI6ImRldiIsImZyZXNoIjpmYWxzZSwidHlwZSI6ImFjY2VzcyJ9.maX6zJ-j5V5KPga461jN6yx_IwSnBeX0Zv5KlxnIKcE";
	
	/**
	 * Renvoie l'espèce tarifaire de l'objet passé en description ou l'erreur renvoyée par le serveur. Les paramètres sont passés directement dans l'url
	 * @param desc description de l'objet recherché
	 * @param min_sim FACULTATIF similarité minimale requise
	 * @param max_res FACULTATIF le nombre maximal de résultats requis
	 * @return le contenu du résultat
	 */
	@GET
	@Path("espece")
	@Produces(MediaType.APPLICATION_JSON)
	public String getEspeceQueryParam(@QueryParam("desc") String desc, @QueryParam("min_sim") String min_sim, @QueryParam("max_res") String max_res) {
		if(desc == null) {
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}
		if(min_sim == null) {
			min_sim = "0.3";
		}
		if(max_res == null) {
			max_res = "20";
		}
		
		HttpRequest request = null;
		HttpResponse<String> response = null;
		
		try {
			request = HttpRequest.newBuilder(new URI("https://api-staging.customsbridge.fr/classify/"))
					.header("Authorization", "Bearer "+this.TOKEN)
					.header("content-type", "application/json")
					.POST(HttpRequest.BodyPublishers.ofString("{\"description\":\""+desc+"\", \"min_similarity\":"+min_sim+", \"max_result\":"+max_res+"}"))
					.build();
			response = HttpClient.newBuilder()
					.build()
					.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (URISyntaxException e) {
			e.printStackTrace();
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
		}
		
		if(response.statusCode() < 200 || response.statusCode() >= 300) {
			throw new WebApplicationException(response.statusCode());
		}
		
		return response.body();
	}
	
	@GET
	public String getAllNomenclature() {
		
		return "";
	}
}
