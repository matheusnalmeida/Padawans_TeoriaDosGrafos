/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grafos_Package;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author Matheus Nunes
 */
public class GrafoOrientado extends Grafo {
      
    public GrafoOrientado() {
        super();
    }

    @Override
    public boolean adicionaAresta(String identificador1, String identificador2, int peso) {
        //If para checar se ambos os vertices existem no grafo e se o peso possue um valor valido
        if ((super.validaVertice(identificador1)) && (super.validaVertice(identificador2)) && (super.validaPeso(peso))) {
            Integer pesoAresta = peso;
            int posicaoDoVertice1 = super.posicaoDoVertice(identificador1);
            int posicaoDoVertice2 = super.posicaoDoVertice(identificador2);
            super.matrizDeAdjacencia.get(posicaoDoVertice1).set(posicaoDoVertice2, pesoAresta);
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected boolean removeAresta(String identificador1, String identificador2) {
        if ((super.validaVertice(identificador1)) && (super.validaVertice(identificador2))) {
            int posicaoDoVertice1 = super.posicaoDoVertice(identificador1);
            int posicaoDoVertice2 = super.posicaoDoVertice(identificador2);
            //Verificando se a aresta realmente existe
            if (super.validaAresta(posicaoDoVertice1, posicaoDoVertice2)) {
                super.matrizDeAdjacencia.get(posicaoDoVertice1).set(posicaoDoVertice2, 0);
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    //Metodo para verificar se o grafo orientado é regular ou não, sendo regular quando todos os vertices do grafo possuem o mesmo grau
    @Override
    public boolean ehRegular() {
        /*
        -Na primeira repeticao para verificar o grau do primeiro vertice, sera armazenado o valor do mesmo na variavel grauPrimeiroElemento.
        -Apos a primeira repeticao, o valor armazenado na variavel grauPrimeiroElemento sera comparado com o grau de cada um dos outros elementos, que 
        sera salvo na variavel grauAtual apos cada repeticao, sendo que caso apos a repeticao o grau do vertice for diferente do inicial, a repeticao
        sera encerrado e sera retornada a informacao de que o grafo n e regular.
         */
        int grauPrimeiroElemento = -1;
        int grauAtual = 0;
        for (int i = 0; i < this.matrizDeAdjacencia.size(); i++) {
            for (int j = 0; j < this.matrizDeAdjacencia.get(i).size(); j++) {
                //A condicao i != j do if, ira representar um laço no grafo, sendo que caso o mesmo seja encontrado o programa ira ignoralo
                if (this.matrizDeAdjacencia.get(j).get(i) != 0 && i != j) {
                    grauAtual++;
                }
            }
            if (grauPrimeiroElemento == -1) {
                grauPrimeiroElemento = grauAtual;
            } else if (grauPrimeiroElemento != grauAtual) {
                return false;
            }
            grauAtual = 0;
        }
        return true;
    }

    //Descobrir se o grafo e fracamente conexo ,ou seja, se todos os vertices sao adjacentes entre si
    @Override
    public String ehConexo() {
        StringBuilder construtorString = new StringBuilder();
        /*
        Foi utilizada a busca em largura para descobrir se o grafo e fracamente conexo e para descobrir quantos 
        componentes fracamente conexos o mesmo possui
         */
        ArrayList<Integer> vetorDeExplorados = new ArrayList<>();
        Queue<Integer> fila = new LinkedList<>();
        Boolean[] visitados = new Boolean[this.matrizDeAdjacencia.size()];
        int contadorDeElementosConexos = 0;
        int verticeAtual;
        //Iniciando vetor de visitados com false
        for (int i = 0; i < visitados.length; i++) {
            visitados[i] = false;
        }

        while (vetorDeExplorados.size() != this.matrizDeAdjacencia.size()) {
            verticeAtual = this.getVerticeNaoExplorado(vetorDeExplorados);
            visitados[verticeAtual] = true;
            fila.add(verticeAtual);
            contadorDeElementosConexos++;
            while (!fila.isEmpty()) {
                verticeAtual = fila.poll();
                vetorDeExplorados.add(verticeAtual);
                //Retornando os adjacente e inserindo na fila
                for (int i = 0; i < this.matrizDeAdjacencia.get(verticeAtual).size(); i++) {
                    //Nao importa o direcionamento das arestas para fracamente conexo em digrafos
                    if ((this.matrizDeAdjacencia.get(verticeAtual).get(i) != 0 || this.matrizDeAdjacencia.get(i).get(verticeAtual) != 0)
                            && (visitados[i] == false)) {
                        visitados[i] = true;
                        fila.add(i);
                    }
                }
            }
        }
        if (contadorDeElementosConexos == 1) {
            construtorString.append("O grafo eh conexo!");
            return construtorString.toString();
        } else {
            construtorString.append("O grafo nao e conexo e possui ").append(contadorDeElementosConexos).append(" elementos conexos");
            return construtorString.toString();
        }
    }

    public String ordenacaoTopologica() {
        //StringBuilder para armazenar as informacoes
        StringBuilder construtor = new StringBuilder();
        //Vetor para armazenar o grau de entrada de cada vertice baseado nas posicoes do vetor
        int[] vetorDeGraus = new int[this.matrizDeAdjacencia.size()];
        //Vetor par armazenar os vertices de acordo com sua ordem de dependencia
        int[] vetorDeVerticeDependencias = new int[this.matrizDeAdjacencia.size()];

        //Inicialmente sera contado o grau de entrada de cada vertice
        for (int i = 0; i < this.matrizDeAdjacencia.size(); i++) {
            for (int j = 0; j < this.matrizDeAdjacencia.get(i).size(); j++) {
                if (this.matrizDeAdjacencia.get(i).get(j) != 0) {
                    vetorDeGraus[j]++;
                }
            }
        }
        /*
        Entao sera iniciada a ordenacao topologica
        É importante ressaltar que a ordenacao topologica ira pssar por todos os vertices ou ate que aconteca de nao existir vertices
        com grau 0 no meio da busca
         */
        for (int i = 0; i < this.matrizDeAdjacencia.size(); i++) {
            int verticeAtual = -1;

            //Retornando Primeiro Vertice com grau nulo
            for (int j = 0; j < vetorDeGraus.length; j++) {
                if (vetorDeGraus[j] == 0) {
                    verticeAtual = j;
                    break;
                }
            }
            /*
            Caso o vertice atual tenha valor -1, isso significa que nao a vertice de grau nulo a ser analisado, podendo assim a ordenacao
            ser finalizada
             */
            if (verticeAtual == -1) {
                return "Impossivel ser realizada a ordenacao topologica";
            }

            //Entao, caso exitsa um vertice com grau 0, o mesmo recebera valor -1 e os seus adjacentes terao o grau diminuido em 1S
            ArrayList<Integer> adjacentes = this.getAdjacentes(verticeAtual);
            vetorDeGraus[verticeAtual] = -1;
            for (int j = 0; j < adjacentes.size(); j++) {
                vetorDeGraus[adjacentes.get(j)]--;
            }
            vetorDeVerticeDependencias[i] = verticeAtual;
        }

        //Passando as informacoes do vetor para o StringBuilder
        for (int i = 0; i < vetorDeVerticeDependencias.length; i++) {
            if (i == vetorDeVerticeDependencias.length - 1) {
                construtor.append(super.identificadoresVertices.get(vetorDeVerticeDependencias[i]));
                break;
            }
            construtor.append(super.identificadoresVertices.get(vetorDeVerticeDependencias[i])).append(" => ");
        }
        return construtor.toString();
    }
    
    //Metodo que ira retornar se o grafo é ou nao fortemente conexo, sendo que caso o mesmo nao seja, sera retornado o seus componentes fortemente conexos
    public String fortementeConexo() {
        //StringBuilder para armazenarmos as informacoes que irao ser retornadas
        StringBuilder construtor = new StringBuilder();
        //Invertendo Matriz        
        int[][] matrizInversa = new int[this.matrizDeAdjacencia.size()][this.matrizDeAdjacencia.size()];
        //Salvando a transposta da matriz de adjacencia na variavel matrizInversa
        for (int i = 0; i < this.matrizDeAdjacencia.size(); i++) {
            for (int j = 0; j < this.matrizDeAdjacencia.get(i).size(); j++) {
                matrizInversa[i][j] = this.matrizDeAdjacencia.get(j).get(i);
            }
        }

        //Criando vetor para armazenar os vertices de acordo com o valor de pos ordem
        ArrayList<Integer> vetorDeVerticesPosOrdem = this.BuscaEmProfundidadeParaPosOrdem();

        //Matriz para armazenar os componente conexos do grafo
        ArrayList<ArrayList<Integer>> matrizDeComponentesFortementeConexos = new ArrayList<>();

        /*
        Realizando busca em profundidade na matriz com ordem inversa e 
        armazenando os componentes fortemente conexos do grafo na matriz de componentes fortemente conexos
         */
        ArrayList<Integer> vetorDeExplorados = new ArrayList<>();
        Stack<Integer> pilha = new Stack<>();
        Boolean[] visitados = new Boolean[this.matrizDeAdjacencia.size()];
        int vertice;
        for (int i = 0; i < visitados.length; i++) {
            visitados[i] = false;
        }
        while (super.contemFalse(visitados)) {
            vertice = this.retornaVerticeNaoVisitadoComValorDePosOrdem(visitados, vetorDeVerticesPosOrdem);
            visitados[vertice] = true;
            pilha.push(vertice);
            while (!pilha.empty()) {
                for (int i = 0; i < matrizInversa[pilha.peek()].length; i++) {
                    if ((matrizInversa[pilha.peek()][i] != 0 && visitados[i] != true)) {
                        pilha.push(i);
                        visitados[i] = true;
                        i = 0;
                    }
                }
                vetorDeExplorados.add(pilha.pop());
            }
            matrizDeComponentesFortementeConexos.add(vetorDeExplorados);
            vetorDeExplorados = new ArrayList<>();
        }
        if (matrizDeComponentesFortementeConexos.size() == 1) {
            return "O grafo é fortemente conexo";
        } else {
            construtor.append("Abaixo estao os componentes fortemente conexos do grafo").append("\n");
            for(int i = 0; i < matrizDeComponentesFortementeConexos.size();i++){
                construtor.append("Componente ").append(i+1).append(": ");
                for (int j = 0; j < matrizDeComponentesFortementeConexos.get(i).size(); j++) {
                    construtor.append(super.identificadoresVertices.get(matrizDeComponentesFortementeConexos.get(i).get(j))).append(" ");
                }
                construtor.append("\n");
            }
        }
        return construtor.toString();
    }

    //Metodo para pegarmos o valor de pos ordem de cada vertice
    private ArrayList<Integer> BuscaEmProfundidadeParaPosOrdem() {
        ArrayList<Integer> vetorDeExplorados = new ArrayList<>();
        Stack<Integer> pilha = new Stack<>();
        Boolean[] visitados = new Boolean[this.matrizDeAdjacencia.size()];
        int vertice;
        for (int i = 0; i < visitados.length; i++) {
            visitados[i] = false;
        }
        while (super.contemFalse(visitados)) {
            vertice = super.getVerticeNaoVisitado(visitados);
            visitados[vertice] = true;
            pilha.push(vertice);
            while (!pilha.empty()) {
                for (int i = 0; i < this.matrizDeAdjacencia.get(pilha.peek()).size(); i++) {
                    if ((this.matrizDeAdjacencia.get(pilha.peek()).get(i) != 0 && visitados[i] != true)) {
                        pilha.push(i);
                        visitados[i] = true;
                        i = 0;
                    }
                }
                vetorDeExplorados.add(pilha.pop());
            }
        }
        
        //Revertendo vetor para que o mesmo fique ordenado do maior para o menor de pos ordem
        Collections.reverse(vetorDeExplorados);
        return vetorDeExplorados;
    }
    
    /*
    Metodo responsavel por retornar o primeiro vertice nao visitado encontrado,
    levando em consideracao o valor de pos ordem
     */
    private int retornaVerticeNaoVisitadoComValorDePosOrdem(Boolean[] visitados, ArrayList<Integer> vetorDeVerticesPosOrdem) {
        for (int i = 0; i < visitados.length; i++) {
            if (visitados[vetorDeVerticesPosOrdem.get(i)].equals(false)) {
                return vetorDeVerticesPosOrdem.get(i);
            }
        }
        return -1;
    }
    
    

}
