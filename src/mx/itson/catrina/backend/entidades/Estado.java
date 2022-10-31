/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.catrina.backend.entidades;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
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

    /**
     * Pasa los datos de un json al objeto estado.
     * @param json Es el json a convertir.
     * @return El objeto estado con los atributos del json.
     */
    public Estado deserializar(String json) {
        Estado estado = new Estado();

        try {
            estado = new Gson().fromJson(json, Estado.class);
        } catch (JsonSyntaxException e) {
            System.err.println("Error: " + e.getMessage());
        }

        return estado;
    }

    /**
     * Ordena la información de la cuenta.
     * @return Una lista con la información ordenada.
     */
    public Object[] obtenerInfoCuentaOrdenada() {
        Object[] lista = {
            "Cuenta: " + getCuenta(),
            "Clabe: " + getClabe(),
            "Moneda: " + getMoneda()
        };

        return lista;
    }
    
    /**
     * Ordena la información del cliente.
     * @return Una lista con la información ordenada.
     */
    public Object[] obtenerInfoClienteOrdenada() {
        Object[] lista = {
            "Nombre: " + cliente.getNombre(),
            "RFC: " + cliente.getRfc(),
            "Domicilio: " + cliente.getDomicilio(),
            "Cuidad: " + cliente.getCiudad(),
            "CP: " + cliente.getCp()
        };

        return lista;
    }
    
    /**
     * Ordena la información del resumen.
     * @param mes El número mes del que se hará el resumen.
     * @return Una lista con todos los resumenes ordenados.
     */
    public Object[] obtenerInfoResumenOrdenada(int mes) {
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

    /**
     * Filtra la lista de movimientos para solo mostrar los necesarios.
     * @param mes El número del mes.
     * @return Una lista de los movimientos del mes dado.
     */
    public List<Movimiento> obtenerListaMovimientosFiltrada(int mes) {
        List<Movimiento> movimientosFiltrados = new ArrayList<>();

        for (Movimiento m : this.movimientos) {
            if (m.getFecha().getMonth() == mes) {
                movimientosFiltrados.add(m);
            }
        }

        movimientosFiltrados.sort((m1, m2) -> m1.getFecha().compareTo(m2.getFecha()));

        return movimientosFiltrados;
    }

    /**
     * El saldo inicial según el mes.
     * @param mes El número del mes.
     * @return El saldo inicial, osea el saldo de los meses anteriores combinado.
     */
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

    /**
     * Obtiene el saldo final del mes.
     * @param mes El número del mes.
     * @return El saldo final.
     */
    public double obtenerSaldoFinal(int mes) {
        return (obtenerSaldoInicial(mes) + obtenerTotalDepositos(mes) - obtenerTotalRetiros(mes));
    }

    /**
     * Obtiene el total de los depositos del mes seleccionado.
     * @param mes El número del mes.
     * @return El total de depositos.
     */
    public double obtenerTotalDepositos(int mes) {
        double totalDepositos = 0;

        for (Movimiento m : this.movimientos) {
            if (m.getTipo() == Tipo.DEPOSITO && m.getFecha().getMonth() == mes) {
                totalDepositos += m.getCantidad();
            }
        }

        return totalDepositos;
    }

    /**
     * Obtiene el total de los retiros del mes seleccionado.
     * @param mes El número del mes.
     * @return El total de los retiros.
     */
    public double obtenerTotalRetiros(int mes) {
        double totalRetiros = 0;

        for (Movimiento m : this.movimientos) {
            if (m.getTipo() == Tipo.RETIRO && m.getFecha().getMonth() == mes) {
                totalRetiros += m.getCantidad();
            }
        }

        return totalRetiros;
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
