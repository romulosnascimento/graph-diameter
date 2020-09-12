import java.io.*;
import java.util.*;

import static java.lang.Integer.parseInt;

class Main {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new InputStreamReader(System.in));
            System.out.println("Digite o caminho para o arquivo de entrada: ");
            String input = scanner.nextLine();

            File inputFile = new File(input);
            Scanner myReader = null;
            myReader = new Scanner(inputFile);
            boolean isFirstLine = true;
            int graph[][] = new int[0][0];
            int path[][] = new int[0][0];

            int diameter = 0;
            int diameterU = 0;
            int diameterV = 0;
            int diameterSize = 0;
            String diameterPath = "";

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (isFirstLine) {
                    String[] line1 = data.split(" ");
                    int n = parseInt(line1[0]);
                    int m = parseInt(line1[1]);
                    graph = new int[n][n];
                    path = new int[n][n];
                    isFirstLine = false;
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

            for (int u = 0; u < graph.length; u++) {
                for (int v = 0; v < graph.length; v++) {
                    if (u == v) graph[v][v] = 0;
                    else if (graph[u][v] == 0) graph[u][v] = 300;
                    path[v][u] = v;
                    path[u][v] = u;
                }
            }

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

            System.out.println("OUTPUT");
            System.out.println(diameter);
            System.out.println((diameterU+1) + " " + (diameterV+1));

            int du = diameterU;
            int dv = diameterV;

            diameterPath = "" + (dv+1);
            diameterSize++;

            while (dv != diameterU) {
                dv = path[du][dv];
                diameterPath = (dv+1) + " "+ diameterPath;
                diameterSize++;
            }

            System.out.println(diameterSize);
            System.out.println(diameterPath);

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
            System.out.println("Resultado escrito em output.txt");

            if(bw!=null) bw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
