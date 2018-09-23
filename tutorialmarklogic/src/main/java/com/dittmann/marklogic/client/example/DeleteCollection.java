package com.dittmann.marklogic.client.example;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.query.DeleteQueryDefinition;
import com.marklogic.client.query.QueryManager;

public class DeleteCollection {
    public static void main(String[] args) {
        final DatabaseClient databaseClient = DatabaseClientFactory.newClient(MarklogicConnectionConfig.getHost(),
                MarklogicConnectionConfig.getPort(),
                new DatabaseClientFactory.DigestAuthContext(MarklogicConnectionConfig.getUser(),
                        MarklogicConnectionConfig.getPassword()));

        final QueryManager queryManager = databaseClient.newQueryManager();
        final DeleteQueryDefinition deleteQueryDefinition = queryManager.newDeleteDefinition();
        deleteQueryDefinition.setCollections("collection");
        queryManager.delete(deleteQueryDefinition);

        databaseClient.release();
    }
}
