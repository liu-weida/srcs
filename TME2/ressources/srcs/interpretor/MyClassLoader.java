package srcs.interpretor;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyClassLoader extends ObjectInputStream {
    public MyClassLoader(InputStream in) throws IOException {
        super(in);
    }

    @Override
    protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
        String className = desc.getName();
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e){
            String tempFolderPath = System.getProperty("java.io.tmpdir");
            URLClassLoader ucl = URLClassLoader.newInstance(new URL[]{new File(tempFolderPath+"/"+matchFolders(tempFolderPath, "commands.*$")).toURI().toURL()});
            return ucl.loadClass(className).asSubclass(Command.class);
        }
    }
    private static String matchFolders(String folderPath, String regex) {
        File folder = new File(folderPath);
        File[] subFolders = folder.listFiles(File::isDirectory);
        if (subFolders != null) {
            Pattern pattern = Pattern.compile(regex);
            for (File subFolder : subFolders) {
                Matcher matcher = pattern.matcher(subFolder.getName());
                if (matcher.matches())
                    return subFolder.getName();
            }
        }
        return null;
    }
}

