import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MiniInterpretadorFluxo {

    // Tabela de Símbolos
    private static final Map<String, List<Integer>> dadosImutaveis = new HashMap<>();
    private static final Map<String, Function<Integer, Integer>> funcoesTransformacao = new HashMap<>();
    private static final Map<String, Predicate<Integer>> funcoesFiltro = new HashMap<>();

    public static void main(String[] args) {
        // 1. Executando comando: VAL entrada = [1, 2, 3, 4, 5, 6]
        dadosImutaveis.put("entrada", List.of(1, 2, 3, 4, 5, 6));

        // 2. Executando comando: DEF dobro(x) = x * 2
        funcoesTransformacao.put("dobro", x -> Integer.valueOf(x * 2) );

        // 3. Executando comando: DEF eMaiorQueCinco(x) = x > 5
        funcoesFiltro.put("eMaiorQueCinco", x -> x > 5);

        // 4. Executando o Pipeline: entrada |> MAP dobro |> FILTER eMaiorQueCinco
        List<Integer> resultado = executarPipeline("entrada", "dobro", "eMaiorQueCinco");

        // Exibição do resultado
        System.out.println("Entrada Original: " + dadosImutaveis.get("entrada"));
        System.out.println("Resultado do Fluxo: " + resultado);
    }

    public static List<Integer> executarPipeline(String nomeVariavel, String nomeMap, String nomeFilter) {
        List<Integer> colecaoOriginal = dadosImutaveis.get(nomeVariavel);

        if (colecaoOriginal == null) {
            throw new IllegalArgumentException("Variável não encontrada: " + nomeVariavel);
        }

        // Execução do pipeline sem alterar a lista original (Imutabilidade)
        return colecaoOriginal.stream()
                .map(funcoesTransformacao.get(nomeMap))       // Comando MAP
                .filter(funcoesFiltro.get(nomeFilter))         // Comando FILTER
                .collect(Collectors.toUnmodifiableList());
    }
}