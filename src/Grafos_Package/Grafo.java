/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grafos_Package;

/**
 *
 * @author Matheus Nunes
 */
public abstract class Grafo {

    int[][] matrizDeAdjacencia;

    public Grafo(int numeroDeVertices) {
        this.matrizDeAdjacencia = new int[numeroDeVertices][numeroDeVertices];
    }

    public void printarGrafo() {
        System.out.println("Matriz De Adjacencia");
        for (int[] linha : this.matrizDeAdjacencia) {
            for (int y : linha) {
                System.out.print(y + " ");
            }
            System.out.println();
        }
    }

    public abstract boolean adicionaAresta(int vertice1, int vertice2, int peso);

    //Verifica se o vertice pertence a um intervalo valido, retornando true caso pertenca e false caso nao pertenca
    protected boolean validaVertice(int vertice) {
        return ((0 <= vertice) && (vertice < this.matrizDeAdjacencia.length));
    }

    //Verifica se o peso informado possui um valor valido
    protected boolean validaPeso(int peso) {
        return peso > 0;
    }

}
