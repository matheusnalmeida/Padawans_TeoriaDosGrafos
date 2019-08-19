/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grafos_Package;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

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
}
