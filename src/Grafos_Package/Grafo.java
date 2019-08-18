/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grafos_Package;

import java.util.ArrayList;

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

    //Retorna uma lista de vertices adjacentes ao vertice passado como parametro
    public ArrayList<String> getAdjacentes(String identificadorVertice) {
        ArrayList<String> listaDeAdjacentes = new ArrayList<>();
        //Será lancada uma excessão do tipo nullpointer caso o vertice nao exista
        if (!this.validaVertice(identificadorVertice)) {
            throw new NullPointerException("Vertice nao existente no Grafo");
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

    /*
    A diferenca do metodo ehRegular na classe GrafoOrientado e na classe GrafoNaoOrientado, é simplesmente o fato de que na nao orientado so tera que ser
    checado aresta de uma unica posicao, já no orientado tera que ser checada a aresta especificamente na posicao de entrada do vertice
     */
    public abstract boolean ehRegular();

    //Metodo pare verificar se o grafo eh completo, ou seja, se todos os vertices serao adjacentes a todos os outros
    public boolean ehCompleto() {
        /*
        Neste metodo, o grau de cada vertice sera salvo na variavel grauAtual e sera comparado se o valor do grau de cada vertice é ou nao igual 
        a quantidade de vertices do grafo - 1, sendo o menos um sera usado pra ignorar o proprio vertice.
         */
        int grauAtual = 0;
        for (int i = 0; i < this.matrizDeAdjacencia.size(); i++) {
            for (int j = 0; j < this.matrizDeAdjacencia.get(i).size(); j++) {
                //A condicao i != j do if, ira representar um laço no grafo, sendo que caso o mesmo seja encontrado o programa ira ignoralo
                if (this.matrizDeAdjacencia.get(i).get(j) != 0 && i != j) {
                    grauAtual++;
                }
            }
            if (grauAtual != (this.matrizDeAdjacencia.size() - 1)) {
                return false;
            }
            grauAtual = 0;
        }
        return true;
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
