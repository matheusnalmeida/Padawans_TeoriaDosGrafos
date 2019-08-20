/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grafos_Package;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Matheus Nunes
 */
public class GrafosTeste {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        //A variavel tipoDoGrafoResposta será utilizada para guardar a quantidade de vertices informada pelo usuario.
        //A variavel quantidadeDeVerrticesResposta será utilizada para guardar a quantidade de vertices informada pelo usuario.
        //A variavel respostaMenuPrincipal sera utilizada para guardar a opcao do menu selecionada
        int tipoDoGrafoResposta;
        int respostaMenuPrincipal;

        //A variavel grafoAtual ira representar o grafo que estaram em execucao no momento para ser realizada a analise.
        Grafo grafoAtual = null;
        /*
        A variavel aplicacao sera usada para validar se o programa deve ou nao continuar em execucao. Caso
        a mesma venha a ter valor 0 a aplicacao sera encerrada.
         */
        int aplicacao = 1;

        while (aplicacao == 1) {
            //If para verificar se nenhum grafo foi cadastrado
            if (grafoAtual == null) {
                System.out.println("Informe o tipo do grafo a ser cadastrado. Caso deseje finalizar o programa digite 0");
                System.out.println("1 - Grafo nao Orientado | 2 - Grafo Orientado");
                tipoDoGrafoResposta = scan.nextInt();
                switch (tipoDoGrafoResposta) {
                    case (0):
                        aplicacao = 0;
                        break;
                    case (1):
                        grafoAtual = new GrafoNaoOrientado();
                        break;
                    case (2):
                        grafoAtual = new GrafoOrientado();
                        break;
                    default:
                        System.out.println("Valor Invalido. Voltando para tela de cadastro inicial...");
                        break;
                }
            } else {
                System.out.println("-----------------------------Menu De Metodos----------------------------");
                System.out.println("1 - Cadastro De Novo Grafo \n2 - Adicionar Vertice \n3 - Remove Vertice \n4 - Adicionar Aresta \n5 - Remove Aresta"
                        + "\n6 - Retorna Adjacentes \n7 - Verificar Se o Grafo eh Regular \n8 - Verificar se o grafo eh completo"
                        + "\n9 - Verificar se o grafo eh conexo \n10 - Dikstra de um vertice para todos os outros \n11 - Printar Grafo  "
                        + "\n0 - Finaliza o programa");
                respostaMenuPrincipal = scan.nextInt();
                switch (respostaMenuPrincipal) {
                    case (0):
                        aplicacao = 0;
                    case (1):
                        grafoAtual = null;
                        break;
                    case (2):
                        System.out.println("Digite o nome do vertice a ser adicionado:");
                        String identificadorNovo = scan.next();
                        boolean validadorVerticeAdicionado = grafoAtual.adicionaVertice(identificadorNovo);
                        if (!validadorVerticeAdicionado) {
                            System.out.println("Vertice ja existente no grafo!");
                        }
                        break;
                    case (3):
                        System.out.println("Digite o nome do vertice a ser removido:");
                        String indentificadorVerticeRemovido = scan.next();
                        boolean validadorVerticeRemovido = grafoAtual.removeVertice(indentificadorVerticeRemovido);
                        if (!validadorVerticeRemovido) {
                            System.out.println("Indentificador Invalido!");
                        } else {
                            System.out.println("Vertice Removido com sucesso");
                        }
                        break;
                    case (4):
                        System.out.println("Aresta Saindo Do Vertice: ");
                        String vertice1 = scan.next();
                        System.out.println("Chegando no vertice: ");
                        String vertice2 = scan.next();
                        System.out.println("Informe o peso: ");
                        int peso = scan.nextInt();
                        boolean validadorAresta = grafoAtual.adicionaAresta(vertice1, vertice2, peso);
                        if (!validadorAresta) {
                            System.out.println("Aresta nao adicionada devido a valores invalidos!");
                        }
                        break;
                    case (5):
                        System.out.println("Aresta a ser removida sai do Vertice: ");
                        String vertice1Remove = scan.next();
                        System.out.println("Aresta a ser removida chega no vertice: ");
                        String vertice2Remove = scan.next();
                        boolean validadorArestaRemovida = grafoAtual.removeAresta(vertice1Remove, vertice2Remove);
                        if (!validadorArestaRemovida) {
                            System.out.println("Aresta nao removida devido a valores invalidos!");
                        }
                        break;
                    case (6):
                        System.out.println("Digite o identificador do vertice a ser mostrado os seus adjacentes:");
                        String vericeAdjacente = scan.next();
                        ArrayList<String> vetorDeAdjacentes = grafoAtual.getAdjacentes(vericeAdjacente);
                        if (vetorDeAdjacentes.isEmpty()) {
                            System.out.println("O vertice nao possui adjacentes");
                            break;
                        }
                        System.out.println("Abaixo seguem os adjacentes do vertice informado:");
                        for (int i = 0; i < vetorDeAdjacentes.size(); i++) {
                            System.out.print(vetorDeAdjacentes.get(i) + " | ");
                        }
                        System.out.println();
                        break;
                    case (7):
                        if (grafoAtual.ehRegular()) {
                            System.out.println("O grafo e regular!");
                        } else {
                            System.out.println("O grafo nao e regular!");
                        }
                        break;
                    case (8):
                        if (grafoAtual.ehCompleto()) {
                            System.out.println("O grafo e completo!");
                        } else {
                            System.out.println("O grafo nao e completo!");
                        }
                        break;
                    case (9):
                        System.out.println(grafoAtual.ehConexo());
                        break;
                    case (10):
                        System.out.println("Informe o vertice a ser analisado:");
                        String verticeRespostaDijkstra = scan.next();
                        System.out.println(grafoAtual.Dijkstra(verticeRespostaDijkstra));
                        break;
                    case (11):
                        grafoAtual.printarGrafo();
                        break;
                    default:
                        System.out.println("Opcao de Menu Invalida!");
                }
            }
        }
    }
}
