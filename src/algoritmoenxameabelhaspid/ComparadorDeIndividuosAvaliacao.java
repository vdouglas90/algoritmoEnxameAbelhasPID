/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package algoritmoenxameabelhaspid;
import java.util.Comparator;
/**
 *
 * @author Victor Douglas
 */
public class ComparadorDeIndividuosAvaliacao implements Comparator<Individuo> {

        @Override
    public int compare(Individuo o1, Individuo o2) {
        if(o1.getAvaliacao() == o2.getAvaliacao()){
            return 0;
        }
        if(o1.getAvaliacao() > o2.getAvaliacao()){
            return 1;
        }
        else{
            return -1;
        }
    }
}
