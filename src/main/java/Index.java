import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;


/**
 * Created by YIZHONGQI on 31/01/2017.
 */
public class Index {
    public static void main(String[] args) {
        String indexPath = "index";
        String docsPath = "lines-10.txt";

        final File docDir = new File(docsPath);
        Path path = new File( indexPath ).toPath();

        Date start = new Date();
        try {
            Directory dir = FSDirectory.open( path );

            Analyzer analyzer = new StandardAnalyzer();
            IndexWriterConfig iwc = new IndexWriterConfig( analyzer );

            iwc.setOpenMode(OpenMode.CREATE);
            iwc.setRAMBufferSizeMB(256.0);

            IndexWriter writer = new IndexWriter(dir, iwc);
            indexDocs(writer, docDir);

            writer.close();

            Date end = new Date();
            System.out.println(end.getTime() - start.getTime() + " total milliseconds");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    static void indexDocs(IndexWriter writer, File file)
            throws IOException {
        // do not try to index files that cannot be read
        if (file.canRead()) {
            if (file.isDirectory()) {
                String[] files = file.list();
                // an IO error could occur
                if (files != null) {
                    for (int i = 0; i < files.length; i++) {
                        indexDocs(writer, new File(file, files[i]));
                    }
                }
            } else {
                TrecDocIterator docs = new TrecDocIterator(file);
                Document doc;
                while (docs.hasNext()) {
                    doc = docs.next();
                    if (doc != null && doc.getField("contents") != null)
                        writer.addDocument(doc);
                }
            }
        }
    }

}
