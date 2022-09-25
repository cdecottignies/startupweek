package fr.ulille.iut.agile.resources;

import java.sql.SQLException;

import fr.ulille.iut.agile.BDDFactory;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

/**
 * BDDClearRessource
 */
@Path("cleardatabase")
public class BDDClearRessource {

    @GET
    public void clearDatabase()  throws SQLException {
        BDDFactory.dropTables();
    }
}
