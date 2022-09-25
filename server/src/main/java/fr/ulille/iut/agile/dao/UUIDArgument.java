package fr.ulille.iut.agile.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import org.jdbi.v3.core.argument.Argument;
import org.jdbi.v3.core.statement.StatementContext;

public class UUIDArgument implements Argument {
    private final UUID value;

    public UUIDArgument(UUID value) {
        this.value = value;
    }

    @Override
    public void apply(int position, PreparedStatement statement, StatementContext ctx) throws SQLException {
        statement.setString(position, value.toString());
    }
}
