package vetcare.gui;

import vetcare.data.Veterinaria;
import vetcare.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import static vetcare.gui.UIConstantes.*;

public class PanelRegistroMascota extends JPanel {

    private JTextField txtNombre, txtRaza, txtEdad;
    private JComboBox<String> cmbEspecie;
    private JRadioButton rbMacho, rbHembra;
    private ButtonGroup grupoSexo;
    private JComboBox<Cliente> cmbDueno;
    private JButton btnRegistrar, btnLimpiar, btnEditar, btnEliminar;
    private JTable tablaMascotas;
    private DefaultTableModel modeloTabla;
    private int idEditando = -1;

    public PanelRegistroMascota() {
        setLayout(new BorderLayout(15, 15));
        setBackground(BG_LIGHT);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        initComponents();
        cargarTabla();
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            actualizarComboDuenos();
            cargarTabla();
        }
    }

    private void initComponents() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(bordeTitulado("Registrar Nueva Mascota"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(FONT_LABEL);
        txtNombre = crearCampoTexto(22);
        JLabel lblEspecie = new JLabel("Especie:");
        lblEspecie.setFont(FONT_LABEL);
        cmbEspecie = crearCombo(new String[]{"Perro", "Gato", "Ave", "Roedor", "Otro"});
        JLabel lblRaza = new JLabel("Raza:");
        lblRaza.setFont(FONT_LABEL);
        txtRaza = crearCampoTexto(22);
        JLabel lblEdad = new JLabel("Edad (años):");
        lblEdad.setFont(FONT_LABEL);
        txtEdad = crearCampoTexto(22);
        JLabel lblSexo = new JLabel("Sexo:");
        lblSexo.setFont(FONT_LABEL);
        JLabel lblDueno = new JLabel("Dueño:");
        lblDueno.setFont(FONT_LABEL);

        rbMacho = new JRadioButton("Macho");
        rbMacho.setFont(FONT_LABEL);
        rbMacho.setBackground(Color.WHITE);
        rbHembra = new JRadioButton("Hembra");
        rbHembra.setFont(FONT_LABEL);
        rbHembra.setBackground(Color.WHITE);
        grupoSexo = new ButtonGroup();
        grupoSexo.add(rbMacho);
        grupoSexo.add(rbHembra);
        rbMacho.setSelected(true);

        cmbDueno = new JComboBox<>();
        cmbDueno.setFont(FONT_LABEL);
        cmbDueno.setBackground(INPUT_BG);

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(lblNombre, gbc);
        gbc.gridx = 1;
        formPanel.add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(lblEspecie, gbc);
        gbc.gridx = 1;
        formPanel.add(cmbEspecie, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(lblRaza, gbc);
        gbc.gridx = 1;
        formPanel.add(txtRaza, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(lblEdad, gbc);
        gbc.gridx = 1;
        formPanel.add(txtEdad, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(lblSexo, gbc);
        gbc.gridx = 1;
        JPanel sexoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        sexoPanel.setBackground(Color.WHITE);
        sexoPanel.add(rbMacho);
        sexoPanel.add(rbHembra);
        formPanel.add(sexoPanel, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(lblDueno, gbc);
        gbc.gridx = 1;
        formPanel.add(cmbDueno, gbc);

        btnRegistrar = new JButton("Registrar Mascota");
        estiloBoton(btnRegistrar, SUCCESS);
        btnLimpiar = new JButton("Limpiar");
        estiloBoton(btnLimpiar, SECONDARY);
        btnEditar = new JButton("Editar");
        estiloBoton(btnEditar, WARNING);
        btnEditar.setEnabled(false);
        btnEliminar = new JButton("Eliminar");
        estiloBoton(btnEliminar, DANGER);
        btnEliminar.setEnabled(false);

        JPanel filaBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 0));
        filaBotones.setBackground(Color.WHITE);
        filaBotones.add(btnRegistrar);
        filaBotones.add(btnEditar);
        filaBotones.add(btnEliminar);
        filaBotones.add(btnLimpiar);

        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(filaBotones, gbc);

        add(formPanel, BorderLayout.WEST);

        modeloTabla = new DefaultTableModel(
            new String[]{"ID", "Nombre", "Especie", "Raza", "Edad", "Sexo", "Dueño"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tablaMascotas = new JTable(modeloTabla);
        estiloTabla(tablaMascotas);
        tablaMascotas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                boolean haySeleccion = tablaMascotas.getSelectedRow() >= 0;
                btnEditar.setEnabled(haySeleccion);
                btnEliminar.setEnabled(haySeleccion);
            }
        });
        JScrollPane scroll = new JScrollPane(tablaMascotas);
        scroll.setBorder(bordeTitulado("Mascotas Registradas"));
        scroll.setPreferredSize(new Dimension(520, 0));
        scroll.getViewport().setBackground(Color.WHITE);
        add(scroll, BorderLayout.CENTER);

        btnRegistrar.addActionListener(e -> guardarMascota());
        btnEditar.addActionListener(e -> editarMascota());
        btnEliminar.addActionListener(e -> eliminarMascota());
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        actualizarComboDuenos();
    }

    private void guardarMascota() {
        try {
            String nombre = txtNombre.getText().trim();
            String especie = (String) cmbEspecie.getSelectedItem();
            String raza = txtRaza.getText().trim();
            String edadStr = txtEdad.getText().trim();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "El nombre de la mascota es obligatorio.",
                    "Campo requerido", JOptionPane.WARNING_MESSAGE);
                txtNombre.requestFocus();
                return;
            }

            if (edadStr.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "La edad es obligatoria.",
                    "Campo requerido", JOptionPane.WARNING_MESSAGE);
                txtEdad.requestFocus();
                return;
            }

            int edad;
            try {
                edad = Integer.parseInt(edadStr);
                if (edad < 0 || edad > 50) {
                    JOptionPane.showMessageDialog(this,
                        "La edad debe ser un numero entre 0 y 50.",
                        "Edad invalida", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                    "La edad debe ser un numero entero valido.",
                    "Error de formato", JOptionPane.ERROR_MESSAGE);
                txtEdad.requestFocus();
                return;
            }
            if (raza.isEmpty()) {
                JOptionPane.showMessageDialog(this, "La raza es obligatoria.", "Campo requerido", JOptionPane.WARNING_MESSAGE);
                txtRaza.requestFocus(); return;
            }

            Sexo sexo = rbMacho.isSelected() ? Sexo.MACHO : Sexo.HEMBRA;

            Cliente dueno = (Cliente) cmbDueno.getSelectedItem();
            if (dueno == null) {
                JOptionPane.showMessageDialog(this,
                    "Debe seleccionar un dueño para la mascota.",
                    "Campo requerido", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (idEditando == -1) {
                int id = Veterinaria.getInstancia().generarIdMascota();
                Mascota m = new Mascota(id, nombre, especie, raza, edad, sexo, dueno.getId());
                Veterinaria.getInstancia().agregarMascota(m);
                modeloTabla.addRow(new Object[]{id, nombre, especie, raza, edad, sexo.name(), dueno.getNombre()});
                JOptionPane.showMessageDialog(this,
                    "Mascota registrada exitosamente con ID: " + id,
                    "Exito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                Veterinaria.getInstancia().actualizarMascota(idEditando, nombre, especie, raza, edad, sexo, dueno.getId());
                actualizarFilaEnTabla(idEditando, nombre, especie, raza, edad, sexo.name(), dueno.getNombre());
                JOptionPane.showMessageDialog(this,
                    "Mascota actualizada exitosamente.",
                    "Exito", JOptionPane.INFORMATION_MESSAGE);
                idEditando = -1;
                btnRegistrar.setText("Registrar Mascota");
                estiloBoton(btnRegistrar, SUCCESS);
            }
            limpiarFormulario();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editarMascota() {
        int fila = tablaMascotas.getSelectedRow();
        if (fila < 0) return;

        idEditando = (int) modeloTabla.getValueAt(fila, 0);
        txtNombre.setText((String) modeloTabla.getValueAt(fila, 1));

        String especie = (String) modeloTabla.getValueAt(fila, 2);
        for (int i = 0; i < cmbEspecie.getItemCount(); i++) {
            if (cmbEspecie.getItemAt(i).equals(especie)) {
                cmbEspecie.setSelectedIndex(i);
                break;
            }
        }

        txtRaza.setText((String) modeloTabla.getValueAt(fila, 3));
        txtEdad.setText(String.valueOf(modeloTabla.getValueAt(fila, 4)));

        String sexoStr = (String) modeloTabla.getValueAt(fila, 5);
        if (sexoStr.equals("HEMBRA")) {
            rbHembra.setSelected(true);
        } else {
            rbMacho.setSelected(true);
        }

        String nombreDueno = (String) modeloTabla.getValueAt(fila, 6);
        for (int i = 0; i < cmbDueno.getItemCount(); i++) {
            Cliente c = cmbDueno.getItemAt(i);
            if (c.getNombre().equals(nombreDueno)) {
                cmbDueno.setSelectedIndex(i);
                break;
            }
        }

        btnRegistrar.setText("Guardar Cambios");
        estiloBoton(btnRegistrar, WARNING);
        txtNombre.requestFocus();
    }

    private void eliminarMascota() {
        int fila = tablaMascotas.getSelectedRow();
        if (fila < 0) return;

        int id = (int) modeloTabla.getValueAt(fila, 0);
        String nombre = (String) modeloTabla.getValueAt(fila, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
            "Esta seguro de eliminar a \"" + nombre + "\"?\nTambien se eliminaran sus citas asociadas.",
            "Confirmar eliminacion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm != JOptionPane.YES_OPTION) return;

        if (Veterinaria.getInstancia().eliminarMascota(id)) {
            modeloTabla.removeRow(fila);
            JOptionPane.showMessageDialog(this,
                "Mascota eliminada.", "Eliminado", JOptionPane.INFORMATION_MESSAGE);
            if (idEditando == id) {
                idEditando = -1;
                btnRegistrar.setText("Registrar Mascota");
                estiloBoton(btnRegistrar, SUCCESS);
            }
            limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(this,
                "No se pudo eliminar la mascota.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        txtNombre.setText("");
        txtRaza.setText("");
        txtEdad.setText("");
        cmbEspecie.setSelectedIndex(0);
        rbMacho.setSelected(true);
        tablaMascotas.clearSelection();
        btnEditar.setEnabled(false);
        btnEliminar.setEnabled(false);
        if (idEditando != -1) {
            idEditando = -1;
            btnRegistrar.setText("Registrar Mascota");
            estiloBoton(btnRegistrar, SUCCESS);
        }
        txtNombre.requestFocus();
    }

    private void actualizarFilaEnTabla(int id, String nombre, String especie, String raza, int edad, String sexo, String dueno) {
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            if ((int) modeloTabla.getValueAt(i, 0) == id) {
                modeloTabla.setValueAt(nombre, i, 1);
                modeloTabla.setValueAt(especie, i, 2);
                modeloTabla.setValueAt(raza, i, 3);
                modeloTabla.setValueAt(edad, i, 4);
                modeloTabla.setValueAt(sexo, i, 5);
                modeloTabla.setValueAt(dueno, i, 6);
                break;
            }
        }
    }

    public void actualizarComboDuenos() {
        cmbDueno.removeAllItems();
        for (Cliente c : Veterinaria.getInstancia().getClientes()) {
            cmbDueno.addItem(c);
        }
    }

    public void cargarTabla() {
        modeloTabla.setRowCount(0);
        Veterinaria vet = Veterinaria.getInstancia();
        for (Mascota m : vet.getMascotas()) {
            Cliente dueno = vet.buscarCliente(m.getIdDueno());
            String nombreDueno = (dueno != null) ? dueno.getNombre() : "Desconocido";
            modeloTabla.addRow(new Object[]{
                m.getId(), m.getNombre(), m.getEspecie(), m.getRaza(),
                m.getEdad(), m.getSexo().name(), nombreDueno
            });
        }
    }
}
