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

public class Indexer {

    // Lucene writer responsible for indexing documents
    private IndexWriter writer;

    // Initialize index writer with standard analyzer
    public Indexer(String indexDir) throws IOException {
        FSDirectory dir = FSDirectory.open(Paths.get(indexDir));
        StandardAnalyzer analyzer = new StandardAnalyzer();

        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        // Create a fresh index (change mode if appending is required)
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);

        writer = new IndexWriter(dir, config);
    }

    // Close writer and release resources
    public void close() throws IOException {
        writer.close();
    }

    // Index a single text file
    public void indexFile(Path filePath) throws IOException {
        String content = Files.readString(filePath);

        Document doc = new Document();
        // Store file path for reference
        doc.add(new StringField("path", filePath.toString(), Field.Store.YES));
        // Index file content for search
        doc.add(new TextField("content", content, Field.Store.YES));

        writer.addDocument(doc);
        System.out.println("Indexed: " + filePath.getFileName());
    }

    // Returns number of indexed documents
    public int getIndexedCount() {
        return writer.getDocStats().numDocs;
    }
}
