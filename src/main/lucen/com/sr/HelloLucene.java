package com.sr;

import com.tutorialspoint.MockData;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

public class HelloLucene {
    static StandardAnalyzer analyzer;
    static Directory index;

    public static void addDocs(StandardAnalyzer analyzer, Directory index) throws Exception {
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter w = new IndexWriter(index, config);
        new MockData().getDataList().forEach(map -> {
            try {
                addDoc(w, map);
            } catch (Exception e) {
               System.out.println(e.getMessage());
            }
        });
        w.close();
    }


    public static void search(String querystr) throws Exception {
        Query q = new QueryParser("address", analyzer).parse(querystr);
        int hitsPerPage = 10;
        IndexReader reader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopDocs topDocs = searcher.search(q, hitsPerPage);
        reader.close();
    }

    public static void main(String[] args) throws Exception {
        analyzer = new StandardAnalyzer();
        index = FSDirectory.open(Paths.get("/index/"));
        addDocs(analyzer, index);
        search("Fuller");
    }

    private static void addDoc(IndexWriter w, Map map) throws Exception {
        Document doc = new Document();
        doc.add(new LongField("id", Long.parseLong(map.get("id").toString()), Field.Store.YES));
        doc.add(new StringField("first_name", (String) map.get("first_name"), Field.Store.YES));
        doc.add(new StringField("last_name", (String) map.get("last_name"), Field.Store.YES));
        doc.add(new StringField("email", (String) map.get("email"), Field.Store.YES));
        doc.add(new StringField("gender", (String) map.get("gender"), Field.Store.YES));
        doc.add(new TextField("address", (String) map.get("address"), Field.Store.YES));
        w.addDocument(doc);
    }

    public static void searchOld(String querystr) throws Exception {
        Query q = new QueryParser("first_name", analyzer).parse(querystr);
        int hitsPerPage = 10;
        IndexReader reader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage);
        searcher.search(q, collector);
        ScoreDoc[] hits = collector.topDocs().scoreDocs;

        System.out.println("Found " + hits.length + " hits.");
        for(int i=0;i<hits.length;++i) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            System.out.println((i + 1) + ". " + d.get("isbn") + "\t" + d.get("title"));
        }
        reader.close();
    }
}