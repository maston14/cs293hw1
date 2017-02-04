import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by YIZHONGQI on 31/01/2017.
 */
public class wtf {
    public static void main(String[] args) {
        try {
            BufferedReader rdr = new BufferedReader(new FileReader("lines-10.txt"));
            String line;
            line = rdr.readLine();

            String[] part = line.split("\\t+");

            System.out.println(line);

            System.out.println( part.length );
            System.out.println(part[0]);
            System.out.println(part[1]);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
