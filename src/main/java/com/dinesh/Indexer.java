package com.dinesh;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Indexer class
 *
 * This class is responsible for indexing text files using Apache Lucene.
 * It reads file content, creates Lucene documents, and stores them
 * in a file-system-based index directory.
 *
 * Typical usage:
 * 1. Create an Indexer instance with the index directory path
 * 2. Call indexFile() for each file to be indexed
 * 3. Close the indexer once indexing is complete
 */
public class Indexer {

    /**
     * IndexWriter is the core component used to write documents to the Lucene index.
     */
    private IndexWriter writer;

    /**
     * Constructor that initializes the Lucene IndexWriter.
     *
     * @param indexDir Path to the directory where the Lucene index will be stored
     * @throws IOException if the index directory cannot be accessed
     */
    public Indexer(String indexDir) throws IOException {

        // Open (or create) the directory where the index will be stored
        FSDirectory dir = FSDirectory.open(Paths.get(indexDir));

        // StandardAnalyzer is used to tokenize and analyze text content
        StandardAnalyzer analyzer = new StandardAnalyzer();

        // Configure the IndexWriter with the analyzer
        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        // CREATE mode deletes any existing index and creates a new one
        // Remove this line if you want to append to an existing index
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);

        // Initialize the IndexWriter
        writer = new IndexWriter(dir, config);
    }

    /**
     * Closes the IndexWriter and releases all resources.
     *
     * @throws IOException if closing the writer fails
     */
    public void close() throws IOException {
        writer.close();
    }

    /**
     * Indexes a single text file.
     *
     * @param filePath Path of the file to be indexed
     * @throws IOException if the file cannot be read
     */
    public void indexFile(Path filePath) throws IOException {

        // Read the entire file content as a String
        String content = Files.readString(filePath);

        // Create a new Lucene document
        Document doc = new Document();

        // Store the file path (not analyzed, exact match)
        doc.add(new StringField("path", filePath.toString(), Field.Store.YES));

        // Store and analyze the file content for full-text search
        doc.add(new TextField("content", content, Field.Store.YES));

        // Add the document to the index
        writer.addDocument(doc);

        // Log indexed file name
        System.out.println("Indexed: " + filePath.getFileName());
    }

    /**
     * Returns the total number of indexed documents.
     *
     * @return number of documents currently in the index
     */
    public int getIndexedCount() {
        return writer.getDocStats().numDocs;
    }
}
