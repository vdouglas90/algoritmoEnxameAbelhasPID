/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmoenxameabelhaspid;

import br.ufrn.dca.controle.QuanserClient;
import br.ufrn.dca.controle.QuanserClientException;
import java.util.Collections;
import java.util.Vector;

/**
 *
 * @author Victor Douglas
 */
public class Pso {

    private int tamanhoPopulacao;
    public Vector populacao;
    private QuanserClient client;
    private Vector Gbest;

    public Pso(int tamanhoPopulacao) {
        populacao = new Vector();
        Gbest = new Vector();
        this.tamanhoPopulacao = tamanhoPopulacao;
    }

    public Vector criarPopulacao() {
        //POPULACAO SENDO CRIADA COM NUMEROS RANDOMICOS ENTRE 0 E 50 EM UM PLANO CARTESIANO
        for (int i = 0; i < tamanhoPopulacao; i++) {
            Individuo individuo = new Individuo();
            //VOU INICIALIZAR OS VALORES DE CADA PARTICULA(KP,KI,KD)
            individuo.particula.add(Math.random() * 20); //antes eu usava de 0 a 100, mas meu problema exite algo menor que 5(na proxima testar valores menor q 1!
            individuo.particula.add(Math.random() * 20);  //VALOR ALEATORIO PRO KI
            individuo.particula.add(Math.random() * 20);  //VALOR ALEATORIO PRO KD

            //preciso inicializar tbm valores aleatórios de velocidade(os inicializei entre 0 e 1, isso tirar dúvida!!!
            individuo.velocidade.add(Math.random() * 5); //antes eu usava de 0 a 100, mas meu problema exite algo menor que 5(na proxima testar valores menor q 1!
            individuo.velocidade.add(Math.random() * 5);  //VALOR ALEATORIO PRO KI
            individuo.velocidade.add(Math.random() * 5);  //VALOR ALEATORIO PRO KD

            //Inicializar meus valores de Pbest e Gbest com o próprio valor da minha particula
            for (int j = 0; j < individuo.particula.size(); j++) {
                individuo.Pbest.add((Double)individuo.particula.get(j));
                //DECIDI USAR O GBEST GLOBAL, ALGO QUE SERÁ GLOBAL E NÃO PERTENCENTE A CADA INDIVIDUO
                //JA QUE O GBEST É O MELHOR DE TODOS ACHADO ATÉ AGORA!!
                //ELE SERA INICIADO SIMPLESMENTE PEGANDO A MELHOR AVALIACÃO INICIAL
                //  individuo.Gbest.add(individuo.particula.get(j));

            }

            populacao.add(individuo); // adicionando um Individuo da classe Individuo

        }
        return populacao; // vetor de Individuos
    }

    public double funcaoAvaliacao(double kp, double ki) throws QuanserClientException, InterruptedException { //MEU PONTO MINIMO PRA APROXIMAR É X=2 E Y=2

        double raiz1 = Math.pow(kp - 10, 2);
        double raiz2 = Math.pow(ki - 15, 2);

        return Math.sqrt(raiz1 + raiz2);
     //   return treinamento(kp, ki);


    }

    public void avaliacaoParticulas(Vector populacao) throws QuanserClientException, InterruptedException {
        for (int i = 0; i < populacao.size(); i++) {
            ((Individuo) populacao.get(i)).setAvaliacao(funcaoAvaliacao((Double) ((Individuo) populacao.get(i)).particula.get(0), (Double) ((Individuo) populacao.get(i)).particula.get(1)));
            System.out.println("VALORES DE AVALIACAO DO INDIVIDUO " + i + "-------  " + ((Individuo) populacao.get(i)).getAvaliacao() + "     KP-KI-KD: " + ((Individuo) populacao.get(i)).particula.get(0) + " " + ((Individuo) populacao.get(i)).particula.get(1));
        }
    }

    //estes parametros w,c1 e c2 são próprios do algoritmo(na hora de atualizar o V).
    //(em um trabalho, vi sendo usado  C1 = 2 e C2=2 e w = 0.8
    //Preciso perguntar ao prof. se o alfa1 e alfa2 que são aleatorios entre 0 e 1 precisam ser gerados toda iteração.
    public Vector enxame(double w, double c1, double c2, int geracoes) throws QuanserClientException, InterruptedException {
        System.out.println();
        System.out.println();
        System.out.println();
        double alfa1 = Math.random(); //vi em um material isso realmente sendo feito toda iteração!
        double alfa2 = Math.random();
        int iterador = 0; //vai incrementar até atingir o limite de gerações!
        criarPopulacao();
        avaliacaoParticulas(populacao);

        //essas variaveis qvou inicializar aqui é apenas como auxiliares no algoritmo
        double a = 0, b = 0, c = 0;


        //VOU INICIALIZAR MEU GBEST
        ////ORDENO MINHA POP PELA AVALIACAO(ORDEM CRESCENTE). Pego o primeiro q tem a menor e melhor avaliacao, e seus pontos vão ser meu gbest inicial!
        Collections.sort(populacao, new ComparadorDeIndividuosAvaliacao());
        Gbest.add(((Individuo) populacao.get(0)).particula.get(0));
        Gbest.add(((Individuo) populacao.get(0)).particula.get(1)); //ADD OS VALORES NO GBEST, A MELHOR PARTICULAR COM SEUS PONTOS!
        //Gbest.add(((Individuo) populacao.get(0)).particula.get(2));

        //CORAÇÃO DO ALGORITMO AGORA!
        while (iterador < geracoes) {
            for (int j = 0; j < populacao.size(); j++) {
                //vo Atualizar o valor da velocidade pro KP, KI e KD(se ligar q na subtração preciso pegar o módulo!
                a = w * (Double) ((Individuo) populacao.get(j)).velocidade.get(0) + c1 * alfa1 * (Math.abs( (Double) ((Individuo) populacao.get(j)).Pbest.get(0) - (Double) ((Individuo) populacao.get(j)).particula.get(0) )  ) + c2 * alfa2 * ( Math.abs( (Double) Gbest.get(0) - (Double) ((Individuo) populacao.get(j)).particula.get(0) )  );
                ((Individuo) populacao.get(j)).velocidade.set(0, a);
                
                b = w * (Double) ((Individuo) populacao.get(j)).velocidade.get(1) + c1 * alfa1 * (  Math.abs(  (Double) ((Individuo) populacao.get(j)).Pbest.get(1) - (Double) ((Individuo) populacao.get(j)).particula.get(1) ) ) + c2 * alfa2 * ( Math.abs( (Double) Gbest.get(1) - (Double) ((Individuo) populacao.get(j)).particula.get(1)) );
                ((Individuo) populacao.get(j)).velocidade.set(1, b);
                
             //   c = w * (Double) ((Individuo) populacao.get(j)).velocidade.get(2) + c1 * alfa1 * (  Math.abs( (Double) ((Individuo) populacao.get(j)).Pbest.get(2) - (Double) ((Individuo) populacao.get(j)).particula.get(2)  ) ) + c2 * alfa2 * (  Math.abs( (Double) Gbest.get(2) - (Double) ((Individuo) populacao.get(j)).particula.get(2) )  );
             //   ((Individuo) populacao.get(j)).velocidade.set(2, c);


                //atualizado os valores de Velocidade, atualizaremos agora os valores das particulas x = x + v
                //(Double)((Individuo)populacao.get(j)).particula.get(0) + (Double)((Individuo)populacao.get(j)).Pbest.get(0)
                ((Individuo)populacao.get(j)).particula.set(0, (Double)((Individuo)populacao.get(j)).particula.get(0) + (Double)((Individuo)populacao.get(j)).velocidade.get(0));
                ((Individuo)populacao.get(j)).particula.set(1, (Double)((Individuo)populacao.get(j)).particula.get(1) + (Double)((Individuo)populacao.get(j)).velocidade.get(1));
               // ((Individuo)populacao.get(j)).particula.set(2, (Double)((Individuo)populacao.get(j)).particula.get(2) + (Double)((Individuo)populacao.get(j)).velocidade.get(2));

                //Agora vou procurar quem é o melhor entre a particula x e o Pbest, pra o Pbest anterior receber tal melhor!
                //"a" vai receber a avaliacao da particula X e o b vai receber do Pbest
                a = funcaoAvaliacao((Double)((Individuo)populacao.get(j)).particula.get(0) , (Double)((Individuo)populacao.get(j)).particula.get(1)  );
                System.out.println("Geracao: "+iterador+"    ---- Individuo: "+j+"  -----Avaliacao: "+a+"  ---KP KI KD: "+(Double)((Individuo)populacao.get(j)).particula.get(0)+" "+(Double)((Individuo)populacao.get(j)).particula.get(1));
                b = funcaoAvaliacao((Double)((Individuo)populacao.get(j)).Pbest.get(0) , (Double)((Individuo)populacao.get(j)).Pbest.get(1)   );
                System.out.println("Geracao: "+iterador+"    ---- Individuo: "+j+"  -----Avaliacao: "+b+"  ---KP KI KD: "+ (Double)((Individuo)populacao.get(j)).Pbest.get(0)+" "+(Double)((Individuo)populacao.get(j)).Pbest.get(1) );
                //aqui testo quem é o menor, se é "a"(particula) ou "b"(Pbest)
                if(a < b){ // se particula é o melhor
                    ((Individuo)populacao.get(j)).Pbest.set(0,(Double)((Individuo)populacao.get(j)).particula.get(0));
                    ((Individuo)populacao.get(j)).Pbest.set(1,(Double)((Individuo)populacao.get(j)).particula.get(1));
                 //   ((Individuo)populacao.get(j)).Pbest.set(2,(Double)((Individuo)populacao.get(j)).particula.get(2));
                }
                else if(b <= a){
                    ((Individuo)populacao.get(j)).Pbest.set(0,(Double)((Individuo)populacao.get(j)).Pbest.get(0));
                    ((Individuo)populacao.get(j)).Pbest.set(1,(Double)((Individuo)populacao.get(j)).Pbest.get(1));
                //    ((Individuo)populacao.get(j)).Pbest.set(2,(Double)((Individuo)populacao.get(j)).Pbest.get(2));

                }

                 //Agora vou procurar quem é o melhor entre a particula  Pbest e Gbest, pra o Gbest anterior receber tal melhor!
                //"a" vai receber a avaliacao da particula X e o b vai receber do Pbest
                a = funcaoAvaliacao((Double)((Individuo)populacao.get(j)).Pbest.get(0) , (Double)((Individuo)populacao.get(j)).Pbest.get(1) );
                System.out.println("Geracao: "+iterador+"    ---- Individuo: "+j+"  -----Avaliacao: "+a+"  ---KP KI KD: "+(Double)((Individuo)populacao.get(j)).Pbest.get(0)+" "+(Double)((Individuo)populacao.get(j)).Pbest.get(1));
                b = funcaoAvaliacao((Double) Gbest.get(0) , (Double) Gbest.get(1) );
                System.out.println("Geracao: "+iterador+"    ---- Individuo: "+j+"  -----Avaliacao: "+b+"  ---KP KI KD: "+(Double) Gbest.get(0)+" "+(Double) Gbest.get(1));
                System.out.println();
                //aqui testo quem é o menor, se é "a"(Pbest) ou "b"(Gbest)
                if(a<b){
                     Gbest.set(0,(Double)((Individuo)populacao.get(j)).Pbest.get(0));
                     Gbest.set(1,(Double)((Individuo)populacao.get(j)).Pbest.get(1));
                //     Gbest.set(2,(Double)((Individuo)populacao.get(j)).Pbest.get(2));

                }

                else if(b<=a){
                    Gbest.set(0, Gbest.get(0));
                    Gbest.set(1, Gbest.get(1));
                  //  Gbest.set(2, Gbest.get(2));
                }

            }
            
            iterador = iterador + 1;
            alfa1 = Math.random();
            alfa2 = Math.random(); //alg manda atualizar esses valores a cada iteracao!
        }
        //processo pra retornar o melhor ao fim do processamento!
        //Collections.sort(populacao, new ComparadorDeIndividuosAvaliacao());//ordena em ordem crescente de acordo com as avaliacoes!
        //return (Individuo) populacao.get(0);
        //EU TENHO Q PEGAR O GBEST, TAVA FAZENDO ERRADO
        return  Gbest;

    }

    public double treinamento(double kp, double ki, double kd) throws QuanserClientException, InterruptedException {
        double saida = 0;
        double somaErros = 0;
        double erro = 0;
        double sp = 15;
        double valorCalculado = 0, valorEscrito = 0;
        double acaoI = 0;
        double t = 0, //tempo
                Ts = 0.1;
        double erroAnterior = 0;

        client = new QuanserClient("localhost", 20081);

        while (t < 180) { //3 * 60 = 180 (cada set poin por 60 vezes OBSERVAR ISSO AQUI QNDO VOLTA P KSA

            saida = client.read(0) * 6.25;
            erro = sp - saida;
            somaErros = somaErros + Math.abs(erro); //VO PEGAR AQUI O VALOR ABSOLUTO DO ERRO, P NÃO SUBTRAIR ND

            valorCalculado = kp * erro + kd * ((erro - erroAnterior) / Ts) + (acaoI = acaoI + ki * Ts * erro);
            valorEscrito = valorCalculado;
            erroAnterior = erro;
            if (valorEscrito > 3) { //SATURAÇÃO DO ATUADOR
                valorEscrito = 3;
            }
            if (valorEscrito < -3) {
                valorEscrito = -3;
            }

            t = t + 1;
            client.write(0, valorEscrito);


            Thread.sleep(100); //AQUI EU DOU UMA PAUSA DE 0.1



            if (t == 60) { //AQUI EU VEJO SE JA ATINGI 20 AMOSTRAS DE VALORES DE VALIDAÇÃO(NO MEU CASO ERROS)
                sp = sp - 5;
            } else if (t == 120) {
                sp = sp + 10;
            } else if (t == 180) { //SE ATINGI 60 AMOSTRAR, PARO TUDO AQUI E RETORNO
                t = 181; //pra sair do while
                client.write(0, 0); //escrever zero na bomba!
            }

        }
        return somaErros;
    }
}
