# Padawans_TeoriaDosGrafos
Implementação de Grafos - Parte 1

#### Versão do Java
JDK VERSION = java 12.0.1 2019-04-16

#### Descrição
Este é um programa destinado a criação de grafos e teste dos mesmos de acordo com as ferramentas existentes no programa. A execução inicial do programa é feita atraves do arquivo GrafosTeste.java localizado na pasta src, sendo seu uso realizado atraves do console.

#### Forma de Representação
O Grafo foi implementado no formato de matriz de adjacencia.

#### Detalhes do Uso

##### 1) Seleção De Ferramentas
Cada ferramenta demonstrada no console ira possuir um indice de indentificação especifico, e caso o usuario queira selecionar uma ferramenta, o mesmo so necessita digitar e comfirmar um indice referente a ferramenta.

##### 2) Configuração Inicial
Ao ser executado pela primeira vez, o programa ira pedir para que o usuario informe se o grafo será dirigido ou não dirigido, sendo o grafo inicialmente criado sem nenhum vertice ou aresta. Logo após a tela de configuração inicial, será mostrado um menu com todas as ferramentas de interação com o grafo.

#### Ferramentas De Interação e seus indices
##### Dentre as Ferramentas de Interação e seus respectivos indices nos podemos citar:
1 - Cadastro de novo grafo = Permite que o usuario crie um novo grafo limpo(sem arestas e vertices).

2 - Adicionar Vertice = Permite que o usuario adicione um novo vertice ao grafo. Sera requisitado que o mesmo informe o identificador do vertice a ser inserido. O identificador sera a maneira como aquele determinado vertice poderar ser referido pelo usuário em outras partes do programa.

3 - Remover Vertice = Permite que o usuario remova um determinado vertice a partir de seu identificador.

4 - Adicionar Aresta = Permite que o usuario adicione uma aresta, informando os identificadores dos vertices que a mesma estara conectada além de seu peso.

5 - Remove Aresta = Permite que o usuario remova uma aresta informando os identificadores de ambos os vertices que a mesma esta conectada,

6 - Retornar Adjacentes = Permite retornar os adjacentes de um determinado vertice informado.

7 - Verificar Se o Grafo eh Regular = Retorna ao usuario se o Grafo feito é K-Regular(Vertices com o mesmo grau).

8 - Verificar se o grafo eh completo = Retorna ao usuario se o Grafo feito é Completo(Todos os vertices se ligam).

9 - Verificar se o grafo eh conexo = Retorna ao usuario se o Grafo feito é Conexo ou Desconexo.

10 - Dijkstra de um vertice para todos os outros  = Permite o usuario saber o menor caminho de um vertice informado a todos os outros.

11 - Dijkstra de um vertice para outro especifico = Permite o usuario saber o menor caminho entre dois vertices especificados.

12 - Ordenação Topológica = Retorna a ordem dos vértices pelo qual passou a ordenação topológica.

13 - Fortemente conexo = Retorna todos os componentes fortemente conexos.

14 - Busca de articulações = Retorna os vértices que são articulações no grafo.

15 - Retornar Centros do Grafo = Retorna o vértice no centr do grafo.

16 - Retornar arvore geradora minima = Retorna a arvore geradora mínima de um determinado grafo.

17 - Printar Grafo = Mostra ao usuario a matriz adjacencia do Grafo passsado.

0 - Finaliza o Programa = Finaliza a execucao do programa.

## Créditos
Abaixa está os links do responsável pela criação do programa.<br />
-[Erik Danilo](https://github.com/ErikDCAlmeida)<br />
-[Aldo Wanderley](https://github.com/Aldo-1)<br />
-[Matheus Almeida](https://github.com/matheus-nunes-Unit)
