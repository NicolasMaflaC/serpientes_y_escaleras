import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EscalerasSerpientesGUI extends JFrame {

    private Tablero tablero;
    private ArrayList<Jugador> jugadores;
    private int turno = 0;

    private JPanel panelTablero;
    private JButton botonDado;
    private JLabel labelTurno;
    private JLabel[] labelCasillas;

    public EscalerasSerpientesGUI(Tablero tablero, ArrayList<Jugador> jugadores) {
        this.tablero = tablero;
        this.jugadores = jugadores;

        setTitle("Escaleras y Serpientes");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        panelTablero = new JPanel(new GridLayout(tablero.getFilas(), tablero.getColumnas()));
        labelCasillas = new JLabel[tablero.getNumCasillas()];
        for (int i = 0; i < tablero.getNumCasillas(); i++) {
            labelCasillas[i] = new JLabel("" + (i + 1), SwingConstants.CENTER);
            labelCasillas[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            panelTablero.add(labelCasillas[i]);
        }
        add(panelTablero, BorderLayout.CENTER);

        JPanel panelControl = new JPanel();
        labelTurno = new JLabel("Turno del Jugador 1");
        panelControl.add(labelTurno);

        botonDado = new JButton("Lanzar Dado");
        botonDado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jugarTurno();
            }
        });
        panelControl.add(botonDado);

        add(panelControl, BorderLayout.SOUTH);
    }

    private void jugarTurno() {
        int dado = tablero.generaNumeroAleatorio(1, 6);
        JOptionPane.showMessageDialog(this, "El jugador " + jugadores.get(turno).getNombre() + " ha sacado un " + dado);

        int nuevaPosicion = jugadores.get(turno).getPosicion() + dado;
        if (nuevaPosicion >= tablero.getNumCasillas()) {
            nuevaPosicion = tablero.getNumCasillas() - 1;
        }
        nuevaPosicion = tablero.aplicarCasillaEspecial(nuevaPosicion);

        jugadores.get(turno).setPosicion(nuevaPosicion);

        actualizarTablero();

        if (nuevaPosicion == tablero.getNumCasillas() - 1) {
            JOptionPane.showMessageDialog(this, "El jugador " + jugadores.get(turno).getNombre() + " ha ganado");
            System.exit(0);
        }

        if (dado != 6) {
            turno = (turno + 1) % jugadores.size();
        }

        labelTurno.setText("Turno del Jugador " + jugadores.get(turno).getNombre());
    }

    private void actualizarTablero() {
        for (int i = 0; i < tablero.getNumCasillas(); i++) {
            labelCasillas[i].setBackground(null);
            labelCasillas[i].setOpaque(true);
        }
        for (Jugador jugador : jugadores) {
            labelCasillas[jugador.getPosicion()].setBackground(jugador.getColor());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            int filas = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de filas:"));
            int columnas = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de columnas:"));
            int numJugadores = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de jugadores (entre 2 y 4):"));

            Tablero tablero = new Tablero(filas, columnas);
            ArrayList<Jugador> jugadores = new ArrayList<>();
            for (int i = 0; i < numJugadores; i++) {
                jugadores.add(new Jugador("Jugador " + (i + 1), getColorJugador(i)));
            }

            EscalerasSerpientesGUI frame = new EscalerasSerpientesGUI(tablero, jugadores);
            frame.setVisible(true);
        });
    }

    private static Color getColorJugador(int jugador) {
        switch (jugador) {
            case 0:
                return Color.RED;
            case 1:
                return Color.BLUE;
            case 2:
                return Color.GREEN;
            case 3:
                return Color.YELLOW;
            default:
                return Color.BLACK;
        }
    }
}
