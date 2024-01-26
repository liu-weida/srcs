package srcs.interpretor;

import travail.Command;

import java.io.*;
import java.nio.file.Path;

public class Cat implements Command {

    String path;

    public Cat(String path){
        this.path = path;
    }

    @Override
    public void execute(PrintStream out) {

        try {
            File file = new File(path);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                out.print(line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
