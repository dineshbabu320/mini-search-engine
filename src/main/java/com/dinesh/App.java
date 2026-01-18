package com.dinesh;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Mini Search Engine
 * 
 * A search engine using Apache Lucene
 * 1. Fetch N URL's
 * 2. Extract text data from HTML
 * 3. Index the text
 * 4. Allow user search
 * 5. Return the relevant URL's
 */

public class App {

    private static final String INDEX_DIR = "index";
    private static final String DATA_DIR = "data";

    public static void main(String[] args) {
        System.out.println("Mini Search Engine");

        try {
            // Create indexer
            Indexer indexer = new Indexer(INDEX_DIR);

            // Index sample file
            Path sampleFile = Paths.get(DATA_DIR, "sample.txt");
            indexer.indexFile(sampleFile);

            System.out.println("\nTotal documents indexed: " + indexer.getIndexedCount());

            indexer.close();
            System.out.println("Index created at: " + INDEX_DIR + "/");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}