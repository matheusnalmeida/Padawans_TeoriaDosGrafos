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
            super.matrizDeAdjacencia.get(super.posicaoDoVertice(identificador1)).set(super.posicaoDoVertice(identificador2),pesoAresta);
            super.matrizDeAdjacencia.get(super.posicaoDoVertice(identificador2)).set(super.posicaoDoVertice(identificador1),pesoAresta);            
            return true;
        }else{
            return false;
        }
    }

}
