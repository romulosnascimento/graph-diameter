import java.io.*;
import java.util.*;

import static java.lang.Integer.parseInt;

class Diametro {

//  Computação de um diâmetro de um grafo utilizadno o algoritmo de Floyd–Warshall.

    public static void main(String[] args) {
        try {

//          Definição de variáveis.

//          Matriz de adjacências.
            int graph[][] = new int[0][0];

//          Matriz de predecessores.
            int path[][] = new int[0][0];

//          Valor do diâmetro.
            int diameter = 0;

//          Vértices diametrais.
            int diameterU = 0;
            int diameterV = 0;

//          Quantidade de vértices no caminho mínimo entre os vértices diametrais.
            int diameterSize = 0;

//          Índice dos vértices no caminho mínimo entre os vértices diametrais.
            String diameterPath = "";

//          Leitura do arquivo de entrada.
//          É possível inserir um caminho para um arquivo específico
//          ou simplesmente teclar enter para usar um arquivo input.txt
//          localizado no mesmo diretório.

            Scanner scanner = new Scanner(new InputStreamReader(System.in));
            System.out.println("Digite o caminho para o arquivo de entrada ou tecle enter para usar input.txt: ");
            String input = scanner.nextLine();
            input = input.isEmpty() ? "input.txt" : input;

            File inputFile = new File(input);
            System.out.println("Arquivo de entrada: " + input);

            Scanner myReader = new Scanner(inputFile);

//          Inserção dos valores de entrada na matriz de adjacências e de predecesores.
            boolean isFirstLine = true;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

//              Na primeira linha, as matrizes são inicializadas de acordo com o número de vértices do grafo (n).
                if (isFirstLine) {
                    String[] line1 = data.split(" ");
                    int n = parseInt(line1[0]);
                    graph = new int[n][n];
                    path = new int[n][n];
                    isFirstLine = false;

//              Na segunda linha, os pesos são adicionados.
//              Os pesos são comparados com o valor inicial do diâmetro para casos em que eles já são mínimos.
                } else {
                    if (!data.isEmpty()) {
                        String[] edge = data.split(" ");
                        int u = parseInt(edge[0]) - 1;
                        int v = parseInt(edge[1]) - 1;
                        int w = parseInt(edge[2]);
                        graph[u][v] = w;
                        graph[v][u] = w;
                        if (graph[u][v] > diameter) {
                            diameter = graph[u][v];
                            diameterU = u;
                            diameterV = v;
                        }
                    }
                }
            }

//          As posições que correspondem ao caminho de um vértice para ele mesmo são preenchidas com 0.
//          As demais posição, quando vazias, recebem o valor de peso máximo para os nossos casos (300).
//          Custo O(v²)
            for (int u = 0; u < graph.length; u++) {
                for (int v = 0; v < graph.length; v++) {
                    if (u == v) graph[v][v] = 0;
                    else if (graph[u][v] == 0) graph[u][v] = 300;
                    path[v][u] = v;
                    path[u][v] = u;
                }
            }

//          Loop principal do algoritmo de Floyd–Warshall, onde os caminhos mínimos são computados.
//          O valor do diâmetro e os vértices diametrais são atulizados e a matriz de predecessores é preenchida simultaneamente.
//          Custo O(v³)
            for (int k = 0; k < graph.length; k++) {
                for (int i = 0; i < graph.length; i++) {
                    for (int j = 0; j < graph.length; j++) {
                        if (graph[i][j] > graph[i][k] + graph[k][j]) {
                            graph[i][j] = graph[i][k] + graph[k][j];
                            path[i][j] = path[k][j];
                            if (graph[i][j] > diameter) {
                                diameter = graph[i][j];
                                diameterU = i;
                                diameterV = j;
                            }
                        }
                    }
                }
            }

//          Reconstrução do caminho mínimo entre os vértices diametrais.
//          Custo O(v)

            int du = diameterU;
            int dv = diameterV;

            diameterPath = "" + (dv+1);
            diameterSize++;

            while (dv != diameterU) {
                dv = path[du][dv];
                diameterPath = (dv+1) + " "+ diameterPath;
                diameterSize++;
            }

            System.out.println("OUTPUT");
            System.out.println(diameter);
            System.out.println((diameterU+1) + " " + (diameterV+1));
            System.out.println(diameterSize);
            System.out.println(diameterPath);

//          Escrita do arquivo de saída (output.txt).
            BufferedWriter bw = null;
            File file = new File("output.txt");

            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            bw.write("" + diameter);
            bw.newLine();
            bw.write((diameterU+1) + " " + (diameterV+1));
            bw.newLine();
            bw.write("" + diameterSize);
            bw.newLine();
            bw.write(diameterPath);
            if(bw!=null) bw.close();

            System.out.println("Resultado escrito em output.txt");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
