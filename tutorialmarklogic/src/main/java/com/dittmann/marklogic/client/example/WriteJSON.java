package com.dittmann.marklogic.client.example;

import com.google.gson.Gson;
import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.JSONDocumentManager;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.StringHandle;

import javax.xml.namespace.QName;
import java.time.Instant;

public class WriteJSON {
    private static final Gson GSON = new Gson();

    private static final String DIRECTORY = "/dir/";

    public static void main(String[] args) {
        final DatabaseClient databaseClient = DatabaseClientFactory.newClient(MarklogicConnectionConfig.getHost(),
                MarklogicConnectionConfig.getPort(),
                new DatabaseClientFactory.DigestAuthContext(MarklogicConnectionConfig.getUser(),
                        MarklogicConnectionConfig.getPassword()));


        final JSONDocumentManager docMgr = databaseClient.newJSONDocumentManager();
        final DocumentMetadataHandle metadata = new DocumentMetadataHandle();

        for (int i = 0; i < 5; i++) {
            final String toJson = GSON.toJson(new TestClass("user " + i, "surname " + i));

            final StringHandle handle = new StringHandle(toJson);
            metadata.getCollections().addAll("collection");
            metadata.getProperties().put(QName.valueOf("INSERTION_TIME"), Instant.now().toEpochMilli());

            // write the document content
            docMgr.write(DIRECTORY + "user_" + i + ".json", metadata, handle);
        }

        databaseClient.release();
    }
}
