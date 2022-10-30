/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.catrina.backend.entidades;

import com.google.gson.Gson;
import java.util.List;
import mx.itson.catrina.backend.enumeradores.Tipo;

/**
 *
 * @author lm
 */
public class Estado {

    private String producto;
    private String cuenta;
    private String clabe;
    private String moneda;
    private Cliente cliente;
    private List<Movimiento> movimientos;

    public Estado deserializar(String json) {
        Estado estado = new Estado();

        try {
            estado = new Gson().fromJson(json, Estado.class);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        return estado;
    }

    public double suma(List<Movimiento> listaMovimientos) {
        double resultado = 0;

        for (Movimiento m : listaMovimientos) {
            switch (m.getTipo()) {
                case DEPOSITO ->
                    resultado += m.getCantidad();
                case RETIRO ->
                    resultado -= m.getCantidad();
                default ->
                    throw new AssertionError();
            }
        }

        return resultado;
    }

    public Object[] obtenerLista() {
        Object[] lista = {
            "Cuenta: " + getCuenta(),
            "Clabe: " + getClabe(),
            "Moneda: " + getMoneda()
        };

        return lista;
    }

    public double obtenerSaldoInicial(List<Movimiento> movimientos, int mes) {
        double saldoInicial = 0;

        for (Movimiento m : movimientos) {
            for (int i = 0; i < movimientos.size(); i++) {
                if (m.getFecha().getMonth() == i && m.getTipo() == Tipo.DEPOSITO) {
                    saldoInicial += m.getCantidad();
                } else if (m.getFecha().getMonth() == i && m.getTipo() == Tipo.RETIRO) {
                    saldoInicial -= m.getCantidad();
                }
            }
        }

        return saldoInicial;
    }

    /**
     * @return the producto
     */
    public String getProducto() {
        return producto;
    }

    /**
     * @param producto the producto to set
     */
    public void setProducto(String producto) {
        this.producto = producto;
    }

    /**
     * @return the cuenta
     */
    public String getCuenta() {
        return cuenta;
    }

    /**
     * @param cuenta the cuenta to set
     */
    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    /**
     * @return the clabe
     */
    public String getClabe() {
        return clabe;
    }

    /**
     * @param clabe the clabe to set
     */
    public void setClabe(String clabe) {
        this.clabe = clabe;
    }

    /**
     * @return the moneda
     */
    public String getMoneda() {
        return moneda;
    }

    /**
     * @param moneda the moneda to set
     */
    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    /**
     * @return the cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * @return the movimientos
     */
    public List<Movimiento> getMovimientos() {
        return movimientos;
    }

    /**
     * @param movimientos the movimientos to set
     */
    public void setMovimientos(List<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }

}
