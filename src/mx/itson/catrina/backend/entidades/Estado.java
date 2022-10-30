/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.catrina.backend.entidades;

import com.google.gson.Gson;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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

    public Object[] obtenerLista() {
        Object[] lista = {
            "Cuenta: " + getCuenta(),
            "Clabe: " + getClabe(),
            "Moneda: " + getMoneda()
        };

        return lista;
    }

    public Object[] obtenerInfoResumen(int mes) {
        Locale local = new Locale("es", "MX");
        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(local);

        Object[] lista = {
            "Saldo Inicial: " + formatoMoneda.format(obtenerSaldoInicial(mes)),
            "Depositos: " + formatoMoneda.format(obtenerTotalDepositos(mes)),
            "Retiros: " + formatoMoneda.format(obtenerTotalRetiros(mes)),
            "Saldo Final: " + formatoMoneda.format(obtenerSaldoFinal(mes))
        };

        return lista;
    }

    public List<Movimiento> obtenerListaMovimientosFiltrada(int mes) {
        List<Movimiento> movimientos = new ArrayList<>();

        for (Movimiento m : this.movimientos) {
            if (m.getFecha().getMonth() == mes) {
                movimientos.add(m);
            }
        }

        movimientos.sort((m1, m2) -> m1.getFecha().compareTo(m2.getFecha()));

        return movimientos;
    }

    public double obtenerSaldoInicial(int mes) {
        double saldoInicial = 0;

        for (Movimiento m : this.movimientos) {
            for (int i = 0; i < mes; i++) {
                if (m.getFecha().getMonth() == i && m.getTipo() == Tipo.DEPOSITO) {
                    saldoInicial += m.getCantidad();
                } else if (m.getFecha().getMonth() == i && m.getTipo() == Tipo.RETIRO) {
                    saldoInicial -= m.getCantidad();
                }
            }
        }

        return saldoInicial;
    }

    public double obtenerSaldoFinal(int mes) {
        return (obtenerSaldoInicial(mes) + obtenerTotalDepositos(mes) - obtenerTotalRetiros(mes));
    }

    public double obtenerTotalDepositos(int mes) {
        double totalDepositos = 0;

        for (Movimiento m : this.movimientos) {
            if (m.getTipo() == Tipo.DEPOSITO && m.getFecha().getMonth() == mes) {
                totalDepositos += m.getCantidad();
            }
        }

        return totalDepositos;
    }

    public double obtenerTotalRetiros(int mes) {
        double totalRetiros = 0;

        for (Movimiento m : this.movimientos) {
            if (m.getTipo() == Tipo.RETIRO && m.getFecha().getMonth() == mes) {
                totalRetiros += m.getCantidad();
            }
        }

        return totalRetiros;
    }

    public double obtenerSubtotal(Movimiento movimiento, int mes) {
        double subtotal = 0;

        if (movimiento.getTipo() == Tipo.DEPOSITO) {
            subtotal += movimiento.getCantidad();
        } else if (movimiento.getTipo() == Tipo.RETIRO) {
            subtotal -= movimiento.getCantidad();
        }

        return subtotal;
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
