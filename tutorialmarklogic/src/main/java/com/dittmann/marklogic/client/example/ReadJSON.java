package com.dittmann.marklogic.client.example;

import com.google.gson.Gson;
import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.JSONDocumentManager;
import com.marklogic.client.io.SearchHandle;
import com.marklogic.client.io.StringHandle;
import com.marklogic.client.query.MatchDocumentSummary;
import com.marklogic.client.query.QueryManager;
import com.marklogic.client.query.StringQueryDefinition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReadJSON {

    private static final Gson GSON = new Gson();

    public static void main(String[] args) {
        final DatabaseClient databaseClient = DatabaseClientFactory.newClient(MarklogicConnectionConfig.getHost(),
                MarklogicConnectionConfig.getPort(),
                new DatabaseClientFactory.DigestAuthContext(MarklogicConnectionConfig.getUser(),
                        MarklogicConnectionConfig.getPassword()));


        final QueryManager queryMgr = databaseClient.newQueryManager();
        queryMgr.setPageLength(500000);

        final StringQueryDefinition query = queryMgr.newStringDefinition();
        query.setDirectory("/dir/");
        query.setCollections("collection");

        final SearchHandle resultsHandle = new SearchHandle();
        queryMgr.search(query, resultsHandle);

        final JSONDocumentManager jsonDocumentManager = databaseClient.newJSONDocumentManager();
        final MatchDocumentSummary[] matchResults = resultsHandle.getMatchResults();
        final List<TestClass> testClasses = new ArrayList<>();

        Arrays.asList(matchResults).forEach(matchDocumentSummary -> {
            final StringHandle content = new StringHandle();
            final StringHandle read = jsonDocumentManager.read(matchDocumentSummary.getUri(), content);
            final TestClass testClass = GSON.fromJson(read.get(), TestClass.class);
            testClasses.add(testClass);
        });

        System.out.println(testClasses);

        databaseClient.release();
    }
}
