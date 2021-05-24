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
    private double net,derivada,erro,fnet;

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
        for (int i = 0; i < camada.size(); i++)
            net += camada.get(i) * peso[pos][i];
    }
    
    public void setLinear()
    {
        fnet = net / 10.0;
        derivada = 0.1;
    }

    public void SetLogistica()
    {
        fnet = (1 / (1 + Math.pow(Math.E, (-net))));
        derivada = fnet * (1 - fnet);
    }

    public void setHiperbolica()
    {
        fnet = (((1 - (Math.pow(Math.E, (-2 * net))))) / (1 + (Math.pow(Math.E, (-2 * net)))));
        derivada = (1 - (Math.pow(fnet, 2)));
    }

    public double getFnet() {
        return fnet;
    }

    public void setFnet(double fnet) {
        this.fnet = fnet;
    }

    public void calculaErroS(int i) 
    {
        erro = (i-fnet) * derivada;
    }

    public void calculaErroOculta(int pos, List<Double> camada, double[][] peso)
    {
        erro = 0;
        for (int i = 0; i < camada.size(); i++)
            erro += camada.get(i) * peso[i][pos];
        
        erro *= derivada;
    }
    
}
