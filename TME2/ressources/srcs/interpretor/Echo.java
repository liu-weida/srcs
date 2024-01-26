package srcs.interpretor;

import travail.Command;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Echo implements Command {
    List<String> args;
    public Echo(List<String> args){
        this.args = args;
    }

    @Override
    public void execute(PrintStream out) {
        for (String str :args){
            out.print(str);
        }
    }
}
