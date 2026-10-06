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

            // Recebe o nome da variavel que vai receber o valor, inicio do fluxo de atribuicao
            case 1: {
                variavelAtual = token.getLexeme();
                break;
            }

            // Recebe o resultado da pilha (9) e atribui a variavel (1), fim do fluxo de atribuicao
            case 2: {
                variaveis.put(variavelAtual, valores.pop());
                variavelAtual = null;
                break;
            }

            // SHOW
            case 3: {
                int resultado = valores.pop();

                String binario = resultado < 0
                        ? "-" + Integer.toBinaryString(Math.abs(resultado))
                        : Integer.toBinaryString(resultado);
                System.out.println(binario);
                break;
            }

            // SOMA
            case 4: {
                int direita = valores.pop();
                int esquerda = valores.pop();
                valores.push(esquerda + direita);
                break;
            }

            // SUBTRACAO
            case 5: {
                int direita = valores.pop();
                int esquerda = valores.pop();
                int resultadoSubtracao = esquerda - direita;

                valores.push(resultadoSubtracao);
                break;
            }

            // MULTIPLICACAO
            case 6: {
                int direita = valores.pop();
                int esquerda = valores.pop();
                valores.push(esquerda * direita);
                break;
            }

            // DIVISAO
            case 7: {
                int direita = valores.pop();
                int esquerda = valores.pop();

                if (direita == 0) {
                    throw new SemanticError(
                            "Divisão por zero não permitida.",
                            token.getPosition());
                }

                valores.push(esquerda / direita);
                break;
            }

            // POTENCIA
            case 8: {
                int expoente = valores.pop();
                int base = valores.pop();
                if (expoente < 0) {
                    throw new SemanticError(
                            "Expoente negativo não permitido.",
                            token.getPosition());
                }

                double resultadoPotencia = Math.pow(base, expoente);

                if (resultadoPotencia > Integer.MAX_VALUE) {
                    throw new SemanticError(
                            "Resultado da exponenciação excede o limite permitido.",
                            token.getPosition());
                }
                valores.push((int) resultadoPotencia);
                break;
            }

            // Converte o literal binario para int e empilha o valor pra ser atribuido ou
            // ser usado em uma operacao
            case 9: {
                int valor = Integer.parseInt(token.getLexeme(), 2);
                valores.push(valor);
                break;
            }

            // consulta o valor da variavel e coloca na pilha pra operacao
            case 10: {
                String nome = token.getLexeme();
                Integer valorVariavel = variaveis.get(nome);

                if (valorVariavel == null) {
                    throw new SemanticError(
                            "Variável '" + nome + "' não inicializada.",
                            token.getPosition());
                }

                valores.push(valorVariavel);
                break;
            }

            // LOGARITMO
            case 11: {
                int baseLog = valores.pop();
                if (baseLog <= 0) {
                    throw new SemanticError(
                            "Logaritmo de número não positivo não permitido.",
                            token.getPosition());
                }
                double resultadoLog = Math.log10(baseLog);
                if (resultadoLog > Integer.MAX_VALUE) {
                    throw new SemanticError(
                            "Resultado do logaritmo excede o limite permitido.",
                            token.getPosition());
                }
                valores.push((int) resultadoLog);
                break;
            }

            default:
                System.out.println("Ação #" + action + ", Token: " + token);
                break;
        }
    }
}
