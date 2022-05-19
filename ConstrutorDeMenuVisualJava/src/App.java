//Código feito baseado nos conhecimentos de Java adquiridos em minhas aulas com o professor Liluyoud Lacerda, alguns poucos conhecimentos prévios e tutoriais da internet. Alguns componentes (switch, ArrayList, sleep()) eu aprendi precocemente da internet. Alguns (new, interface, instanciação de métodos) eu não entendi, mas sabia a finalidade, portanto apenas copiei dos tutoriais.

import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.Collections; //Imports

public class App {
    static Scanner leitor = new Scanner(System.in);
    public static void main(String[] args) throws Exception {

        //Impressão de texto inicial (com atraso).
        TimeUnit.SECONDS.sleep(1);
        System.out.println("Bem-vindo ao construtor de menus!");
        TimeUnit.SECONDS.sleep(2);
        System.out.println("Aqui, você vai utilizar comandos numéricos para construir a representação visual de um menu.");
        TimeUnit.SECONDS.sleep(2);

        App appExec = new App(); //Instanciação; não sei como funciona, mas utilizo para poder executar algo não-estático
        appExec.criarComandos(); //de dentro de algo estático.
        //Eu uso a instanciação pois nem todos os métodos utilizados funcionam se forem "static".
    }

    interface AcaoComando { void comando(); }  
    //Pedaço de código copiado da internet, não sei o que significa.    
    //Eu peguei pois queria fazer um array de métodos.

    //Eu aprendi ArrayList por conta própria para poder fazer um "array" mutável.
    public ArrayList<App.AcaoComando> comandosFeitos = new ArrayList<>(); //Este ArrayList armazena os métodos.
    public ArrayList<Integer> indexOrdemTamanho = new ArrayList<>();      //Este ArrayList armazena o comprimento de cada elemento do ArrayList acima. São separados pois .max() não suporta a ordenação de arrays com elementos diferentes.

    int espacoMinimo = 5, maior = 0, saltoEsquerdo = 0, saltoDireito = 1;
    //Instancialção de diversos integers utilizados ao longo do programa.

    public void criarComandos() {

        comandosFeitos.add(new AcaoComando() { public void comando() { LinhaBorda(); }});
        comandosFeitos.add(new AcaoComando() { public void comando() { LinhaGenerica(""); }});
        comandosFeitos.add(new AcaoComando() { public void comando() { LinhaBorda(); }});
        //Inserção dos elementos iniciais dos ArrayLists. Essa parte de "new AcaoComando()" também usei da internet.
        indexOrdemTamanho.add(0,0); indexOrdemTamanho.add(1,0);
        comandos();
    }

    //Comandos principais

    void comandos() { //Lista de comandos possíveis.

        System.out.println("\n\nEscolha um comando:\n");
        System.out.println("1: Inserir linha vazia");
        System.out.println("2: Inserir linha com um componente de texto");
        System.out.println("3: Escolher posição do salto");
        System.out.println("4: Remover linhas");
        System.out.println("5: Definir tamanho das margens laterais");
        System.out.println("6: Visualizar menu");
        System.out.println("7: Finalizar (encerra o programa)\n");

        //Switch de comandos. Aprendi switch por conta própria.
        if (leitor.hasNextInt()) {
            switch (leitor.nextInt()) {
                
                case 0 : { comandoSecreto(); break; }
                case 1 : { linhaVazia();     break; }
                case 2 : { linhaTexto();     break; }
                case 3 : { posiSalto();      break; }
                case 4 : { removerLinhas();  break; }
                case 5 : { tamanhoMargem();  break; }
                case 6 : { visualizarMenu(); break; }
                case 7 : { try { encerrar(); } catch (Exception e) { } break; } //automatizado, não sei usar try/catch.
                default: { leitor.nextLine(); retorno(true); break; }
            }
        } else { leitor.nextLine(); retorno(true); }
    }

    void linhaVazia() { //Insere uma linha vazia depois do índex desejado.

        leitor.nextLine(); //Correção de um erro do nextLine (quando é executado em outro método).

        while (comandosFeitos.size() < Integer.MAX_VALUE) {

            System.out.println("\nEscolha uma linha; você vai adicionar uma linha vazia após ela.\nVocê não pode ultrapassar a borda.\nDigite um valor inválido para retornar.");

            int adicion = 0;
            
            visMenuInterno(); //Abrir uma visualização do menu com valores de índex.

            if (leitor.hasNextInt()) {

                adicion = leitor.nextInt(); 
                if (adicion >= (comandosFeitos.size())) retorno(true); //Inserir linha se o índex é menor que o da borda inferior.
                else {
                
                    comandosFeitos.add(adicion, new AcaoComando() { public void comando() { LinhaGenerica(""); }});
                    indexOrdemTamanho.add(0);
                    //Adicionar a linha nos arrays.

                }

            } else retorno(true);
        }
    }

    void linhaTexto() { //Insere uma linha de texto customizado depois do índex desejado.

        leitor.nextLine();

        while (comandosFeitos.size() < Integer.MAX_VALUE) { //O único limite do tamanho do menu é o limite de integer do Java.

            System.out.println("\nEscolha uma linha; você vai adicionar uma linha com o seu texto após ela. \nVocê não pode ultrapassar a borda.\nDigite um valor inválido para retornar.");

            int adicion = 0;

            visMenuInterno();

            if (leitor.hasNextInt()) {

                adicion = leitor.nextInt(); 
                if (adicion >= (comandosFeitos.size())) retorno(true); //Inserir linha se o índex é menor que o da borda inferior.
                else {
                
                    System.out.println("\nInsira o seu texto. Pode ser qualquer coisa! (exceto uma linha vazia.)");
                    
                    String compon = leitor.next();
                    
                    comandosFeitos.add(adicion, new AcaoComando() { public void comando() { LinhaGenerica(compon); }});
                    indexOrdemTamanho.add(compon.length());
                    maior = Collections.max(indexOrdemTamanho) + (Collections.max(indexOrdemTamanho) % 2);
                    //Adicionar a linha nos arrays, atualizando e adaptando o maior componente.
                        
                }

            } else retorno(true);
        }
    }

    void posiSalto() { //Define a posição do salto de compensação ímpar.

        leitor.nextLine();

        String saltoPos = "null";

        switch (saltoEsquerdo) { //Define a string da posição atual do salto.

            case 0: { saltoPos = "direita"; break; }
            case 1: { saltoPos = "esquerda"; break; }
        }

        System.out.println("\nAqui, você vai definir a posição do salto de ajuste da linha.");
        System.out.println("Este ajuste é feito caso o texto imbutido tiver um número ímpar de caracteres.");
        System.out.printf("Atualmente, o salto está para a %s. Defina a direção desejada:\n", saltoPos);
        System.out.println("1: Esquerda\n2: Direita\n");

        switch (leitor.nextLine()) { //Switch da posição do salto inserido.

            case "1": {System.out.println("Salto definido para a esquerda!\n"); 
                      saltoEsquerdo = 1; saltoDireito = 0; retorno(false); break;}
            case "2": {System.out.println("Salto definido para a direita!\n"); 
                      saltoEsquerdo = 0; saltoDireito = 1; retorno(false); break;}
            default : {retorno(true); break;}
        }
    }

    void removerLinhas() { //Remove linhas do menu.

        leitor.nextLine();

        int remove = 0;
        String plural = "s";

        if (indexOrdemTamanho.size() - 2 == 1) plural = ""; //Define a pluralidade dos textos.
        
        System.out.println("\nQuantas linhas você quer remover?");
        System.out.printf("Você pode remover no máximo %s linha%s.\n", indexOrdemTamanho.size() - 1, plural);
        
        if (leitor.hasNextInt()) {

            remove = leitor.nextInt(); 
            if (remove >= indexOrdemTamanho.size()) retorno(true); //Não permitir a remoção da última borda ou além.

        } else retorno(true);

        for (int i = remove; i > 0; i--) { //Remover quantas linhas quiser, limitando-se pelo tamanho inicial do menu.

            if (remove == 1) plural = "";

            System.out.println("\nQual linha você quer remover? Você não pode remover as linhas de borda."); 
            System.out.printf("\nHá %s linha%s para remover, digite um valor inválido para cancelar.\n", remove, plural);

            visMenuInterno();

            if (leitor.hasNextInt()) {

                int remv = leitor.nextInt()-1; //Converter índex base 1 em base 0.

                if ((remv == 0) || (remv >= indexOrdemTamanho.size())) { retorno(true); } 
                else {

                        comandosFeitos.remove(remv);
                        indexOrdemTamanho.remove(remv);
                        maior = Collections.max(indexOrdemTamanho) + (Collections.max(indexOrdemTamanho) % 2);

                        remove--; //Redução do remove para o texto de linhas restantes.
                        
                }
            } else retorno(true);
        }

        System.out.printf("\nLinha%s removida%s com sucesso!\n", plural, plural);
        visualizarMenu(); //Se o procedimento for completo, demonstrar o menu sem o índex.
        
    }

    void tamanhoMargem() { //Define o tamanho das margens à esquerda e à direita.

        leitor.nextLine();

        System.out.println("\nDefina um valor para o tamanho das margens.");
        System.out.printf("\nO tamanho atual é %s.\n\n", espacoMinimo);

        if (leitor.hasNextInt()) {

            int novoEspaco = leitor.nextInt();
            if (novoEspaco < 0) retorno(true); //O valor mínimo é 0.
            
            espacoMinimo = novoEspaco;
            System.out.printf("\nO tamanho agora é %s.\n", espacoMinimo);
            visualizarMenu();

        } else retorno(true);

    }

    void visualizarMenu() { //Executar os comandos do ArrayList, imprimindo o menu sem o índex.

        System.out.println("\nEis seu belo menu:");
        for (AcaoComando acaoComando : comandosFeitos) acaoComando.comando();
        retorno(false);

    }

    static void encerrar() throws Exception { //Fechar o leitor e encerrar o programa, com um atraso.

        System.out.println("\nAté mais!"); 
        leitor.close(); 
        TimeUnit.SECONDS.sleep(2); //O atraso só funciona se o método possuir "throws". Teria colocado em mais métodos se não fosse por esta limitação.
        System.exit(0);
    }

    //Comandos Internos

    void retorno(boolean inval) { //Textos de retorno

        if (inval) { //Impresso apenas se o método for invocado por um valor inválido.
            System.out.println("\nEste valor é inválido!\n"); 
        }

        System.out.println("\nDigite qualquer coisa para retornar.\n"); 
        leitor.nextLine(); //Travar o código até a entrada do jogador.
        comandos();

    }
       
    void visMenuInterno() {

        float linhaDoCodigo = 1; //É float para poder realizar o cálculo logaritmo.
        float linhaMaxima = comandosFeitos.size();
        int zeros = (int) (Math.log10(linhaMaxima)); //"zeros" vai adicionar 0s no índex das linhas de acordo com o tamanho do número.

        for (AcaoComando acaoComando : comandosFeitos) { //Equivalente do "For Each" do C#, de acordo com o VSCode.
            
            int subZeros = (int) (Math.log10(linhaDoCodigo)); //Subtrair 0s de acordo com o tamanho do número.

            for (int i = 0; (i < zeros-subZeros); i++) System.out.print("0"); //Incluir os 0s à esquerda dos números, adaptando de 
            System.out.printf("%.0f ", linhaDoCodigo);                        //acordo com o tamanho do maior número de índex.
            acaoComando.comando();

            linhaDoCodigo ++;   
        }
    }

    void LinhaGenerica(String componente) { //Imprime uma linha com ou sem texto.

        int espaco = espacoMinimo + (maior - componente.length()) / 2; //Define o tamanho dos espaços laterais.

        System.out.print("|");                                                                                //Margem
        for (int i = 0; i < (espaco + (componente.length() % 2) * saltoEsquerdo); i++) System.out.print(" "); //Espaço esquerdo
        System.out.print(componente);                                                                         //Componente
        for (int i = 0; i < (espaco + (componente.length() % 2) * saltoDireito ); i++) System.out.print(" "); //Espaço esquerdo
        System.out.println("|");                                                                              //Margem

    }

    void LinhaBorda() { //Imprime uma linha de borda.

        System.out.print("+");                                                      //Canto
        for (int i = 0; i < (maior + espacoMinimo * 2); i++) System.out.print("-"); //Margem
        System.out.println("+");                                                    //Canto

    }


/*Depuração
Copie estes comandos para qualquer* parte do código para depurar valores.
* - qualquer onde os valores correspondentes são legíveis.

Os componentes estão desatualizados.

System.out.printf("indexMaior %s og\n", indexMaior);
System.out.printf("indexAA %s og\n",indexAA);  
System.out.printf("remove %s\n",remove);
System.out.printf("componAnterior %s\n",componAnterior);
System.out.printf("componAA %s\n",componAnteanterior);
System.out.printf("indexMaior %s\n", indexMaior);
System.out.printf("indexAA %s\n",indexAA);  
System.out.printf("comandosFeitos %s\n",comandosFeitos.size());
System.out.printf("indexOT size %s\n",indexOrdemTamanho.get(indexOrdemTamanho.size()-1));
System.out.printf("indexOT max %s\n",Collections.max(indexOrdemTamanho));
System.out.printf("compon %s\n",compon);           

*/

void comandoSecreto() { //Comando secreto! Você só sabe se ler o código ;D
    
    //String do desenho da lua (foto original de autoria própria!)
    String segredo = ".                                               ...............................................................................................................  ..     .                               \n                                              .  .  ........................................................................................................ ...  ....                                  \n                                   . .   .. ..... .....................................................................................................................                                 \n                                       .............. .............................,,,,,,**************//***********,,,,,,................................................ .. .                         \n                                      ..... .............................,,,****////////////////(///(/(((((((((((((/////////***,,,,,.......................................... ..                       \n                              ....................................,,,*****///////////////////////////////////////////////(/(////////**,,,,.......................................  ..  .                \n                         .  ... ...........................,,,***//////////////////////////////////////////////////////////////((((/(////***,,,,................................   .                    \n         .      .        .    .........................,,**//////////////////////////////*****////////////////////////////////(((((((((((((////**,,..................................... .              \n                        ...........................,,**///////////****///////////((/////*******//*****////////////////////////(/(((((((((((((((////**,,.................................. ...           \n                    ...........................,,*///(((/////*************////((((/////*********/***///***//////////////////////(((((((((((((((((((((//**,,................................ ... . ..    \n                . ... ......................,*///(((((//////*************////////////***********////****/*/////////(///////////////((((((((((((((((((((((//*,,...............................     ..    \n            .   ... .....................,*/((((((((////***************////(((////*******///******///****///////////////////////(//((((((((((((((((((((((((((/**,................................       \n    .           . ....................,*/((((((((((////////*****//////(((((((////////////////****/*///////////////////////////////((((((((((((((((((((((((((((((/**,..............................      \n       .  .  .. ....................,//((((((///((/(((((////////(((((((((//////////////////////////////////////((((((//////////////(((((((((((((((((((((((/(((((((//*,,.............................    \n       .. ......  ...............,*/(((((((/(((((((//(((((//(((((((((((////////////////////////////////////(((((((((((((((((////////(((((((((((((((((((((((((((((((((//*,.............................. \n           ....................,*/(((((((((((((((((////////((((((((((///////////////////////////////////((((((((///////////////////(/(((((((((((((((((((((((((((((((((((/*,,........................ ...\n   .  .......................*/(((((((((((((((((((((((/////(((((((////////*********//*******//(((///(((((((((///////////////////(((((((((((((((((((((((((((((((((((((((((((/*,..........................\n      ....................,*/((((((((((((((((((((((((((////(((((///////*************//****////(((/////(((((((///////////////////(((((((((((((((((((((((((((((((((((((((((((((/*,........................\n..    ..................,,//(((((((((((((((((((((((((/(/////(///////*//**************///////////((((/((/(///////////////////////(((((((((((((((((((((((((((((((((((((((((((((((/*,......................\n    ...................,*/(((((((((((((((((((((((((((((((///////////////************/*//////////////////////////((((////////(((((((((((((((((((((((((((((((((((((((((((((((((((((/*,....................\n   ..................,*//(((((//((//((((((((((((((((((((((///////////////**************////////////////////////////////((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((/*,..................\n ..................,,*/(((((////(((((((((((((((((((((((((/((((///////////*/*//********///////////*//////////////((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((##((/*,,.,..............\n.  ...............,**/((((((((((((((((((((////(((((((((((//////////////////////*******////////////////////////((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((##((/,,...............\n.................,*//(((((((((((((((//////////(((((((//////////////////////////****/////////////////(((((/(((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((##((/*,,.............\n...............,,*//(((((((((/(/(/(//////////((((//////**/////////////////////*/*/////////////////((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((#(/**,.,..........\n..............,**/(((((((((((((/(///////////((((////******///////////////////*****////////////////((((((((((((((((((((((((((((((((((((((((((((###(((((((((((((((((((((((((((((((((((((#(((/*,,..........\n.............,*//(((((((((((((////////////////////////**///////////////////////**//////////////////(((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((####(/*,,,........\n............,*/((((((((((((((///////////////////////*//////////**/////////////**/////////////////(((((((((((((((((((((((((((((((((((((((((((((((((((#((((((((((((((((((((((((((((((((#####((/*,,........\n...........,*/((((((((//((//////(((/////////////////////////////////////////////////////((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((##(#(((#####(/*,,.......\n..........,*/((((((((//////////////////////////////////////////////////////////(((((////((((((((((((((((((((((((((((((((((((((((((((((((((((#(((((((((##(((((((((((((((((#(#(((((((((########(/*,,,.....\n..........,/((((((((////////////////////////////////////////////////////((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((####((((((((((####((#(##(((((((((((#((#((((((#######(*,,,.....\n.........,*((((((((///////////////////////////////////////////////////////////////////((((((((((((((((((((((((((((((((((((((((((((((((((((###(((#(#(####################(######(#(((###########/*,,.....\n.........,/((((((((///////////(/((((((((((////////////////////////////////////////////((((((((((((((((((((((((((((((((((((((((((((((((((((###((################################################(/,,,....\n........,*/(((((((//////////////(((((((((////////////////////////////////(((((/////////((((((((((((((((((((((((((((((((((((((((((((((((((((((((####################################((###########/*,,....\n........,*((((((((////////////((((((((((((//////////////////////////////////////////////((((((((((((((((((((((((((((((((((((((((#((((((((((((######################################((###########(/,,,...\n........*/((((((((/////////////((((((((((((/////////////////////////////////////////////((((((((((((((((((((((((((((((((((((((((((((((((############################################(############/*,,...\n.......,*/(((((((((/////////////(((((((((((((((((/////////////////////////////////////(((((((((((((((((((((((((((((((((((((((((((((((((((((######################################################/*,,...\n.......,*(((((((((((///////////((((((((((((((((((/////////((((((((///////////////////(((((((((((((((((((((((((((((((((((((((((((((((((((((((((###################################################(*,,...\n.......,*((((((((((((//////////((((((((((((((///////////(/(((((((((((((((////////////((((((((((((((((((((((((((((((((((((((((((((((((((((((((((##################################################(*,,...\n.......,/((((((((((((/////////((((((((((((////////////////////(((((((((((///////////(((((((((((((((////((((((((((((((((((((((((((((((((((((#((((#################################################(*,,...\n.......,/(#(((((((((//////(((((((((((((((/////////((((((//////((((((((((((((//////////((((((((((/((((((((((((((((((((((((((((((((((((((((((((((((################################################(*,,...\n.......,/(##((((((((///////((((((((((((/////////////(////////(((((((((((((((((/////////(((((((/////((((((((((((((((((((((((((((((((((((((((////(((((#############################################(*,,...\n.......,/(##((((((((//////((((((((((////////////////////////((((///////(((((///////////////////////((((((((((((((((((((((((((((((/////((((//////((((((#(##################################((#####(*,....\n.......,*(###(((((((/////((((((((/////////////////////////((((((/////////((((//////////////////////((((((((((((((((((((((((((((/////////////////((((((((((#######################################(*,....\n.......,*/####((((((/////(((((/////////////////////////////((((///////////(((/////////////////////(((/(((((((((((((((((/((((////////(/////////////((((((((#(####################################(/*,....\n........,/(###((((((//////(((///////////////////////////////////////////////////////(///////////(((((///(((((((((//////((((((///((((///////////////((((((###################(##(#(((((((((######(/,.....\n........,*(####(((((/////(((///////////////////////////////////////////////(////(((((((((((((((////////((((((((////((((((((((((((/////////(//////////(((######((((#####(((##((((((((((((((######(*,.....\n.........*/(##((((((/////(((((((//////////////////////////////////////////////(((((((((((((((////////((((((((((((((((((((((((((/(///////////////////(((#####(((((((((((((((((((((((((((((######(/,......\n.........,/(####((((//////(((((//////////////////////////////////////////////(((((((((((((((((///////((((((((((((((((((((((((((((((/////(////////((((((((((((((###((((((((((((((((((((((((####(/*,......\n.........,,/((#((((((/////(((((/////////////////////////////////////////////((((((((((((((((((((((((((((((((((((((((((((//////(((//////((((((((((((((((((((((((((((((((((((((((((((((((((#####(/,,......\n..........,*/((((((((((////(((((/////////////////////////////////////////////(((((((((((((((((((((((((((((((((((((((((/////////((//////(((((((((((((((((((((((((((((((((((((((((((((((((#####(/*,,......\n...........,*((((((((((////((((((/////////////////////////////////////////////(((((((((###(((((((((((((((((((///(((((/////////((((((((((((((((//////((((((((((((((((((((((((((((((((((((####((*,,.......\n............,/((((((((((//((((((((////////////////////////////////////////////(((((((((###(((((((((((((((((////////////////////(((((((((((((((((((((((((((((((((((((((((((((((((((((((((###((*,,,.......\n............,,/(((((((((((((((((((((////////////////////////////////////////((((((((((((((((((((((((((((((((/////////////////////((((((((((((((((((((((((((((((((((((((((((((((((((((((#((((/*,,........\n.............,*/(((((((((((((((((((((////////////////////////////////////////((((((((((((((((((((((((((((((((((((/(((((((/////////////(((((((((((((/((((((((((((((((((((((((((((((((((##((//,,,.........\n...............,//((#((((((///(((((((////////////////////////////////////////(((((((((((((((((((((((((((((((((((((((((((((((////////////(((((((///////((((((((((((((((((((((((((((((##(((/*,,...........\n...............,,*/(((#((((((/((((((((////////////////////////////////////////((((((((((((((((((((((//////(///////((((((((////////////////((///////////(((((((((((((((((((((((((((((#(((/*,,............\n   ..............,*/((((((((////((((((((//////////////////////////////((((////((((((((((((((((((((((////(/////////////(////////////(((//////////////////((((((((((((((((((((((((((###(//*,..............\n   ..  ...........,*/(((#(((((//((((((((((((((///////////////////////////(//////(///////////////////////(/////////////////////////((((//////////////////(((((((((((((((((((((((((##((/*,,...............\n.   ................,*/((((((((/////((((((((((((/////////////////////////////////////////(((((((((((((////////////////////////////((((((((////////////((((((##(((((((((((((((((###((/*,.................\n..  . ................,/(##(((((/////((((((((((((((/////////////////////////////////////(((((((((((((///((/////////////////////((((((((((((///////////((((#####((((((((((((((###((/*,,..................\n     ...................*/(((((((//////((((((((((((((/////////////////////////////////(((((((((((((((((//(////////////////////(((((((((((((((((((((((((((######((((((((((((###(((*,.....................\n     ....................,*/(((((((//////////((((((//////////////////////////////((((////(((((((((((((///////////////////////////((((((((((((((((((((((#######((((((((((((###((/*,......................\n        .... ..............,*/(((((((/////////////////////////////////////////////////////////(((((//////////////////////////////((((((((((((((((((((#####(((((((((((((####((/*,........................\n       ... .. ................*/((((((//////////////////////////(((((///////////////////////////////////////////////////////////((((((((((((((((((####((((((((((((((#####((/*......................... .\n         ...... ...............,,*/((((//////////////////////////((((((((((/////////////////////////////////////////////////////((((//(((((((((((((#(((((((((((((((####(/*,,........................... \n           ...  .   ..............,*/((((/////////////////////////(((((((((/////////////////////////////////////////////////////(((((((((((((((((########(((((((####((/*,.........................     .\n                  .   ..............,**/(((/////////////////////////////((///////////////////////////////////////////////////////((((((((((((((((######(((((((###((/*,,..................... ......... .\n             .  .   .   ...............,*///(////////////////////////////////////////////////////////////////////////////////////(((((((((((((((#####((((((((((((/*,........................... .....   \n                         .................,*//////////////////////////////////////////////////////////////////////////*//////((((((((((((((((((((((((((((((((//*,..........................             \n                       ... .  ...............,**////////////////////////////////////////////////////////***///////////////((((((((((((((((((((((####((((((/**,.............................      .      \n                                .................,**//////////////////////////////****////////////////////**/**/////(((((((((((((((((((((((((((((((((//**,,...........................  .               \n                        . ... ... .. ...............,,***//////////////////////////*///////////***//////////////////(((((((((((((((((((((((((((((//**,,...................... . ........ ..             \n                               ..       ... .............,,****//////////////////////****/////////////(((((((((((((((((((((((((((((((((((((///**,,,..........................   .  .                    \n                            .           ......................,,****/////////////////////////////((((((((((((((((((((((((((((((((((((///***,,,,.................................. ..                    \n                                ....          ......................,,*****/////(((((((((((((((((((((((((((((((((((((((((((((((///****,,.................................. .. .                         \n                                        .         .........................,,,****////((((((((((##((#####((#(((((((((////****,,,,.........................................                              \n                                 .           .         ..............................,,,,,,,********************,,,,,,,...............................................   .                              \n                                                          ..  .....................................................................................................                                     \n                                                                ... ...................................................................................    .  .                                         \n";   

        System.out.println("\n"+segredo+"\n");
        System.out.println("Comando secreto! Toma uma lua. Veja em tela cheia!"); 
        System.out.println("\nArte ASCII feita em https://manytools.org/hacker-tools/convert-images-to-ascii-art/\n");
        retorno(false); //Imprimir o texto após o desenho, créditos do programa utilizado, retornar ao menu.
    }

} //Fim!