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

    //Metodo para verificar se o grafo nao dirigido é regular ou não, sendo regular quanto todos os vertices do grafo possuem o mesmo grau
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
                if (this.matrizDeAdjacencia.get(i).get(j) != 0 && this.matrizDeAdjacencia.get(j).get(i) != 0) {
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
}
