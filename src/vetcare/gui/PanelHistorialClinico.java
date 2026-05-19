package vetcare.gui;

import vetcare.data.Veterinaria;
import vetcare.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import static vetcare.gui.UIConstantes.*;

public class PanelHistorialClinico extends JPanel {

    private JComboBox<Mascota> cmbMascota;
    private JTable tablaHistorial;
    private DefaultTableModel modeloTabla;
    private JTextArea txtResumen;

    public PanelHistorialClinico() {
        setLayout(new BorderLayout(15, 15));
        setBackground(BG_LIGHT);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        initComponents();
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            actualizarComboMascotas();
        }
    }

    private void initComponents() {
        JPanel panelSeleccion = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 8));
        panelSeleccion.setBackground(Color.WHITE);
        panelSeleccion.setBorder(bordeTitulado("Seleccionar Paciente"));

        JLabel lblMascota = new JLabel("Mascota:");
        lblMascota.setFont(FONT_LABEL);
        panelSeleccion.add(lblMascota);

        cmbMascota = new JComboBox<>();
        cmbMascota.setFont(FONT_LABEL);
        cmbMascota.setBackground(INPUT_BG);
        cmbMascota.setPreferredSize(new Dimension(280, 30));
        panelSeleccion.add(cmbMascota);

        JButton btnConsultar = new JButton("Consultar Historial");
        estiloBoton(btnConsultar, SECONDARY);
        panelSeleccion.add(btnConsultar);

        JButton btnRefrescar = new JButton("Refrescar");
        estiloBoton(btnRefrescar, PRIMARY);
        panelSeleccion.add(btnRefrescar);

        add(panelSeleccion, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(
            new String[]{"ID Cita", "Fecha", "Hora", "Motivo", "Diagn\u00f3stico", "Estado"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tablaHistorial = new JTable(modeloTabla);
        estiloTabla(tablaHistorial);
        JScrollPane scrollTabla = new JScrollPane(tablaHistorial);
        scrollTabla.setBorder(bordeTitulado("Citas y Diagnosticos"));
        scrollTabla.getViewport().setBackground(Color.WHITE);
        add(scrollTabla, BorderLayout.CENTER);

        txtResumen = new JTextArea(4, 50);
        txtResumen.setEditable(false);
        txtResumen.setFont(FONT_MONO);
        txtResumen.setBackground(new Color(250, 250, 250));
        txtResumen.setBorder(BorderFactory.createCompoundBorder(
            bordeTitulado("Resumen del Paciente"),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        JScrollPane scrollResumen = new JScrollPane(txtResumen);
        scrollResumen.setPreferredSize(new Dimension(0, 130));
        add(scrollResumen, BorderLayout.SOUTH);

        btnConsultar.addActionListener(e -> consultarHistorial());
        btnRefrescar.addActionListener(e -> actualizarComboMascotas());

        actualizarComboMascotas();
    }

    private void consultarHistorial() {
        modeloTabla.setRowCount(0);
        Mascota mascota = (Mascota) cmbMascota.getSelectedItem();
        if (mascota == null) {
            JOptionPane.showMessageDialog(this,
                "No hay mascotas registradas.", "Sin datos", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Veterinaria vet = Veterinaria.getInstancia();
        Cliente dueno = vet.buscarCliente(mascota.getIdDueno());
        String nombreDueno = (dueno != null) ? dueno.getNombre() : "Desconocido";

        int totalCitas = 0;
        int completadas = 0;

        for (Cita c : vet.getCitasPorMascota(mascota.getId())) {
            modeloTabla.addRow(new Object[]{
                c.getId(), c.getFecha(), c.getHora(),
                c.getMotivo(), c.getDiagnostico(), c.getEstado()
            });
            totalCitas++;
            if (c.getEstado().equals(Cita.ESTADO_COMPLETADA)) {
                completadas++;
            }
        }

        StringBuilder resumen = new StringBuilder();
        resumen.append("  Paciente: ").append(mascota.getNombre());
        resumen.append("  |  Especie: ").append(mascota.getEspecie());
        resumen.append("  |  Raza: ").append(mascota.getRaza());
        resumen.append("  |  Edad: ").append(mascota.getEdad()).append(" a\u00f1os");
        resumen.append("  |  Sexo: ").append(mascota.getSexo().name());
        resumen.append("  |  Due\u00f1o: ").append(nombreDueno);
        resumen.append("\n  Total citas: ").append(totalCitas);
        resumen.append("  |  Completadas: ").append(completadas);
        resumen.append("  |  Pendientes: ").append(totalCitas - completadas);

        if (totalCitas == 0) {
            resumen.append("\n  Este paciente no tiene citas registradas a\u00fan.");
        }

        txtResumen.setText(resumen.toString());
    }

    public void actualizarComboMascotas() {
        cmbMascota.removeAllItems();
        for (Mascota m : Veterinaria.getInstancia().getMascotas()) {
            cmbMascota.addItem(m);
        }
    }
}
