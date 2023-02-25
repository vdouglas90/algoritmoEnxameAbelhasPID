/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package algoritmoenxameabelhaspid;

import br.ufrn.dca.controle.QuanserClientException;
import java.util.Vector;

/**
 *
 * @author Victor Douglas
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws QuanserClientException, InterruptedException {
        // TODO code application logic here
    
        Pso pso = new Pso(50);
       // Individuo individuo = new Individuo();
        Vector individuo = new Vector();
        individuo = pso.enxame(0.8,2,2,100); //parametros -> w,c1,c2,geracoes

        //System.out.println("GENES DO MELHOR INDIVIDUO -> " +individuo.particula.get(0)+" "+individuo.particula.get(1)+" "+individuo.particula.get(2) + " AVALIACAO FOI: " + individuo.getAvaliacao());
        System.out.println("GENES DO MELHOR INDIVIDUO -> " +individuo.get(0)+" "+individuo.get(1) + " AVALIACAO FOI: " +  pso.funcaoAvaliacao((Double)individuo.get(0), (Double)individuo.get(1))/*individuo.getAvaliacao()*/);
    }

}
