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
 * Indexes text files using Apache Lucene.
 */
public class Indexer {

    private IndexWriter writer;

    public Indexer(String indexDir) throws IOException {
        FSDirectory dir = FSDirectory.open(Paths.get(indexDir));
        StandardAnalyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE); // If you want to freshly index the files keep this line, else can remove
        writer = new IndexWriter(dir, config);
    }

    public void close() throws IOException {
        writer.close();
    }

    public void indexFile(Path filePath) throws IOException {
        String content = Files.readString(filePath);
        
        Document doc = new Document();
        doc.add(new StringField("path", filePath.toString(), Field.Store.YES));
        doc.add(new TextField("content", content, Field.Store.YES));
        
        writer.addDocument(doc);
        System.out.println("Indexed: " + filePath.getFileName());
    }

    public int getIndexedCount() {
        return writer.getDocStats().numDocs;
    }
}