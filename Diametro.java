import java.util.*;
import java.lang.*;
import java.io.*;
import java.math.*;

import static java.lang.Integer.parseInt;

class Diametro {

//  Computação de um diâmetro de um grafo utilizando o algoritmo de Floyd–Warshall.

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
            
            IO io = new IO();
     	    String s = io.next();
            int inputPosition = 1;

            //     	IO io = new IO();
//        while (s != null) {
//            io.write(s + " ");
//            s = io.next();
//        }
//
//     	io.flush();

//          Inserção dos valores de entrada na matriz de adjacências e de predecessores.

            while (s != null) {
//              Na primeira linha, as matrizes são inicializadas de acordo com o número de vértices do grafo (n).
                if (inputPosition == 1) {
                    int n = parseInt(s);
                    graph = new int[n][n];
                    path = new int[n][n];

//              Na segunda linha, os pesos são adicionados.
//              Os pesos são comparados com o valor inicial do diâmetro para casos em que eles já são mínimos.
                } else if (inputPosition > 2) {
                    int u = parseInt(s) - 1;
                    int v = parseInt(io.next()) - 1;
                    int w = parseInt(io.next());
                    graph[u][v] = w;
                    graph[v][u] = w;
                    if (graph[u][v] > diameter) {
                        diameter = graph[u][v];
                        diameterU = u;
                        diameterV = v;
                    }
                }

                inputPosition++;
                s = io.next();
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
//          O valor do diâmetro e os vértices diametrais são atualizados e a matriz de predecessores é preenchida simultaneamente.
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

//          Escrita do arquivo de saída.

            io.write("" + diameter);
            io.newLine();
            io.write((diameterU+1) + " " + (diameterV+1));
            io.newLine();
            io.write("" + diameterSize);
            io.newLine();
            io.write(diameterPath);
            io.flush();

        } catch (Error e) {
            e.printStackTrace();
        }
    }

    public static class IO {
        String [] line;
        int i;
        InputStreamReader ir;
        BufferedReader in;
        OutputStreamWriter or;
        BufferedWriter out;
        IO(){
            ir = new InputStreamReader(System.in);
            in = new BufferedReader(ir);
            or = new OutputStreamWriter(System.out);
            out = new BufferedWriter(or);
        }
        String next(){
            try{
                if (line == null || i == line.length){
                    line = in.readLine().split(" ");
                    i = 0;
                }
                return line[i++];
            }catch (Exception e){ return null; }
        }
        Integer nextInt(){
            return Integer.valueOf(next());
        }
        Long nextLong(){
            return Long.valueOf(next());
        }
        <T> void write(T t) {
            try{
                out.append(String.valueOf(t));
            }catch (Exception e){}
        }
        public void flush(){
            try{
                out.flush();
            }catch(IOException e){}
        }

        public void newLine() {
            try{
                out.append(String.valueOf("\n"));
            }catch (Exception e){}
        }
    }
}
