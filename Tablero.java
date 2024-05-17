import javax.swing.JOptionPane;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Random;

public class Tablero {
    private int filas;
    private int columnas;
    private int numCasillas;
    private Map<Integer, Integer> casillasEspeciales;

    public Tablero(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.numCasillas = filas * columnas;
        casillasEspeciales = new HashMap<>();
        inicializarCasillasEspeciales();
    }

    private void inicializarCasillasEspeciales() {
        // Definir casillas especiales (escaleras y serpientes)
        ArrayList<Integer> posiciones = generarPosicionesAleatorias(0, numCasillas - 1, numCasillas / 10); // Aproximadamente el 10% de las casillas serán especiales
        Random random = new Random();
        for (int i = 0; i < posiciones.size(); i++) {
            int posicion = posiciones.get(i);
            int tipo = random.nextInt(2); // 0 para serpiente, 1 para escalera
            int destino = tipo == 0 ? posicion - random.nextInt(posicion) : posicion + random.nextInt(numCasillas - posicion);
            casillasEspeciales.put(posicion + 1, destino + 1);
        }
    }

    private ArrayList<Integer> generarPosicionesAleatorias(int minimo, int maximo, int cantidad) {
        ArrayList<Integer> posiciones = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < cantidad; i++) {
            int posicion = random.nextInt(maximo - minimo + 1) + minimo;
            if (!posiciones.contains(posicion)) {
                posiciones.add(posicion);
            } else {
                i--;
            }
        }
        return posiciones;
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public int getNumCasillas() {
        return numCasillas;
    }

    public int generaNumeroAleatorio(int minimo, int maximo) {
        return (int) (Math.random() * (maximo - minimo + 1) + minimo);
    }

    public int aplicarCasillaEspecial(int posicion) {
        if (casillasEspeciales.containsKey(posicion + 1)) {
            int destino = casillasEspeciales.get(posicion + 1);
            if (destino > posicion + 1) {
                JOptionPane.showMessageDialog(null, "El jugador ha encontrado una escalera!");
            } else {
                JOptionPane.showMessageDialog(null, "El jugador ha encontrado una serpiente!");
            }
            return destino - 1;
        }
        return posicion;
    }
}
