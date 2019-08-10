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

    public GrafoNaoOrientado() {
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
            super.matrizDeAdjacencia.get(posicaoDoVertice2).set(posicaoDoVertice1, pesoAresta);
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
                super.matrizDeAdjacencia.get(posicaoDoVertice2).set(posicaoDoVertice1, 0);
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

}
