/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grafos_Package;

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
        int quantidadeDeVerrticesResposta;
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
                System.out.println("1 - Cadastro De Novo Grafo \n2 - Adicionar Vertice \n3 - Adicionar Aresta \n4 - Printar Grafo \n0 - Finaliza o programa");
                respostaMenuPrincipal = scan.nextInt();
                switch (respostaMenuPrincipal) {
                    case (0):
                        aplicacao = 0;
                    case (1):
                        grafoAtual = null;
                        break;
                    case(2):
                        System.out.println("Digite o nome do vertice a ser adicionado:");
                        String identificador = scan.next();
                        boolean validadorVertice = grafoAtual.adicionaVertice(identificador);
                        if (!validadorVertice){
                            System.out.println("Vertice ja existente no grafo!");
                        }
                        break;
                    case (3):
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
                    case (4):
                        grafoAtual.printarGrafo();
                        break;
                    default:
                        System.out.println("Opcao de Menu Invalida!");
                }
            }
        }
    }
}
