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
public class GrafoNaoOrientado extends Grafo {

    /*
    Numero de arestas que saem do vertice de origem da busca em profundidade.
    OBS: Esta variavel sera utilizada no metodo de busca de articulacao, sendo necessario iniciar a variavel de forma global para a mesma n ser resetada
     */
    private int arestasDeSaida;

    public GrafoNaoOrientado() {
        super();
        this.arestasDeSaida = 0;
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

    //Metodo para verificar se o grafo nao dirigido é regular ou não, sendo regular quanto todos os vertices do grafo possuem o mesmo grau
    @Override
    public boolean ehRegular() {
        /*
        -Na primeira repeticao para verificar o grau do primeiro verticeAtual, sera armazenado o valor do mesmo na variavel grauPrimeiroElemento.
        -Apos a primeira repeticao, o valor armazenado na variavel grauPrimeiroElemento sera comparado com o grau de cada um dos outros elementos, que 
        sera salvo na variavel grauAtual apos cada repeticao, sendo que caso apos a repeticao o grau do verticeAtual for diferente do inicial, a repeticao
        sera encerrado e sera retornada a informacao de que o grafo n e regular.
         */
        int grauPrimeiroElemento = -1;
        int grauAtual = 0;
        for (int i = 0; i < this.matrizDeAdjacencia.size(); i++) {
            //A variavel grau ira receber a quantidade de vertices adjacentes que o vertice atual possui
            grauAtual = this.getAdjacentes(i).size();
            //Caso grau do primeiro elemento seja -1 isso significa que estamos ainda no primeiro verticeAtual
            if (grauPrimeiroElemento == -1) {
                grauPrimeiroElemento = grauAtual;
            } else if (grauPrimeiroElemento != grauAtual) {
                return false;
            }
            grauAtual = 0;
        }
        return true;
    }

    //Descobrir se o grafo e conexo, ou seja, se todos os vertices sao adjacentes entre si
    @Override
    public String ehConexo() {
        StringBuilder construtorString = new StringBuilder();
        //Foi utilizada a busca em largura para descobrir se o grafo e conexo e para descobrir quantos componentes conexos o mesmo possui
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
                for (int i = 0; i < this.getAdjacentes(verticeAtual).size(); i++) {
                    if (visitados[this.getAdjacentes(verticeAtual).get(i)] == false) {
                        visitados[this.getAdjacentes(verticeAtual).get(i)] = true;
                        fila.add(this.getAdjacentes(verticeAtual).get(i));
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

    //Algoritimo para retornar quais vertices sao articulacoes
    //Ideia utilizada e explicada no seguinte video: https://www.youtube.com/watch?v=aZXi1unBdJA&t=991s
    public String buscaDeArticulacao() {
        //StringBuilder para armazenar as informacoes e mostrar
        StringBuilder construtor = new StringBuilder();
        //Id que cada vertice ira receber. O mesmo representa a ordem que os vertices foram visitados na busca em profundidade
        int id = 0;
        //Vetor para armazenar o id de cada vertice
        int[] idDosVertices = new int[this.matrizDeAdjacencia.size()];
        //Vetor para armazenar o vertice de menor id que cada vertice consegue chegar
        int[] menorId = new int[this.matrizDeAdjacencia.size()];
        //Vetor de visitados
        boolean[] visitados = new boolean[this.matrizDeAdjacencia.size()];
        //Vetor de articulacoes
        boolean[] vetorDeArticulacoes = new boolean[this.matrizDeAdjacencia.size()];

        /*
        O for sera utilizado principalmente com o objetivo de se verificar pontos de articulacoes em grafos que sejam desconexos,
        sendo que nesses casos a busca iria iniciar de nos diferentes, consequentemente tendo nos raiz diferentes
         */
        for (int i = 0; i < this.matrizDeAdjacencia.size(); i++) {
            if (visitados[i] == false) {
                this.buscaEmProfundidadeArticulacao(i, i, -1, visitados, menorId, idDosVertices, vetorDeArticulacoes, id);
                //Verificando se o vertice raiz atual, contem ou nao mais de um filho
                if (arestasDeSaida > 1) {
                    vetorDeArticulacoes[i] = true;
                } else if (arestasDeSaida < 1) {
                    vetorDeArticulacoes[i] = false;
                }
            }
        }
        for (int i = 0; i < vetorDeArticulacoes.length; i++) {
            if (vetorDeArticulacoes[i] == true) {
                construtor.append(super.identificadoresVertices.get(i)).append(" ");
            }
        }
        return construtor.toString();
    }

    //Busca em profundidade feita de forma recursiva para se achar os pontos de articulação
    private void buscaEmProfundidadeArticulacao(int verticeRaiz, int verticeAtual, int verticeAnterior, boolean[] visitados, int[] menorId, int[] idDosVertices, boolean[] vetorDeArticulacoes, int id) {
        if (verticeAnterior == verticeRaiz) {
            arestasDeSaida++;
        }
        visitados[verticeAtual] = true;
        menorId[verticeAtual] = id;
        idDosVertices[verticeAtual] = id;
        id++;

        ArrayList<Integer> adjacentes = this.getAdjacentes(verticeAtual);
        for (int i = 0; i < adjacentes.size(); i++) {
            if (adjacentes.get(i) == verticeAnterior) {
                continue;
            }
            //Realizando a busca atraves de cada adjacente
            if (visitados[adjacentes.get(i)] == false) {
                this.buscaEmProfundidadeArticulacao(verticeRaiz, adjacentes.get(i), verticeAtual, visitados, menorId, idDosVertices, vetorDeArticulacoes, id);
                menorId[verticeAtual] = Math.min(menorId[verticeAtual], menorId[adjacentes.get(i)]);
                //Ponto de articulacao atraves de uma ponte
                if (idDosVertices[verticeAtual] < menorId[adjacentes.get(i)]) {
                    vetorDeArticulacoes[verticeAtual] = true;
                } //Achando articulacoes atraves de um ciclo
                else if (idDosVertices[verticeAtual] == menorId[adjacentes.get(i)]) {
                    vetorDeArticulacoes[verticeAtual] = true;
                }
            } else {
                menorId[verticeAtual] = Math.min(menorId[verticeAtual], idDosVertices[adjacentes.get(i)]);
            }
        }
    }

    //Algoritimo utilizado para identificacao do(s) centro(s) do grafo
    public String centroDoGrafo() {
        if (!this.matrizDeAdjacencia.isEmpty()) {
            StringBuilder construtor = new StringBuilder();
            /*
        A ideia sera inicialmente realizar uma busca em largura partindo de cada vertice para que se identifique 
        o valor de excentricidade de cada vertice. O metodo responsavel por essa funcao sera o retornaExcentricidadeDoVertice.
        OBS: O valor de excentricidade de um vertice, é a distancia ate o vertice mais longe do mesmo.
             */
            int[] vetorDeExcentricidades = new int[this.matrizDeAdjacencia.size()];
            for (int i = 0; i < this.matrizDeAdjacencia.size(); i++) {
                vetorDeExcentricidades[i] = this.retornaExcentricidadeDoVertice(i);
            }

            /*
        Apos termos a excentricidade de cada vertice, somente sera necessario ver qual ou quais deles possui a menor excentricidade
        e retornalo(s). Os vertices serao salvos no vetor de centro de grafos.
             */
            ArrayList<Integer> centrosDoGrafo = new ArrayList<>();
            int menorExcentricidade = Integer.MAX_VALUE;
            for (int i = 0; i < vetorDeExcentricidades.length; i++) {
                if (vetorDeExcentricidades[i] < menorExcentricidade) {
                    centrosDoGrafo.clear();
                    centrosDoGrafo.add(i);
                    menorExcentricidade = vetorDeExcentricidades[i];
                } else if (vetorDeExcentricidades[i] == menorExcentricidade) {
                    centrosDoGrafo.add(i);
                }
            }

            //Formando mensagem de retorno do metodo
            if (centrosDoGrafo.size() > 1) {
                construtor.append("Os centros do grafo sao os vertices:").append("\n");
            } else {
                construtor.append("O centros do grafo é o vertice ");
            }
            for (int i = 0; i < centrosDoGrafo.size(); i++) {
                construtor.append(this.identificadoresVertices.get(centrosDoGrafo.get(i))).append("\n");
            }
            return construtor.toString();
        } else {
            return "O grafo nao possui vertices";
        }
    }

    //Metodo que ira realizar a busca em profundidade e retornar a excentricidade do vertice de inicio
    private int retornaExcentricidadeDoVertice(int verticeDeInicio) {
        Queue<Integer> fila = new LinkedList<>();
        Boolean[] visitados = new Boolean[this.matrizDeAdjacencia.size()];

        //A variavel vetorDeMenoresDistancias ira armazenar a menor distancia de cada vertice para o vertice que se iniciou a busca em largura
        int[] vetorDeMenoresDistancias = new int[this.matrizDeAdjacencia.size()];
        for (int i = 0; i < visitados.length; i++) {
            visitados[i] = false;
        }

        visitados[verticeDeInicio] = true;
        fila.add(verticeDeInicio);
        while (!fila.isEmpty()) {
            int elementoAtual = fila.poll();
            for (int i = 0; i < this.matrizDeAdjacencia.get(elementoAtual).size(); i++) {
                if (this.matrizDeAdjacencia.get(elementoAtual).get(i) != 0 && visitados[i] == false) {
                    visitados[i] = true;
                    fila.add(i);
                    vetorDeMenoresDistancias[i] = vetorDeMenoresDistancias[elementoAtual] + 1;
                }
            }
        }

        //Entao apos a busca sera retornado a maior das menores distancias, que nesse caso sera o valor de excentricidade
        int maiorDistancia = -1;
        for (int i = 0; i < vetorDeMenoresDistancias.length; i++) {
            if (vetorDeMenoresDistancias[i] > maiorDistancia) {
                maiorDistancia = vetorDeMenoresDistancias[i];
            }
        }
        return maiorDistancia;
    }
}
