package vetcare.gui;

import vetcare.data.Veterinaria;
import vetcare.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static vetcare.gui.UIConstantes.*;

public class PanelAgendarCita extends JPanel {

    private JComboBox<Mascota> cmbMascota;
    private JTextField txtFecha, txtHora;
    private JTextArea txtMotivo;
    private JButton btnAgendar, btnEditar, btnLimpiar;
    private JTable tablaCitas;
    private DefaultTableModel modeloTabla;
    private JTextField txtIdSel;
    private JTextField txtDiagnostico;
    private JButton btnCompletar, btnCancelar;
    private int idEditando = -1;

    public PanelAgendarCita() {
        setLayout(new BorderLayout(10, 10));
        setBackground(BG_LIGHT);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        initComponents();
        cargarTabla();
    }

    private void initComponents() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(bordeTitulado("Agendar / Editar Cita"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 8, 5, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblMascota = new JLabel("Mascota:");
        lblMascota.setFont(FONT_LABEL);
        cmbMascota = new JComboBox<>();
        cmbMascota.setFont(FONT_LABEL);

        JLabel lblFecha = new JLabel("Fecha (AAAA-MM-DD):");
        lblFecha.setFont(FONT_LABEL);
        txtFecha = crearCampoTexto(20);
        txtFecha.setText(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));

        JLabel lblHora = new JLabel("Hora (HH:MM):");
        lblHora.setFont(FONT_LABEL);
        txtHora = crearCampoTexto(20);

        JLabel lblMotivo = new JLabel("Motivo:");
        lblMotivo.setFont(FONT_LABEL);
        txtMotivo = crearAreaTexto(3, 20);
        JScrollPane spMotivo = new JScrollPane(txtMotivo);
        spMotivo.setPreferredSize(new Dimension(0, 65));
        spMotivo.setBorder(BorderFactory.createLineBorder(BORDER));

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(lblMascota, gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        formPanel.add(cmbMascota, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        formPanel.add(lblFecha, gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        formPanel.add(txtFecha, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        formPanel.add(lblHora, gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        formPanel.add(txtHora, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0; gbc.anchor = GridBagConstraints.NORTHWEST;
        formPanel.add(lblMotivo, gbc);
        gbc.gridx = 1; gbc.weightx = 1; gbc.fill = GridBagConstraints.BOTH;
        formPanel.add(spMotivo, gbc);

        btnAgendar = new JButton("Agendar Cita");
        estiloBoton(btnAgendar, SUCCESS);
        btnEditar = new JButton("Editar");
        estiloBoton(btnEditar, WARNING);
        btnEditar.setEnabled(false);
        btnLimpiar = new JButton("Limpiar");
        estiloBoton(btnLimpiar, SECONDARY);

        JPanel filaBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 0));
        filaBtn.setBackground(Color.WHITE);
        filaBtn.add(btnAgendar);
        filaBtn.add(btnEditar);
        filaBtn.add(btnLimpiar);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER; gbc.weightx = 0; gbc.insets = new Insets(8, 8, 6, 8);
        formPanel.add(filaBtn, gbc);

        formPanel.setPreferredSize(new Dimension(380, 0));
        add(formPanel, BorderLayout.WEST);

        modeloTabla = new DefaultTableModel(
            new String[]{"ID", "Mascota", "Fecha", "Hora", "Motivo", "Estado", "Diagnostico"}, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        tablaCitas = new JTable(modeloTabla);
        estiloTabla(tablaCitas);
        tablaCitas.setRowSelectionAllowed(true);
        tablaCitas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaCitas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) actualizarSeleccion();
        });
        JScrollPane spTabla = new JScrollPane(tablaCitas);
        spTabla.setBorder(bordeTitulado("Citas Registradas"));
        spTabla.getViewport().setBackground(Color.WHITE);
        add(spTabla, BorderLayout.CENTER);

        JPanel bottomBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        bottomBar.setBackground(new Color(250, 250, 250));
        bottomBar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));

        bottomBar.add(new JLabel("ID:"));
        txtIdSel = new JTextField(6);
        txtIdSel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        txtIdSel.setEditable(false);
        txtIdSel.setHorizontalAlignment(JTextField.CENTER);
        txtIdSel.setBackground(new Color(255, 255, 220));
        txtIdSel.setBorder(BorderFactory.createLineBorder(new Color(200, 180, 0)));
        txtIdSel.setText("---");
        bottomBar.add(txtIdSel);

        bottomBar.add(Box.createHorizontalStrut(12));
        bottomBar.add(new JLabel("Diagnostico:"));

        txtDiagnostico = crearCampoTexto(25);
        bottomBar.add(txtDiagnostico);

        bottomBar.add(Box.createHorizontalStrut(10));
        btnCompletar = new JButton("Completar");
        estiloBoton(btnCompletar, SECONDARY);
        btnCompletar.setEnabled(false);
        bottomBar.add(btnCompletar);

        btnCancelar = new JButton("Cancelar Cita");
        estiloBoton(btnCancelar, DANGER);
        btnCancelar.setEnabled(false);
        bottomBar.add(btnCancelar);

        add(bottomBar, BorderLayout.SOUTH);

        btnAgendar.addActionListener(e -> guardarCita());
        btnEditar.addActionListener(e -> editarCita());
        btnCompletar.addActionListener(e -> completarCita());
        btnCancelar.addActionListener(e -> cancelarCita());
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        actualizarComboMascotas();
    }

    private void actualizarSeleccion() {
        int fila = tablaCitas.getSelectedRow();
        if (fila >= 0) {
            int mr = tablaCitas.convertRowIndexToModel(fila);
            int id = (int) modeloTabla.getValueAt(mr, 0);
            txtIdSel.setText(String.valueOf(id));

            Cita c = Veterinaria.getInstancia().buscarCita(id);
            if (c != null) {
                txtDiagnostico.setText(c.getDiagnostico() != null && !c.getDiagnostico().equals("null") ? c.getDiagnostico() : "");
                boolean prog = c.getEstado().equals(Cita.ESTADO_PROGRAMADA);
                btnEditar.setEnabled(prog);
                btnCompletar.setEnabled(prog);
                btnCancelar.setEnabled(prog);
            }
        } else {
            txtIdSel.setText("---");
            txtDiagnostico.setText("");
            btnEditar.setEnabled(false);
            btnCompletar.setEnabled(false);
            btnCancelar.setEnabled(false);
        }
    }

    private void guardarCita() {
        try {
            Mascota mascota = (Mascota) cmbMascota.getSelectedItem();
            if (mascota == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar una mascota.", "Campo requerido", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String fecha = txtFecha.getText().trim();
            String hora = txtHora.getText().trim();
            String motivo = txtMotivo.getText().trim();

            if (fecha.isEmpty()) { JOptionPane.showMessageDialog(this, "La fecha es obligatoria.", "Campo requerido", JOptionPane.WARNING_MESSAGE); txtFecha.requestFocus(); return; }
            if (hora.isEmpty()) { JOptionPane.showMessageDialog(this, "La hora es obligatoria.", "Campo requerido", JOptionPane.WARNING_MESSAGE); txtHora.requestFocus(); return; }
            if (motivo.isEmpty()) { JOptionPane.showMessageDialog(this, "El motivo es obligatorio.", "Campo requerido", JOptionPane.WARNING_MESSAGE); txtMotivo.requestFocus(); return; }

            try { LocalDate.parse(fecha, DateTimeFormatter.ISO_LOCAL_DATE); }
            catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "La fecha debe tener el formato AAAA-MM-DD.", "Formato invalido", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!hora.matches("\\d{2}:\\d{2}")) {
                JOptionPane.showMessageDialog(this, "La hora debe tener el formato HH:MM.", "Formato invalido", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (Veterinaria.getInstancia().existeCitaEnHorario(mascota.getId(), fecha, hora, idEditando == -1 ? null : idEditando)) {
                JOptionPane.showMessageDialog(this,
                    "Ya existe una cita agendada para esta mascota en la misma fecha y hora.\nSeleccione otro horario.",
                    "Conflicto de horario", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (idEditando == -1) {
                int id = Veterinaria.getInstancia().generarIdCita();
                Cita cita = new Cita(id, mascota.getId(), fecha, hora, motivo);
                Veterinaria.getInstancia().agregarCita(cita);
                modeloTabla.addRow(new Object[]{id, mascota.getNombre(), fecha, hora, motivo, Cita.ESTADO_PROGRAMADA, ""});
                JOptionPane.showMessageDialog(this, "Cita agendada con ID: " + id, "Exito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                Cita c = Veterinaria.getInstancia().buscarCita(idEditando);
                if (c == null) return;
                c.setIdMascota(mascota.getId());
                c.setFecha(fecha);
                c.setHora(hora);
                c.setMotivo(motivo);
                actualizarFilaEnTabla(idEditando, mascota.getNombre(), fecha, hora, motivo, c.getEstado(), c.getDiagnostico());
                JOptionPane.showMessageDialog(this, "Cita actualizada.", "Exito", JOptionPane.INFORMATION_MESSAGE);
                idEditando = -1;
                btnAgendar.setText("Agendar Cita");
                estiloBoton(btnAgendar, SUCCESS);
            }
            limpiarFormulario();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editarCita() {
        int fila = tablaCitas.getSelectedRow();
        if (fila < 0) return;
        int idCita = (int) modeloTabla.getValueAt(tablaCitas.convertRowIndexToModel(fila), 0);
        Cita c = Veterinaria.getInstancia().buscarCita(idCita);
        if (c == null) return;
        if (!c.getEstado().equals(Cita.ESTADO_PROGRAMADA)) {
            JOptionPane.showMessageDialog(this, "Solo se pueden editar citas Programadas.", "Accion no permitida", JOptionPane.WARNING_MESSAGE);
            return;
        }
        idEditando = idCita;
        String nomMasc = (String) modeloTabla.getValueAt(tablaCitas.convertRowIndexToModel(fila), 1);
        for (int i = 0; i < cmbMascota.getItemCount(); i++) {
            if (cmbMascota.getItemAt(i).getNombre().equals(nomMasc)) {
                cmbMascota.setSelectedIndex(i);
                break;
            }
        }
        txtFecha.setText(c.getFecha());
        txtHora.setText(c.getHora());
        txtMotivo.setText(c.getMotivo());
        btnAgendar.setText("Guardar Cambios");
        estiloBoton(btnAgendar, WARNING);
    }

    private void completarCita() {
        String idStr = txtIdSel.getText().trim();
        if (idStr.isEmpty() || idStr.equals("---")) {
            JOptionPane.showMessageDialog(this, "Seleccione una cita de la tabla.", "Sin seleccion", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            int idCita = Integer.parseInt(idStr);
            Cita c = Veterinaria.getInstancia().buscarCita(idCita);
            if (c == null) { JOptionPane.showMessageDialog(this, "Cita no encontrada.", "Error", JOptionPane.ERROR_MESSAGE); return; }
            if (!c.getEstado().equals(Cita.ESTADO_PROGRAMADA)) {
                JOptionPane.showMessageDialog(this, "Solo se pueden completar citas Programadas.", "Accion no permitida", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String diag = txtDiagnostico.getText().trim();
            if (diag.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar un diagnostico.", "Campo requerido", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Veterinaria.getInstancia().actualizarDiagnostico(idCita, diag);
            JOptionPane.showMessageDialog(this, "Cita completada.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            cargarTabla();
            txtDiagnostico.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID invalido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelarCita() {
        String idStr = txtIdSel.getText().trim();
        if (idStr.isEmpty() || idStr.equals("---")) {
            JOptionPane.showMessageDialog(this, "Seleccione una cita de la tabla.", "Sin seleccion", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            int idCita = Integer.parseInt(idStr);
            Cita c = Veterinaria.getInstancia().buscarCita(idCita);
            if (c == null) { JOptionPane.showMessageDialog(this, "Cita no encontrada.", "Error", JOptionPane.ERROR_MESSAGE); return; }
            if (!c.getEstado().equals(Cita.ESTADO_PROGRAMADA)) {
                JOptionPane.showMessageDialog(this, "Solo se pueden cancelar citas Programadas.", "Accion no permitida", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int r = JOptionPane.showConfirmDialog(this, "Cancelar cita ID " + idCita + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (r != JOptionPane.YES_OPTION) return;
            Veterinaria.getInstancia().cancelarCita(idCita);
            JOptionPane.showMessageDialog(this, "Cita cancelada.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            cargarTabla();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID invalido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        txtFecha.setText(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        txtHora.setText("");
        txtMotivo.setText("");
        if (idEditando != -1) {
            idEditando = -1;
            btnAgendar.setText("Agendar Cita");
            estiloBoton(btnAgendar, SUCCESS);
        }
    }

    private void actualizarFilaEnTabla(int id, String masc, String fecha, String hora, String motivo, String est, String diag) {
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            if ((int) modeloTabla.getValueAt(i, 0) == id) {
                modeloTabla.setValueAt(masc, i, 1);
                modeloTabla.setValueAt(fecha, i, 2);
                modeloTabla.setValueAt(hora, i, 3);
                modeloTabla.setValueAt(motivo, i, 4);
                modeloTabla.setValueAt(est, i, 5);
                modeloTabla.setValueAt(diag, i, 6);
                break;
            }
        }
    }

    public void actualizarComboMascotas() {
        cmbMascota.removeAllItems();
        for (Mascota m : Veterinaria.getInstancia().getMascotas()) cmbMascota.addItem(m);
    }

    public void cargarTabla() {
        modeloTabla.setRowCount(0);
        for (Cita c : Veterinaria.getInstancia().getCitas()) {
            Mascota m = Veterinaria.getInstancia().buscarMascota(c.getIdMascota());
            String nom = (m != null) ? m.getNombre() : "Desconocida";
            modeloTabla.addRow(new Object[]{c.getId(), nom, c.getFecha(), c.getHora(), c.getMotivo(), c.getEstado(), c.getDiagnostico()});
        }
        txtIdSel.setText("---");
        txtDiagnostico.setText("");
        btnEditar.setEnabled(false);
        btnCompletar.setEnabled(false);
        btnCancelar.setEnabled(false);
    }
}
