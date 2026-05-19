package vetcare.gui;

import vetcare.data.Veterinaria;
import vetcare.model.Cliente;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import static vetcare.gui.UIConstantes.*;

public class PanelRegistroCliente extends JPanel {

    private JTextField txtNombre, txtTelefono, txtEmail, txtDireccion;
    private JButton btnRegistrar, btnLimpiar, btnEditar, btnEliminar;
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;
    private int idEditando = -1;

    public PanelRegistroCliente() {
        setLayout(new BorderLayout(15, 15));
        setBackground(BG_LIGHT);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        initComponents();
        cargarTabla();
    }

    private void initComponents() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(bordeTitulado("Registrar Nuevo Cliente"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblNombre = new JLabel("Nombre completo:");
        lblNombre.setFont(FONT_LABEL);
        txtNombre = crearCampoTexto(22);
        JLabel lblTelefono = new JLabel("Telefono:");
        lblTelefono.setFont(FONT_LABEL);
        txtTelefono = crearCampoTexto(22);
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(FONT_LABEL);
        txtEmail = crearCampoTexto(22);
        JLabel lblDireccion = new JLabel("Direccion:");
        lblDireccion.setFont(FONT_LABEL);
        txtDireccion = crearCampoTexto(22);

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(lblNombre, gbc);
        gbc.gridx = 1;
        formPanel.add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(lblTelefono, gbc);
        gbc.gridx = 1;
        formPanel.add(txtTelefono, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(lblEmail, gbc);
        gbc.gridx = 1;
        formPanel.add(txtEmail, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(lblDireccion, gbc);
        gbc.gridx = 1;
        formPanel.add(txtDireccion, gbc);

        btnRegistrar = new JButton("Registrar Cliente");
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

        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(filaBotones, gbc);

        add(formPanel, BorderLayout.WEST);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Telefono", "Email", "Direccion"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tablaClientes = new JTable(modeloTabla);
        estiloTabla(tablaClientes);
        tablaClientes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                boolean haySeleccion = tablaClientes.getSelectedRow() >= 0;
                btnEditar.setEnabled(haySeleccion);
                btnEliminar.setEnabled(haySeleccion);
            }
        });
        JScrollPane scroll = new JScrollPane(tablaClientes);
        scroll.setBorder(bordeTitulado("Clientes Registrados"));
        scroll.setPreferredSize(new Dimension(480, 0));
        scroll.getViewport().setBackground(Color.WHITE);
        add(scroll, BorderLayout.CENTER);

        btnRegistrar.addActionListener(e -> guardarCliente());
        btnEditar.addActionListener(e -> editarCliente());
        btnEliminar.addActionListener(e -> eliminarCliente());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
    }

    private void guardarCliente() {
        try {
            String nombre = txtNombre.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String email = txtEmail.getText().trim();
            String direccion = txtDireccion.getText().trim();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "El nombre del cliente es obligatorio.",
                    "Campo requerido", JOptionPane.WARNING_MESSAGE);
                txtNombre.requestFocus();
                return;
            }
            if (telefono.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El telefono es obligatorio.", "Campo requerido", JOptionPane.WARNING_MESSAGE);
                txtTelefono.requestFocus(); return;
            }
            if (email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El email es obligatorio.", "Campo requerido", JOptionPane.WARNING_MESSAGE);
                txtEmail.requestFocus(); return;
            }
            if (direccion.isEmpty()) {
                JOptionPane.showMessageDialog(this, "La direccion es obligatoria.", "Campo requerido", JOptionPane.WARNING_MESSAGE);
                txtDireccion.requestFocus(); return;
            }

            if (idEditando == -1) {
                int id = Veterinaria.getInstancia().generarIdCliente();
                Cliente c = new Cliente(id, nombre, telefono, email, direccion);
                Veterinaria.getInstancia().agregarCliente(c);
                modeloTabla.addRow(new Object[]{id, nombre, telefono, email, direccion});
                JOptionPane.showMessageDialog(this,
                    "Cliente registrado exitosamente con ID: " + id,
                    "Exito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                Veterinaria.getInstancia().actualizarCliente(idEditando, nombre, telefono, email, direccion);
                actualizarFilaEnTabla(idEditando, nombre, telefono, email, direccion);
                JOptionPane.showMessageDialog(this,
                    "Cliente actualizado exitosamente.",
                    "Exito", JOptionPane.INFORMATION_MESSAGE);
                idEditando = -1;
                btnRegistrar.setText("Registrar Cliente");
                btnRegistrar.setBackground(SUCCESS);
            }
            limpiarFormulario();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editarCliente() {
        int fila = tablaClientes.getSelectedRow();
        if (fila < 0) return;
        idEditando = (int) modeloTabla.getValueAt(fila, 0);
        txtNombre.setText((String) modeloTabla.getValueAt(fila, 1));
        txtTelefono.setText((String) modeloTabla.getValueAt(fila, 2));
        txtEmail.setText((String) modeloTabla.getValueAt(fila, 3));
        txtDireccion.setText((String) modeloTabla.getValueAt(fila, 4));
        btnRegistrar.setText("Guardar Cambios");
        estiloBoton(btnRegistrar, WARNING);
        txtNombre.requestFocus();
    }

    private void eliminarCliente() {
        int fila = tablaClientes.getSelectedRow();
        if (fila < 0) return;
        int id = (int) modeloTabla.getValueAt(fila, 0);
        String nombre = (String) modeloTabla.getValueAt(fila, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
            "Esta seguro de eliminar a \"" + nombre + "\" y todas sus mascotas?",
            "Confirmar eliminacion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm != JOptionPane.YES_OPTION) return;

        if (Veterinaria.getInstancia().eliminarCliente(id)) {
            modeloTabla.removeRow(fila);
            JOptionPane.showMessageDialog(this,
                "Cliente y sus mascotas eliminados.", "Eliminado", JOptionPane.INFORMATION_MESSAGE);
            if (idEditando == id) {
                idEditando = -1;
                btnRegistrar.setText("Registrar Cliente");
                estiloBoton(btnRegistrar, SUCCESS);
            }
            limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(this,
                "No se pudo eliminar el cliente.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        txtNombre.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtDireccion.setText("");
        tablaClientes.clearSelection();
        btnEditar.setEnabled(false);
        btnEliminar.setEnabled(false);
        if (idEditando != -1) {
            idEditando = -1;
            btnRegistrar.setText("Registrar Cliente");
            estiloBoton(btnRegistrar, SUCCESS);
        }
        txtNombre.requestFocus();
    }

    private void actualizarFilaEnTabla(int id, String nombre, String telefono, String email, String direccion) {
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            if ((int) modeloTabla.getValueAt(i, 0) == id) {
                modeloTabla.setValueAt(nombre, i, 1);
                modeloTabla.setValueAt(telefono, i, 2);
                modeloTabla.setValueAt(email, i, 3);
                modeloTabla.setValueAt(direccion, i, 4);
                break;
            }
        }
    }

    public void cargarTabla() {
        modeloTabla.setRowCount(0);
        for (Cliente c : Veterinaria.getInstancia().getClientes()) {
            modeloTabla.addRow(new Object[]{
                c.getId(), c.getNombre(), c.getTelefono(),
                c.getEmail(), c.getDireccion()
            });
        }
    }
}
