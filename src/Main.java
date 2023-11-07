import java.util.Random;

/**
 * FR1: Crea una clase CuentaCorriente, con un atributo que indique el saldo de la cuenta, 
 * el constructor que le da un valor inicial al saldo y después los métodos setter y getter. 
 * En estos métodos deberás añadir un sleep() aleatorio, que duerma al hilo durante un número 
 * de milisegundos que oscile entre 250 y 2000 (0,25s y 2s respectivamente). Añade también otro 
 * método que reciba una cantidad y la añada al saldo, indicando por pantalla el estado previo 
 * del saldo, el estado final y quién realiza el ingreso. Deberás definir los parámetros que 
 * reciba este método y deberás definirlo como synchronized.
 */
class CuentaCorriente {
    private double saldo;

    /**
     * Constructor para esta clase
     * @param saldo
     */
    public CuentaCorriente(double saldo) {
        this.saldo = saldo;
    }

    /**
     * @return el saldo actual de la cuenta después de un retraso aleatorio
     */
    public double getSaldo(){
        randomSleep();
        return saldo;
    }

    /**
     * Establece el saldo de la cuenta después de un retraso aleatorio
     * @param saldo
     */
    public void setSaldo(double saldo){
        randomSleep();
        this.saldo=saldo;
    }

    /**
     * Ingresa una cantidad en la cuenta corriente de forma síncrona.
     * @param cantidadAIngresar
     */
    public void ingreso(double cantidadAIngresar){
        double saldoAnterior=getSaldo();
        System.out.println("Saldo previo al ingreso: "+saldoAnterior);
        System.out.println("Saldo a ingresar: " + cantidadAIngresar);
        setSaldo(saldoAnterior+cantidadAIngresar);
        System.out.println("Saldo después del ingreso: "+ getSaldo());
    }

    /**
     * Tiempo de espera artificial para los métodos de esta clase.
     */
    public void randomSleep(){
        Random r = new Random();
        try {
            Thread.sleep(r.nextInt(250,2000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/**
 * FR2: Crea una clase que extienda de Thread, y desde el método run() usará el método de la 
 * clase CuentaCorriente que permite añadir saldo a la cuenta.
 */
class Fr2 extends Thread{
    CuentaCorriente cuentaFr2 = new CuentaCorriente(0);
    @Override
    public void run() {
        cuentaFr2.ingreso(100);
    }
}

/**
 * FR3: Crea en el método main un objeto de tipo CuentaCorriente asignando un valor inicial y 
 * visualiza el saldo inicial. Ahora crea varios hilos que compartan dicho objeto. Cada hilo
 * recibirá un nombre y tendrá una cantidad de dinero. Lanza los hilos y espera a que finalicen 
 * para visualizar el saldo final de la cuenta
 */
public class Main{
    
    public static void main(String[] args) throws Exception {

        // FR3: Crea en el método main un objeto de tipo CuentaCorriente asignando un 
        // valor inicial y visualiza el saldo inicial
        final CuentaCorriente cuentaCompartida = new CuentaCorriente(1);
        System.out.println(cuentaCompartida.getSaldo());

        // Ahora crea varios hilos que compartan dicho objeto. Cada hilo recibirá un nombre
        // y tendrá una cantidad de dinero. Lanza los hilos y espera a que finalicen para 
        // visualizar el saldo final de la cuenta

        /**
         * Clase que extiende Thread para utilizar en la segunda parte de FR3
         * Tiene un nombre y una cantidad de dinero a ingresar.
         */
        class HiloConCuentaCompartida extends Thread{
            private String nombreHilo;
            CuentaCorriente cuentaCompartida;
            double cantidadAIngresar;
            public HiloConCuentaCompartida(CuentaCorriente cuentaCompartida,String nombreHilo, double cantidadAIngresar) {
                this.nombreHilo=nombreHilo;
                this.cuentaCompartida = cuentaCompartida;
                this.cantidadAIngresar=cantidadAIngresar;
            }
            @Override
            public void run() {
                cuentaCompartida.ingreso(cantidadAIngresar);
            }
            
        }

        HiloConCuentaCompartida hilo1 = new HiloConCuentaCompartida(cuentaCompartida, "Hilo1", 100);
        HiloConCuentaCompartida hilo2 = new HiloConCuentaCompartida(cuentaCompartida, "Hilo2", 200);
        HiloConCuentaCompartida hilo3 = new HiloConCuentaCompartida(cuentaCompartida, "Hilo3", 300);
        HiloConCuentaCompartida hilo4 = new HiloConCuentaCompartida(cuentaCompartida, "Hilo4", 400);
        hilo1.start();
        hilo2.start();
        hilo3.start();
        hilo4.start();
        Thread.sleep(8100);
        System.out.println("Saldo final:" + cuentaCompartida.getSaldo());

    }
}