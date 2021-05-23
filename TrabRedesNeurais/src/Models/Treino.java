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
    private double erro;

    public Treino() {
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
}
