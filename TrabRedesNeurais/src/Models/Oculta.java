/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author vicga
 */
public class Oculta 
{
    private List<Neuronio> n;
    private double[][] ocultapeso, saidapeso;

    public Oculta() {
        n = new ArrayList<>();
    }

    public Oculta(List<Neuronio> n, double[][] ocultapeso, double[][] saidapeso) {
        this.n = n;
        this.ocultapeso = ocultapeso;
        this.saidapeso = saidapeso;
    }

    public List<Neuronio> getNeuronio() {
        return n;
    }

    public void setNeuronio(List<Neuronio> n) {
        this.n = n;
    }

    public double[][] getOcultapeso() {
        return ocultapeso;
    }

    public void setOcultapeso(double[][] ocultapeso) {
        this.ocultapeso = ocultapeso;
    }

    public double[][] getSaidapeso() {
        return saidapeso;
    }

    public void setSaidapeso(double[][] saidapeso) {
        this.saidapeso = saidapeso;
    }
    
    public void setCamada(int ent,int ocu, int sai)
    {
        for (int i = 0; i < ocu; i++) 
            n.add(new Neuronio());
        setOcultapeso(new double[ocu][ent]);
        setSaidapeso(new double[sai][ocu]);
    }
    
    public void rPeso(int entradas, int saidas)
    {
        Random r = new Random();
        for (int i = 0; i < n.size(); i++) 
            for (int j = 0; j < entradas; j++) 
                ocultapeso[i][j] = (-2)+ r.nextDouble() * (2 - (2));
        
        for (int i = 0; i < saidas; i++) 
            for (int j = 0; j < n.size(); j++) 
                ocultapeso[i][j] = (-2)+ r.nextDouble() * (2 - (2));
    }
    
    public void corrigePesoO(double aprendizagem, List<Double> erro)
    {
        for (int i = 0; i < n.size(); i++)
            for (int j = 0; j < erro.size(); j++) 
                ocultapeso[i][j] = ocultapeso[i][j] + 
                        (aprendizagem * n.get(i).getErro() * erro.get(j));
    }
    
    public void corrigePesoS(double aprendizagem, List<Double> erro)
    {
        for (int i = 0; i < erro.size(); i++)
            for (int j = 0; j < n.size(); j++)
                saidapeso[i][j] = saidapeso[i][j] + 
                        (aprendizagem * erro.get(i) * n.get(j).getNetr());
    }
}
