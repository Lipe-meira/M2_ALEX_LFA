import java.nio.file.Files;
import java.nio.file.Path;
import java.io.StringReader;

import gals.Lexico;
import gals.Semantico;
import gals.Sintatico;

public class App {
    public static void main(String[] args) throws Exception {
        String codigo = Files.readString(Path.of("examples/programa.txt"));
        Lexico lexico = new Lexico(new StringReader(codigo));
        Sintatico sintatico = new Sintatico();
        Semantico semantico = new Semantico();

        sintatico.parse(lexico, semantico);

    }
}
