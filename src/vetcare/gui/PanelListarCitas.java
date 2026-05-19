package vetcare.gui;

import vetcare.data.Veterinaria;
import vetcare.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import static vetcare.gui.UIConstantes.*;

public class PanelListarCitas extends JPanel {

    private JComboBox<String> cmbFiltro;
    private JTable tablaCitas;
    private DefaultTableModel modeloTabla;
    private JTextArea txtDetalle;

    public PanelListarCitas() {
        setLayout(new BorderLayout(15, 15));
        setBackground(BG_LIGHT);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        initComponents();
        cargarTabla();
    }

    private void initComponents() {
        JPanel panelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 8));
        panelFiltro.setBackground(Color.WHITE);
        panelFiltro.setBorder(bordeTitulado("Filtros"));

        JLabel lblMostrar = new JLabel("Mostrar:");
        lblMostrar.setFont(FONT_LABEL);
        panelFiltro.add(lblMostrar);
        cmbFiltro = crearCombo(new String[]{"Todas", "Solo Programadas", "Solo Completadas", "Solo Canceladas"});
        cmbFiltro.setPreferredSize(new Dimension(180, 30));
        panelFiltro.add(cmbFiltro);

        JButton btnFiltrar = new JButton("Filtrar");
        estiloBoton(btnFiltrar, SECONDARY);
        JButton btnRefrescar = new JButton("Refrescar");
        estiloBoton(btnRefrescar, PRIMARY);
        panelFiltro.add(btnFiltrar);
        panelFiltro.add(btnRefrescar);

        add(panelFiltro, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(
            new String[]{"ID", "Mascota", "Due\u00f1o", "Fecha", "Hora", "Motivo", "Estado", "Diagn\u00f3stico"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tablaCitas = new JTable(modeloTabla);
        estiloTabla(tablaCitas);
        tablaCitas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                mostrarDetalle();
            }
        });

        JScrollPane scrollTabla = new JScrollPane(tablaCitas);
        scrollTabla.setBorder(bordeTitulado("Citas"));
        scrollTabla.getViewport().setBackground(Color.WHITE);
        add(scrollTabla, BorderLayout.CENTER);

        txtDetalle = new JTextArea(6, 50);
        txtDetalle.setEditable(false);
        txtDetalle.setFont(FONT_MONO);
        txtDetalle.setBackground(new Color(250, 250, 250));
        txtDetalle.setBorder(BorderFactory.createCompoundBorder(
            bordeTitulado("Detalle de la Cita"),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        JScrollPane scrollDetalle = new JScrollPane(txtDetalle);
        scrollDetalle.setPreferredSize(new Dimension(0, 180));
        add(scrollDetalle, BorderLayout.SOUTH);

        btnFiltrar.addActionListener(e -> filtrar());
        btnRefrescar.addActionListener(e -> cargarTabla());
    }

    private void filtrar() {
        String seleccion = (String) cmbFiltro.getSelectedItem();
        modeloTabla.setRowCount(0);
        Veterinaria vet = Veterinaria.getInstancia();

        for (Cita c : vet.getCitas()) {
            boolean incluir = false;
            switch (seleccion) {
                case "Todas": incluir = true; break;
                case "Solo Programadas": incluir = c.getEstado().equals(Cita.ESTADO_PROGRAMADA); break;
                case "Solo Completadas": incluir = c.getEstado().equals(Cita.ESTADO_COMPLETADA); break;
                case "Solo Canceladas": incluir = c.getEstado().equals(Cita.ESTADO_CANCELADA); break;
            }
            if (incluir) {
                Mascota m = vet.buscarMascota(c.getIdMascota());
                Cliente d = (m != null) ? vet.buscarCliente(m.getIdDueno()) : null;
                String nombreMascota = (m != null) ? m.getNombre() : "Desconocida";
                String nombreDueno = (d != null) ? d.getNombre() : "Desconocido";
                modeloTabla.addRow(new Object[]{
                    c.getId(), nombreMascota, nombreDueno, c.getFecha(), c.getHora(),
                    c.getMotivo(), c.getEstado(), c.getDiagnostico()
                });
            }
        }
    }

    private void mostrarDetalle() {
        int fila = tablaCitas.getSelectedRow();
        if (fila < 0) {
            txtDetalle.setText("Seleccione una cita de la tabla para ver su detalle.");
            return;
        }
        try {
            int idCita = (int) modeloTabla.getValueAt(fila, 0);
            Cita c = Veterinaria.getInstancia().buscarCita(idCita);
            if (c == null) return;

            Mascota m = Veterinaria.getInstancia().buscarMascota(c.getIdMascota());
            Cliente d = (m != null) ? Veterinaria.getInstancia().buscarCliente(m.getIdDueno()) : null;

            StringBuilder sb = new StringBuilder();
            sb.append("  CITA #").append(c.getId());
            sb.append("  |  Estado: ").append(c.getEstado());
            sb.append("\n  Paciente: ").append(m != null ? m.getNombre() : "?");
            sb.append("  |  Especie: ").append(m != null ? m.getEspecie() : "?");
            sb.append("  |  Due\u00f1o: ").append(d != null ? d.getNombre() : "?");
            sb.append("\n  Fecha: ").append(c.getFecha());
            sb.append("  |  Hora: ").append(c.getHora());
            sb.append("\n  Motivo: ").append(c.getMotivo());
            if (c.getDiagnostico() != null && !c.getDiagnostico().isEmpty()) {
                sb.append("\n  Diagn\u00f3stico: ").append(c.getDiagnostico());
            }
            txtDetalle.setText(sb.toString());
        } catch (Exception e) {
            txtDetalle.setText("Error al cargar detalle.");
        }
    }

    public void cargarTabla() {
        cmbFiltro.setSelectedIndex(0);
        filtrar();
    }
}
