/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vicga
 */
public class Treino 
{    
    private List<Double> entradas = new ArrayList<>();
    private List<Neuronio> saidas = new ArrayList<>();
    private Oculta o;
    private double erro;

    public Treino() {
        o = new Oculta();
    }
    
    public Oculta getOculta() {
        return o;
    }

    public void setOculta(Oculta o) {
        this.o = o;
    }

    public double getErro() {
        return erro;
    }

    public void setErro(double erro) {
        this.erro = erro;
    }
    
    public List<Double> getEntradas() {
        return entradas;
    }

    public void setEntradas(List<Double> entradas) {
        this.entradas = entradas;
    }

    public List<Neuronio> getSaidas() {
        return saidas;
    }

    public void setSaidas(List<Neuronio> saidas) {
        this.saidas = saidas;
    }

    public void calcularER()
    {
        for (int i = 0; i < saidas.size(); i++) 
            erro+= Math.pow(saidas.get(i).getErro(), 2);
        erro *= 0.5;
    }
}
