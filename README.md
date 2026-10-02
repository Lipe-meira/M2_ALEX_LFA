# Interpretador de linguagem binária

Projeto acadêmico da disciplina de Linguagens Formais Autômatos.

## Objetivo

Desenvolver, com Web-GALS e Java, uma linguagem para trabalhar com números binários inteiros sem sinal.

A linguagem implementa:

- atribuição de valores e expressões a variáveis;
- exibição de valores;
- soma, subtração, multiplicação e divisão;
- exponenciação;
- logaritmo.

## Tecnologias

- Java
- Web-GALS

## Como executar

É necessário ter um JDK instalado. O projeto foi desenvolvido com Java 21 e não utiliza dependências externas.

Na raiz do projeto, execute no terminal do Windows:

```powershell
javac -d out .\src\App.java .\src\gals\*.java
java -cp out App
```

O `App` lê o programa de `examples/programa.txt`. Edite esse arquivo para testar outros comandos e execute novamente. Se alterar os arquivos Java, recompile antes de executar.

Os arquivos compilados ficam em `out/`, pasta ignorada pelo Git.

## Exemplo

Conteúdo de `examples/programa.txt`:

```text
A = 10;
B = 11;
C = 111 + A * B;
Show(C);
```

Saída:

```text
1101
```

Todos os números escritos no programa são binários: `10` representa 2, `11` representa 3 e `111` representa 7. Nesse exemplo, o resultado é `7 + 2 * 3 = 13`, exibido como `1101`.

Outras operações:

```text
A = 10 ** 11;
Show(A);
B = log(1100100);
Show(B);
```

Saída:

```text
1000
10
```

## Regras da linguagem

- Comandos terminam com `;`.
- Variáveis possuem apenas letras e diferenciam maiúsculas de minúsculas: `A` e `a` são variáveis distintas.
- Os comandos reservados são `Show` e `log`, escritos exatamente assim.
- `Show` recebe uma variável, como em `Show(A);`.
- Literais numéricos contêm apenas `0` e `1`.
- Parênteses permitem agrupar expressões.
- A exponenciação (`**`) tem prioridade sobre multiplicação e divisão, que têm prioridade sobre soma e subtração.
- Potências encadeadas são avaliadas da direita para a esquerda: `A ** B ** C` equivale a `A ** (B ** C)`.
- A divisão é inteira: a parte fracionária é descartada.
- `log` calcula o logaritmo na base 10 e descarta a parte fracionária do resultado.
- Espaços, tabulações e quebras de linha são ignorados. Comentários no programa de entrada não são suportados.

## Estrutura do projeto

```text
examples/
    programa.txt       Programa lido pelo interpretador
src/
    App.java           Leitura do arquivo e execução dos analisadores
    gals/
        Lexico.java    Reconhecimento dos tokens
        Sintatico.java Validação da estrutura dos comandos
        Semantico.java Variáveis, cálculos e exibição de resultados
        Token.java     Identificador, lexema e posição de um token
        ...            Tabelas, constantes e classes de erro do GALS
linguagem.gals         Tokens, gramática, configurações e ações semânticas
README.md              Documentação do projeto
```

O Web-GALS gera os analisadores e as classes de suporte. A lógica do interpretador foi implementada manualmente em `Semantico.java`, a partir da classe gerada, e `App.java` foi escrito para executar o programa. Ao gerar os arquivos novamente, preserve a implementação semântica.

## Funcionamento

O `App` lê o código e fornece seu conteúdo ao analisador léxico. O léxico reconhece os tokens, e o sintático verifica se eles seguem a gramática. Durante a análise, os marcadores `#1` a `#11` acionam métodos da parte semântica.

O `Semantico` armazena as variáveis em um `Map<String, Integer>` e usa uma pilha para avaliar expressões. Os literais são convertidos de binário para `int`; `Show` converte o resultado de volta para binário.

## Erros e limitações atuais

O interpretador detecta:

- caracteres inválidos e comandos fora da gramática;
- uso de variável não inicializada;
- subtração com resultado negativo;
- divisão por zero;
- logaritmo de valor não positivo;
- expoente negativo e potência acima de `Integer.MAX_VALUE`.

Apesar de a linguagem trabalhar com valores sem sinal, a implementação utiliza o `int` do Java: os valores não negativos representáveis vão de 0 a 2.147.483.647. Soma e multiplicação ainda não possuem verificação de estouro. Literais acima desse limite geram `NumberFormatException`.

A execução para no primeiro erro. Atualmente, o `App` propaga as exceções, exibindo o stack trace no terminal. A apresentação de mensagens amigáveis e a organização de uma suíte de testes ainda estão pendentes.

## Web-GALS

O projeto utiliza o [Web-GALS](https://lia-univali.github.io/Web-GALS/) para definir tokens, testar a gramática e gerar código Java. A gramática utiliza análise sintática SLR e ações semânticas para conectar a análise ao interpretador.
