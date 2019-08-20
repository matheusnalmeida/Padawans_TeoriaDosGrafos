/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grafos_Package;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 *
 * @author Matheus Nunes
 */
public abstract class Grafo {

    protected ArrayList<String> identificadoresVertices;
    protected ArrayList<ArrayList<Integer>> matrizDeAdjacencia;

    public Grafo() {
        this.matrizDeAdjacencia = new ArrayList<>();
        this.identificadoresVertices = new ArrayList<>();
    }

    public void printarGrafo() {
        System.out.println("Matriz De Adjacencia");
        for (ArrayList<Integer> vetorDeArestas : this.matrizDeAdjacencia) {
            for (int peso : vetorDeArestas) {
                System.out.print(peso + " ");
            }
            System.out.println();
        }
    }

    protected boolean adicionaVertice(String identificador) {
        //Verifica se ja existe um vertice com o identificador especificado. Caso nao exista, o mesmo podera ser adicionado
        if (!this.validaVertice(identificador)) {
            this.identificadoresVertices.add(identificador);
            /*
            Este for inicial e utilizado para adicionarmos o valor 0 no final de cada vetor da matriz de adjacencia, assim permitindo 
            que posteriormente seja possivel a adicao de uma aresta para o novo vertice a ser inserido.
             */
            for (int i = 0; i < this.matrizDeAdjacencia.size(); i++) {
                this.matrizDeAdjacencia.get(i).add(0);
            }
            /*Posteriormente Sera Adicionado na matriz de adjacencia, o novo vetor que ira representar as arestas do novo vertice,
            sendo estas, previamente iniciadas com peso 0*/
            ArrayList<Integer> novoVertice = new ArrayList<Integer>();
            int quantidadeTotalDeVertices = this.matrizDeAdjacencia.size();
            for (int i = 0; i < quantidadeTotalDeVertices + 1; i++) {
                novoVertice.add(0);
            }
            this.matrizDeAdjacencia.add(novoVertice);
            return true;
        }
        return false;
    }

    protected boolean removeVertice(String identificador) {
        if (this.validaVertice(identificador)) {
            int posicaoDoVerticeASerRemovido = this.posicaoDoVertice(identificador);
            this.identificadoresVertices.remove(posicaoDoVerticeASerRemovido);
            /*
            Este for sera utilizado para removermos todas as posicoes dos vertices da matriz, 
            que representem uma possivel ligacao ao vertice que venha a ser removido
             */
            for (int i = 0; i < this.matrizDeAdjacencia.size(); i++) {
                this.matrizDeAdjacencia.get(i).remove(posicaoDoVerticeASerRemovido);
            }
            //Posteriormente sera removido da matriz de adjacencia o vetor que representa o vertice a ser removido
            this.matrizDeAdjacencia.remove(posicaoDoVerticeASerRemovido);
            return true;
        } else {
            return false;
        }
    }

    protected abstract boolean adicionaAresta(String identificador1, String identificador2, int peso);

    protected abstract boolean removeAresta(String identificador1, String identificador2);

    //Retorna uma lista de vertices adjacentes ao passar o identificador do vertice como parametro
    public ArrayList<String> getAdjacentes(String identificadorVertice) {
        ArrayList<String> listaDeAdjacentes = new ArrayList<>();
        //Será lancada uma excessão do tipo nullpointer caso o vertice nao exista
        if (!this.validaVertice(identificadorVertice)) {
            throw new IllegalArgumentException("Vertice nao existente no Grafo");
        }
        int vertice = this.posicaoDoVertice(identificadorVertice);
        for (int i = 0; i < this.matrizDeAdjacencia.get(vertice).size(); i++) {
            //O if (vertice == i) ira representar um laço no grafo, sendo que caso o mesmo seja encontrado o programa ira ignoralo
            if (vertice == i) {
                continue;
            }
            if (this.matrizDeAdjacencia.get(vertice).get(i) != 0) {
                listaDeAdjacentes.add(this.identificadoresVertices.get(i));
            }
        }
        return listaDeAdjacentes;
    }

    //Retorna uma lista de vertices adjacentes ao passar a posicao do vertice na matriz como parametro 
    protected ArrayList<Integer> getAdjacentes(int posicaoDoVerticeMatriz) {
        ArrayList<Integer> listaDeAdjacentes = new ArrayList<>();
        //Será lancada uma excessão do tipo IllegalArgumentException caso a posicao na matriz informada seja invalida
        if (0 <= posicaoDoVerticeMatriz && posicaoDoVerticeMatriz < this.matrizDeAdjacencia.size()) {
            for (int i = 0; i < this.matrizDeAdjacencia.get(posicaoDoVerticeMatriz).size(); i++) {
                //O if (vertice == i) ira representar um laço no grafo, sendo que caso o mesmo seja encontrado, o programa ira ignoralo
                if (posicaoDoVerticeMatriz == i) {
                    continue;
                }
                if (this.matrizDeAdjacencia.get(posicaoDoVerticeMatriz).get(i) != 0) {
                    listaDeAdjacentes.add(i);
                }
            }
            return listaDeAdjacentes;
        }
        throw new IllegalArgumentException("Posicao Invalida!");
    }

    /*
    A diferenca do metodo ehRegular na classe GrafoOrientado e na classe GrafoNaoOrientado, é simplesmente o fato de que na nao orientado so tera que 
    ser checada a aresta de uma unica posicao, já no orientado tera que ser checada a aresta especificamente na posicao de entrada no vertice, ou seja, 
    grau de entrada.
     */
    public abstract boolean ehRegular();

    //Metodo pare verificar se o grafo eh completo, ou seja, se todos os vertices serao adjacentes a todos os outros
    public boolean ehCompleto() {
        /*
        Neste metodo, a quatidade de vertices adjacentes do vertice atual serao salvo na variavel verticeAdjacente e sera comparado se a quantidade 
        de adjacentes de cada vertice é ou nao igual a quantidade de vertices do grafo - 1, sendo que o menos um, sera usado para ignorar o proprio 
        vertice.
         */
        int verticeAdjacente = 0;
        for (int i = 0; i < this.matrizDeAdjacencia.size(); i++) {
            verticeAdjacente = this.getAdjacentes(i).size();
            if (verticeAdjacente != (this.matrizDeAdjacencia.size() - 1)) {
                return false;
            }
        }
        return true;
    }

    /*
    A diferenca do metodo ehConexo na classe grafo nao orientado e na classe grafo orientado, e simplesmente o fato de que na nao orientado, so sera ne-
    cessario checar as arestas a partir de um dos vertices da mesma, ja nos orientados sera checado se entre dois vertices, existe uma aresta ou indo
    ou voltando, tendo em conta que o direcionamento das arestas nao importa para sabermos se o digrafo e fracamente conexo.
     */
    public abstract String ehConexo();

    //Dijkstra para calcular a menos distancia de um vertice para todos os outros
    public String Dijkstra(String identificadorVerticeOrigem) {
        if (!this.validaVertice(identificadorVerticeOrigem)) {
            return "Vertice Inexistente no grafo";
        }
        int verticeOrigem = this.posicaoDoVertice(identificadorVerticeOrigem);
        int[] vetorDeDistancias = new int[this.matrizDeAdjacencia.size()];
        Boolean[] visitados = new Boolean[this.matrizDeAdjacencia.size()];
        int[] path = new int[this.matrizDeAdjacencia.size()];
        int verticeAtual = verticeOrigem;

        for (int i = 0; i < path.length; i++) {
            path[i] = -1;
        }

        //Iniciando vetor de distancias
        for (int i = 0; i < vetorDeDistancias.length; i++) {
            if (i == verticeOrigem) {
                vetorDeDistancias[i] = 0;
                continue;
            }
            vetorDeDistancias[i] = Integer.MAX_VALUE;
        }

        //Iniciando vetor de visitados
        for (int i = 0; i < visitados.length; i++) {
            visitados[i] = false;
        }

        //Quando o vetor de visitados nao possuir mais false, isso ira significar que nos ja teremos vasculhado todos os possiveis caminhos a partir de todos os vertices
        while (this.contemFalse(visitados)) {
            visitados[verticeAtual] = true;

            //Primeiro for responsavel por atualizar os pesos de acordo com a menor distancia no vetor de distancias
            for (int i = 0; i < this.matrizDeAdjacencia.get(verticeAtual).size(); i++) {
                //Ignora o laco
                if (i == verticeAtual) {
                    continue;
                }
                //Checa se um dado vertice ainda nao visitado e adjacente ao vertice atual, possui uma distancia ate ele menor do que a ja existente
                if ((visitados[i] == false) && (this.matrizDeAdjacencia.get(verticeAtual).get(i) != 0) && (vetorDeDistancias[i] > vetorDeDistancias[verticeAtual] + this.matrizDeAdjacencia.get(verticeAtual).get(i))) {
                    vetorDeDistancias[i] = vetorDeDistancias[verticeAtual] + this.matrizDeAdjacencia.get(verticeAtual).get(i);
                    path[i] = verticeAtual;
                }
            }

            for (int i = 0; i < vetorDeDistancias.length; i++) {
                if (visitados[i] == false) {
                    verticeAtual = i;
                    break;
                }
            }
            //Segundo for responsavel por verificar qual o vertice que ainda nao foi visitado e que possui o menor peso entre os vertices nao visitados
            for (int i = verticeAtual; i < vetorDeDistancias.length; i++) {
                if (visitados[i] == false && vetorDeDistancias[i] < vetorDeDistancias[verticeAtual]) {
                    verticeAtual = i;
                }
            }
        }
        return this.tracarCaminhoParatodosOsVertices(vetorDeDistancias, path, verticeOrigem);
    }

    //Dijkstra para calcular a menos distancia de um vertice para outro
    public String Dijkstra(String identificadorVerticeOrigem, String identificadorVerticeDestino) {
        if (!this.validaVertice(identificadorVerticeOrigem) || !this.validaVertice(identificadorVerticeDestino)) {
            return "Vertice Inexistente no grafo";
        }
        if (identificadorVerticeOrigem.equals(identificadorVerticeDestino)){
            return "Os vertices informados são iguais";
        }
        
        int verticeOrigem = this.posicaoDoVertice(identificadorVerticeOrigem);
        int verticeDestino = this.posicaoDoVertice(identificadorVerticeDestino);
        int[] vetorDeDistancias = new int[this.matrizDeAdjacencia.size()];
        Boolean[] visitados = new Boolean[this.matrizDeAdjacencia.size()];
        int[] path = new int[this.matrizDeAdjacencia.size()];
        int verticeAtual = verticeOrigem;

        for (int i = 0; i < path.length; i++) {
            path[i] = -1;
        }

        //Iniciando vetor de distancias
        for (int i = 0; i < vetorDeDistancias.length; i++) {
            if (i == verticeOrigem) {
                vetorDeDistancias[i] = 0;
                continue;
            }
            vetorDeDistancias[i] = Integer.MAX_VALUE;
        }

        //Iniciando vetor de visitados
        for (int i = 0; i < visitados.length; i++) {
            visitados[i] = false;
        }

        //Quando o vetor de visitados nao possuir mais false, isso ira significar que nos ja teremos vasculhado todos os possiveis caminhos a partir de todos os vertices
        while (this.contemFalse(visitados)) {
            visitados[verticeAtual] = true;

            //Quando ja tiver encontrado o vertice de destino
            if (verticeAtual == verticeDestino) {
                break;
            }

            //Primeiro for responsavel por atualizar os pesos de acordo com a menor distancia no vetor de distancias
            for (int i = 0; i < this.matrizDeAdjacencia.get(verticeAtual).size(); i++) {
                //Ignora o laco
                if (i == verticeAtual) {
                    continue;
                }
                //Checa se um dado vertice ainda nao visitado e adjacente ao vertice atual, possui uma distancia ate ele menor do que a ja existente
                if ((visitados[i] == false) && (this.matrizDeAdjacencia.get(verticeAtual).get(i) != 0) && (vetorDeDistancias[i] > vetorDeDistancias[verticeAtual] + this.matrizDeAdjacencia.get(verticeAtual).get(i))) {
                    vetorDeDistancias[i] = vetorDeDistancias[verticeAtual] + this.matrizDeAdjacencia.get(verticeAtual).get(i);
                    path[i] = verticeAtual;
                }
            }

            for (int i = 0; i < vetorDeDistancias.length; i++) {
                if (visitados[i] == false) {
                    verticeAtual = i;
                    break;
                }
            }
            //Segundo for responsavel por verificar qual o vertice que ainda nao foi visitado e que possui o menor peso entre os vertices nao visitados
            for (int i = verticeAtual; i < vetorDeDistancias.length; i++) {
                if (visitados[i] == false && vetorDeDistancias[i] < vetorDeDistancias[verticeAtual]) {
                    verticeAtual = i;
                }
            }
        }
        
        return this.tracarCaminho(vetorDeDistancias, path, verticeOrigem, verticeDestino);        
    }

    protected String tracarCaminhoParatodosOsVertices(int[] vetorDeDistancias, int[] path, int verticeOrigem) {
        StringBuilder construtorString = new StringBuilder();
        //Salva os caminhos na ordem do ultimo para o de origem
        ArrayList<Integer> caminhosInversos = new ArrayList<>();
        int verticeAtual;

        //Ira verficar se tem caminho de todos os vertices da matriz de adjacencia em relacao ao vertice de origem
        for (int i = 0; i < this.matrizDeAdjacencia.size(); i++) {
            //Verificando se o vertice atual a ser tracado o caminho ate o vertice de origem,e o proprio vertice do de origem
            if (i == verticeOrigem) {
                continue;
            }
            /*
            Ira comecar a adicionar os vertice no vetor de tras pra frente, posteriormente colocando-os na ordem certa e adicionando
            no StringBuilder
             */
            caminhosInversos.add(i);
            verticeAtual = i;
            while (true) {
                //Verificando se o vertice atual nao possui antecessores, ou seja, nao tera como chegar no mesmo
                if (path[verticeAtual] == -1) {
                    construtorString.append("Nao ha caminho do vertice ").append(this.identificadoresVertices.get(verticeOrigem)).append(" até o vertice ").
                            append(this.identificadoresVertices.get(i)).append("\n");
                    break;
                    //Verificando se o vertice atual ainda e diferente do de origem
                } else if (path[verticeAtual] != verticeOrigem) {
                    caminhosInversos.add(path[verticeAtual]);
                    verticeAtual = path[verticeAtual];
                } else {
                    caminhosInversos.add(verticeOrigem);
                    break;
                }
            }
            /*
            Checando se o ultimo vertice do vetor é o vertice de origem, pois caso não seja, nao sera inserido o caminho no striong builder, per-
            manecendo somente a mensagem de caminho inexistente
             */
            if (caminhosInversos.get(caminhosInversos.size() - 1) == verticeOrigem) {
                //Colocando a lista na ordem correta e adicionando no StringBuilder
                Collections.reverse(caminhosInversos);
                for (int j = 0; j < caminhosInversos.size(); j++) {
                    if (j == caminhosInversos.size() - 1) {
                        construtorString.append(this.identificadoresVertices.get(caminhosInversos.get(j)));
                        break;
                    }
                    construtorString.append(this.identificadoresVertices.get(caminhosInversos.get(j))).append(" => ");
                }
                construtorString.append("  | Distancia do vertice ").append(this.identificadoresVertices.get(verticeOrigem)).append(" ate o vertice ")
                        .append(this.identificadoresVertices.get(i)).append(" é ").append(vetorDeDistancias[i]).append("\n");
            }
            caminhosInversos.clear();
        }
        return construtorString.toString();
    }
    
    protected String tracarCaminho(int[] vetorDeDistancias, int[] path, int verticeOrigem,int verticeDestino){
        //Formando o caminho do vertice de origem ate o de destino
        StringBuilder construtorString = new StringBuilder();
        //Salva os caminhos na ordem do ultimo para o de origem
        ArrayList<Integer> caminhosInversos = new ArrayList<>();
        int verticeAtual;
        /*
        Ira comecar a adicionar os vertice no vetor de tras pra frente, posteriormente colocando-os na ordem certa e adicionando
        no StringBuilder
         */
        caminhosInversos.add(verticeDestino);
        verticeAtual = verticeDestino;
        while (true) {
            //Verificando se o vertice atual nao possui antecessores, ou seja, nao tera como chegar no mesmo
            if (path[verticeAtual] == -1) {
                return "Nao a caminho entre os vertices especificados";
                //Verificando se ja chegou no vertice de origem
            } else if (path[verticeAtual] == verticeOrigem) {
                caminhosInversos.add(verticeOrigem);
                break;
            }
            verticeAtual = path[verticeAtual];
            caminhosInversos.add(verticeAtual);
        }
        //Colocando a lista na ordem correta e adicionando no StringBuilder
        Collections.reverse(caminhosInversos);
        for (int j = 0; j < caminhosInversos.size(); j++) {
            if (j == caminhosInversos.size() - 1) {
                construtorString.append(this.identificadoresVertices.get(caminhosInversos.get(j)));
                break;
            }
            construtorString.append(this.identificadoresVertices.get(caminhosInversos.get(j))).append(" => ");
        }
        construtorString.append("  | Distancia do vertice ").append(this.identificadoresVertices.get(verticeOrigem)).append(" ate o vertice ")
                .append(this.identificadoresVertices.get(verticeDestino)).append(" é ").append(vetorDeDistancias[verticeDestino]).append("\n");
        return construtorString.toString();
    }

    //Metodo para verificar se existem vertices ainda nao visitados
    private boolean contemFalse(Boolean[] vetorDeVisitados) {
        for (int i = 0; i < vetorDeVisitados.length; i++) {
            if (vetorDeVisitados[i] == false) {
                return true;
            }
        }
        return false;
    }

    //Retorna um determinado vertice que ainda nao foi explorado de acordo com o vetor de explorados passado como parametro
    public int getVerticeNaoExplorado(ArrayList<Integer> vetorDeExplorados) {
        int verticeNaoVisitado = -1;
        for (int i = 0; i < this.matrizDeAdjacencia.size(); i++) {
            if (!vetorDeExplorados.contains(i)) {
                verticeNaoVisitado = i;
                break;
            }
        }
        return verticeNaoVisitado;
    }

    //Verifica se aquele vertice realmente existe no Grafo
    protected boolean validaVertice(String identificadorVertice) {
        return this.identificadoresVertices.contains(identificadorVertice);
    }

    //Verifica se aquela determinada aresta existe no grafo 
    protected boolean validaAresta(int posicaoDoVertice1, int posicaoDoVertice2) {
        return this.matrizDeAdjacencia.get(posicaoDoVertice1).get(posicaoDoVertice2) != 0;
    }

    //Verifica se o peso informado possui um valor valido
    protected boolean validaPeso(int peso) {
        return peso > 0;
    }

    //Metodo responsavel por retornar a posicao do vertice na matriz de adjacencia
    protected int posicaoDoVertice(String identificador) {
        return this.identificadoresVertices.indexOf(identificador);
    }
}
