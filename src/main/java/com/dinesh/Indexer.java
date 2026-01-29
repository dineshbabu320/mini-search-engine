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


    private IndexWriter writer;

    // Initialize index writer with standard analyzer
    public Indexer(String indexDir) throws IOException {
        FSDirectory dir = FSDirectory.open(Paths.get(indexDir));
        StandardAnalyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        writer = new IndexWriter(dir, config);
    }


    public void close() throws IOException {
        writer.close();
    }

    // Index a single text file
    public void indexFile(Path filePath) throws IOException {
        String content = Files.readString(filePath);
        
        Document doc = new Document()
        doc.add(new StringField("path", filePath.toString(), Field.Store.YES))
        doc.add(new TextField("content", content, Field.Store.YES));
        
        writer.addDocument(doc);
        System.out.println("Indexed: " + filePath.getFileName());
    }

    // Returns number of indexed documents
    public int getIndexedCount() {
        return writer.getDocStats().numDocs;
    }
}
