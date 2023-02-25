/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package algoritmoenxameabelhaspid;

import java.util.Vector;

/**
 *
 * @author Victor Douglas
 */
public class Individuo implements Comparable<Individuo> {
    public Vector particula;
    public Vector velocidade;
    public Vector Pbest;
    //public Vector Gbest; //ISSO VAI SER GLOBAL LA NO CODIGO DO PSO
    private double avaliacao;
    public Individuo(){

       particula = new Vector();
       velocidade = new Vector();
       Pbest = new Vector();
       //Gbest = new Vector();

   }

    /**
     * @return the avaliacao
     */
    public double getAvaliacao() {
        return avaliacao;
    }

    /**
     * @param avaliacao the avaliacao to set
     */
    public void setAvaliacao(double avaliacao) {
        this.avaliacao = avaliacao;
    }


     @Override
    public int compareTo(Individuo o) {
        if(this.avaliacao == o.getAvaliacao()){
            return 0;}

        if(o.getAvaliacao() > this.avaliacao){
            return 1;}
        else{
            return -1;}
    }




}
