package gals;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Semantico implements Constants {
    private final Map<String, Integer> variaveis = new HashMap<>();
    private final Stack<Integer> valores = new Stack<>();
    private String variavelAtual;

    public void executeAction(int action, Token token) throws SemanticError {

        switch (action) {
            case 1:
                variavelAtual = token.getLexeme();
                break;

            case 2:
                variaveis.put(variavelAtual, valores.pop());
                variavelAtual = null;
                break;
            case 3:
                int resultado = valores.pop();
                System.out.println(Integer.toBinaryString(resultado));
                break;
            case 4: {
                int direita = valores.pop();
                int esquerda = valores.pop();
                valores.push(esquerda + direita);
                break;
            }
            case 9:
                int valor = Integer.parseInt(token.getLexeme(), 2);
                valores.push(valor);
                break;
            case 10:
                String nome = token.getLexeme();
                Integer valorVariavel = variaveis.get(nome);

                if (valorVariavel == null) {
                    throw new SemanticError(
                            "Variável '" + nome + "' não inicializada.",
                            token.getPosition());
                }

                valores.push(valorVariavel);
                break;

            default:
                System.out.println("Ação #" + action + ", Token: " + token);
                break;
        }
    }
}
