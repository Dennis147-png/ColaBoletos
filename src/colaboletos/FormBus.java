package colaboletos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormBus {
    private JPanel Ventana;
    private JComboBox cBoxRuta;
    private JTextField textCedula;
    private JTextField textNombre;
    private JTextField textCantidad;
    private JButton comprarButton;
    private JButton embarcarButton;
    private JLabel lblVendidosGye;
    private JLabel lblVendidosCue;
    private JLabel lblVendidosLoj;
    private JLabel lblDispGye;
    private JLabel lblDispCue;
    private JLabel lblDispLoj;
    private JLabel lblTotal;
    private JTextArea textArea1;

    public Bus[] buses = {
            new Bus("QUITO - GUAYAQUIL", 10.50),
            new Bus("QUITO - CUENCA", 12.75),
            new Bus("QUITO - LOJA", 15.00)
    };

    public FormBus() {
        textArea1.setEditable(false);

        comprarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Bus bus = buses[cBoxRuta.getSelectedIndex()];
                    String cedula = textCedula.getText().trim();
                    String nombre = textNombre.getText().trim();

                    if (!cedula.matches("\\d{10}")) {
                        throw new Exception("La cedula debe tener 10 digitos numericos");
                    }
                    if (nombre.isEmpty()) {
                        throw new Exception("Ingrese el nombre del pasajero");
                    }
                    int cantidad = Integer.parseInt(textCantidad.getText().trim());

                    Pasajero p = new Pasajero(cedula, nombre, cantidad, bus.getRuta(), bus.getPrecio());
                    bus.encolar(p);

                    JOptionPane.showMessageDialog(null, "Compra realizada\n" + p);
                    textCedula.setText("");
                    textNombre.setText("");
                    textCantidad.setText("");
                    actualizar();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "El numero de boletos debe ser un numero entero");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });
        embarcarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Bus bus = buses[cBoxRuta.getSelectedIndex()];
                    Pasajero p = bus.embarcar();
                    JOptionPane.showMessageDialog(null, "Embarco:\n" + p);
                    actualizar();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });
    }

    private void actualizar() {
        lblVendidosGye.setText("" + buses[0].getVendidos());
        lblVendidosCue.setText("" + buses[1].getVendidos());
        lblVendidosLoj.setText("" + buses[2].getVendidos());
        lblDispGye.setText("" + buses[0].getDisponibles());
        lblDispCue.setText("" + buses[1].getDisponibles());
        lblDispLoj.setText("" + buses[2].getDisponibles());

        double total = 0;
        StringBuilder sb = new StringBuilder();
        for (Bus b : buses) {
            total += b.getRecaudado();
            sb.append("--- " + b.getRuta() + " (en espera de embarcar) ---\n");
            sb.append(b.sacarCola());
        }
        lblTotal.setText("$" + String.format("%.2f", total));
        textArea1.setText(sb.toString());
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Venta de boletos");
        frame.setContentPane(new FormBus().Ventana);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
