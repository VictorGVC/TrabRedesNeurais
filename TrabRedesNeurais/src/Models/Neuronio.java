/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.List;

/**
 *
 * @author vicga
 */
public class Neuronio 
{
    private double net,derivada,erro,netr;

    public Neuronio() {
    }

    public Neuronio(double net, double derivada, double erro) {
        this.net = net;
        this.derivada = derivada;
        this.erro = erro;
    }

    public double getNet() {
        return net;
    }

    public void setNet(double net) {
        this.net = net;
    }

    public double getDerivada() {
        return derivada;
    }

    public void setDerivada(double derivada) {
        this.derivada = derivada;
    }

    public double getErro() {
        return erro;
    }

    public void setErro(double erro) {
        this.erro = erro;
    }
    
    public void calculaNet(int pos,List<Double> camada,double[][] peso)
    {
        net = 0;
        for (int i = 0; i < camada.size() - 1; i++)
            net += camada.get(i) * peso[pos][i];
    }
    
    public void setLinear()
    {
        netr = net / 10.0;
        derivada = 0.1;
    }

    public void SetLogistica()
    {
        netr = (1 / (1 + Math.pow(Math.E, (-net))));
        derivada = netr * (1 - netr);
    }

    public void setHiperbolica()
    {
        netr = (((1 - (Math.pow(Math.E, (-2 * net))))) / (1 + (Math.pow(Math.E, (-2 * net)))));
        derivada = (1 - (Math.pow(netr, 2)));
    }

    public double getNetr() {
        return netr;
    }

    public void setNetr(double netr) {
        this.netr = netr;
    }

    public void calculaErroS(int i) 
    {
        erro = (i-netr) * derivada;
    }

    public void calculaErroOculta(int pos, List<Double> camada, double[][] peso)
    {
        erro = 0;
        for (int i = 0; i < camada.size(); i++)
            erro += camada.get(i) * peso[i][pos];
        
        erro *= derivada;
    }
    
}
