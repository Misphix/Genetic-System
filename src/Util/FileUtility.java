package Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtility {
    public static String[] getLines(String filesName[]) throws IOException {
        List<String> lines = new ArrayList<>();

        for (String aFileName : filesName) {
            FileReader fr = new FileReader(aFileName);
            BufferedReader br = new BufferedReader(fr);
            while (br.ready()) {
                lines.add(br.readLine());
            }
            fr.close();
            br.close();
        }

        return lines.toArray(new String[lines.size()]);
    }

    public static String[] getFilesName(String folderName) {
        File folder = new File(folderName);
        File[] listOfFiles = folder.listFiles();
        ArrayList<String> lines = new ArrayList<>();

        assert listOfFiles != null;

        for (File file : listOfFiles) {
            if (file.isFile()) {
                lines.add(file.getAbsolutePath());
            }
        }

        return lines.toArray(new String[lines.size()]);
    }
}
