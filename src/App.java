import java.nio.file.Files;
import java.nio.file.Path;
import java.io.StringReader;

import gals.Lexico;
import gals.Semantico;
import gals.Sintatico;

import gals.LexicalError;
import gals.SyntacticError;
import gals.SemanticError;
import java.io.IOException;

public class App {
    public static void main(String[] args) {
        try {
            String codigo = Files.readString(Path.of("examples/programa.txt"));

            Lexico lexico = new Lexico(new StringReader(codigo));
            Sintatico sintatico = new Sintatico();
            Semantico semantico = new Semantico();

            sintatico.parse(lexico, semantico);

        } catch (LexicalError e) {
            System.out.println("Erro léxico: " + e.getMessage());

        } catch (SyntacticError e) {
            System.out.println(
                    "Erro sintático na posição "
                            + e.getPosition()
                            + ": "
                            + e.getMessage());

        } catch (SemanticError e) {
            System.out.println("Erro semântico: " + e.getMessage());

        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }

    }
}
