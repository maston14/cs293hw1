import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

import java.io.*;
import java.util.Iterator;

/**
 * Created by YIZHONGQI on 31/01/2017.
 */


public class TrecDocIterator implements Iterator<Document> {

    protected BufferedReader rdr;
    protected boolean at_eof = false;

    public TrecDocIterator(File file) throws FileNotFoundException {
        rdr = new BufferedReader(new FileReader(file));
        System.out.println("Reading " + file.toString());
    }

    public boolean hasNext() {
        return !at_eof;
    }

    public Document next() {
        Document doc = new Document();

        try {

            String line = null;
            line = rdr.readLine();
            while( line != null && line.equals("") ) {
                line = rdr.readLine();
            }

            if (line == null) {
                at_eof = true;
                return null;
            }
            String[] parts = line.split("\\t");

            if( parts.length != 3 ) {
                System.out.println( "length is " + parts.length + ", Wrong length");
                System.out.println(parts[0]);

            }

            String docno = parts[0];
            String title = parts.length >= 2 ? parts[1] : "";
            String contents = parts.length >= 3 ? parts[2] : "";

            doc.add(new StringField("docno", docno, Field.Store.YES));
            doc.add(new TextField("title", title, Field.Store.YES));
            doc.add(new TextField("contents", contents, Field.Store.NO));

        } catch (IOException e) {
            doc = null;
        }
        return doc;
    }

    public void remove() {
        // Do nothing, but don't complain
    }

}
