import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.String;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.exit;


public class App {

    public static void main(String[] args) {
        extractURL();
    }

    public static void extractURL() {
        JFrame frame=new JFrame();
        JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(true);
        chooser.showOpenDialog(frame);
        File[] files = chooser.getSelectedFiles();
        int i = 0;
        ArrayList output = new ArrayList<>();
        try {
            for (File file : files) {
                Matcher m = urlPattern.matcher(readfromfile(files[i]).toString());
                while (m.find()) {
                    output.add(m.group());
                }
                i++;
            }
            //f.setVisible(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(output);
        exit(1);

    }

    public static ArrayList readfromfile(File files) throws IOException {
        ArrayList output = new ArrayList<>();
        if (files.isFile()) {
            BufferedReader inputStream = null;
            String line;
            try {
                inputStream = new BufferedReader(new FileReader(files));
                while ((line = inputStream.readLine()) != null) {
                    output.add(line);
                }
            } catch (IOException e) {
                System.out.println(e);
            } finally {
                if (inputStream != null) {

                    inputStream.close();
                    return output;
                }
            }
        }

        return output;
    }
    private static final Pattern urlPattern = Pattern.compile(
            "(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)"
                    + "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*"
                    + "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)",
            Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);

}

