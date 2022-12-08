package net.mcentire.repository;

import net.mcentire.model.Division;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DivisionRepository extends EntityRepository<Division> {

    private static final String tableName = "first_level_divisions";
    private static final String tableIdentifier = "Division_ID";
    private static final String[] entityCreatorFields = {
            "Division",
            "Country_ID"
    };
    private static final String[] entityUpdaterFields = {
            "Division",
            "Country_ID"
    };

    @Override
    String getTableName() {
        return tableName;
    }

    @Override
    String getTableIdentifier() {
        return tableIdentifier;
    }

    @Override
    String[] getEntityCreatorFields() {
        return entityCreatorFields;
    }

    @Override
    String[] getEntityUpdaterFields() {
        return entityUpdaterFields;
    }

    @Override
    Division createEntityFromResultSet(ResultSet rs) {
        try {
            Division division = new Division(
                    rs.getInt("Division_ID"),
                    rs.getString("Division"),
                    rs.getInt("Country_ID")
            );
            return division;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    QueryParameter[] getEntityCreatorQueryParams(Division entity) {
        QueryParameter[] params = new QueryParameter[]{
                new QueryParameter(entity.getName()),
                new QueryParameter(entity.getCountryId())
        };
        return params;
    }

    @Override
    QueryParameter[] getEntityUpdaterQueryParams(Division entity) {
        QueryParameter[] params = new QueryParameter[]{
                new QueryParameter(entity.getName()),
                new QueryParameter(entity.getCountryId())
        };
        return params;
    }
}
