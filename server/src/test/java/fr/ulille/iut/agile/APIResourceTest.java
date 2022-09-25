package fr.ulille.iut.agile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jakarta.json.Json;
import jakarta.json.stream.JsonParser;
import jakarta.json.stream.JsonParser.Event;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class APIResourceTest {
	
	private HttpServer server;
	private WebTarget target;

	@Before
	public void setUp() throws Exception {
		// start the server
		server = Main.startServer();
		// create the client
		Client c = ClientBuilder.newClient();

		// uncomment the following line if you want to enable
		// support for JSON in the client (you also have to uncomment
		// dependency on jersey-media-json module in pom.xml and Main.startServer())
		// --
		// c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());
		target = c.target(Main.BASE_URI);
	}

	@After
	public void tearDown() throws Exception {
		server.shutdownNow();
	}
	
	@Test
	public void testGetEspece() {
		boolean containsBestCode = false;
		boolean containsSecondBestCode = false;
		Response response = target.path("interface").path("espece")
				.queryParam("desc", "pistolet à colle")
				.request()
				.header("Accept", MediaType.APPLICATION_JSON)
				.get();
		
		assertEquals(200, response.getStatus());
		
		JsonParser parser = Json.createParser(new StringReader(response.readEntity(String.class)));
		
		while(parser.hasNext()) {
			Event e = parser.next();
			if(e == JsonParser.Event.VALUE_STRING) {
				if(parser.getString().equals("84198998")) {
					containsBestCode = true;
				}
				else if(parser.getString().equals("8516797090")) {
					containsSecondBestCode = true;
				}
			}
		}
		
		assertTrue(containsBestCode && containsSecondBestCode);
	}
	
	@Test
	public void testGetAll() {
		
	}
}
