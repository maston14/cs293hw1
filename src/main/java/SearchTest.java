import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by YIZHONGQI on 01/02/2017.
 */
public class SearchTest {
    public static void main(String[] args) {
        String indexPath = "index";
        Path path = new File( indexPath ).toPath();
        try {
            IndexReader directory = DirectoryReader.open( FSDirectory.open( path ) );
            IndexSearcher searcher = new IndexSearcher( directory );
            Analyzer analyzer = new StandardAnalyzer();
            //QueryParser parser = new QueryParser( "title", analyzer );
            QueryParser parser = new QueryParser( "contents", analyzer );
            Query query = parser.parse("brazil");

            TopDocs results = searcher.search( query, 2 );
            ScoreDoc[] hits = results.scoreDocs;
            Document doc = searcher.doc(hits[0].doc);

            System.out.println("Title: " + doc.get("title"));
            System.out.println("Contents: " + doc.get("contents"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
