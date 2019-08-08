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
public class GrafoNaoOrientado extends Grafo {

    public GrafoNaoOrientado(int numeroDeVertices) {
        super(numeroDeVertices);
    }

    @Override
    public boolean adicionaAresta(int vertice1, int vertice2, int peso) {
        //If para checar se ambos os vertices e o peso possuem um valor valido
        if ((super.validaVertice(vertice1)) && (super.validaVertice(vertice2)) && (super.validaPeso(peso))) {
            super.matrizDeAdjacencia[vertice1][vertice2] = peso;
            super.matrizDeAdjacencia[vertice2][vertice1] = peso;
            return true;
        }else{
            return false;
        }
    }

}
