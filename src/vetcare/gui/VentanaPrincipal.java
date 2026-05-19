package vetcare.gui;

import vetcare.data.PersistenciaArchivo;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static vetcare.gui.UIConstantes.*;

public class VentanaPrincipal extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainContainer;
    private PanelRegistroCliente panelClientes;
    private PanelRegistroMascota panelMascotas;
    private PanelAgendarCita panelCitas;
    private PanelListarCitas panelListado;
    private PanelHistorialClinico panelHistorial;

    public VentanaPrincipal() {
        setTitle("VetCare - Clinica Veterinaria Huellitas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(900, 600));

        try {
            PersistenciaArchivo.cargarDatos();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                "No se pudieron cargar los datos guardados. Se iniciara con datos vacios.",
                "Aviso", JOptionPane.INFORMATION_MESSAGE);
        }

        initComponents();

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                guardarAlCerrar();
            }
        });
    }

    private void initComponents() {
        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);
        mainContainer.setBackground(BG_LIGHT);

        JPanel welcomePanel = crearWelcomePanel();
        JPanel appPanel = crearAppPanel();

        mainContainer.add(welcomePanel, "welcome");
        mainContainer.add(appPanel, "app");

        add(mainContainer);
    }

    private JPanel crearWelcomePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BG_LIGHT);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(8, 30, 8, 30);
        gbc.anchor = GridBagConstraints.CENTER;

        int y = 0;

        JLabel lblTitulo = new JLabel("VETCARE");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 52));
        lblTitulo.setForeground(PRIMARY);
        gbc.gridy = y++;
        panel.add(lblTitulo, gbc);

        JLabel lblSub = new JLabel("Clinica Veterinaria Huellitas");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblSub.setForeground(SECONDARY);
        gbc.gridy = y++;
        panel.add(lblSub, gbc);

        JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
        sep.setPreferredSize(new Dimension(320, 2));
        sep.setForeground(SECONDARY);
        sep.setBackground(SECONDARY);
        gbc.gridy = y++;
        gbc.insets = new Insets(18, 30, 18, 30);
        panel.add(sep, gbc);

        JLabel lblDesc = new JLabel("Sistema de Gestion Veterinaria");
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblDesc.setForeground(TEXT_DARK);
        gbc.insets = new Insets(4, 30, 4, 30);
        gbc.gridy = y++;
        panel.add(lblDesc, gbc);

        JPanel featurePanel = new JPanel(new GridLayout(0, 1, 0, 10));
        featurePanel.setBackground(BG_LIGHT);
        featurePanel.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));

        String[] features = {
            ">  Registro de clientes y sus mascotas",
            ">  Agendamiento de citas medicas",
            ">  Historial clinico por paciente",
            ">  Datos guardados automaticamente"
        };
        for (String f : features) {
            JLabel lbl = new JLabel(f);
            lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            lbl.setForeground(TEXT_DARK);
            lbl.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
            featurePanel.add(lbl);
        }
        gbc.gridy = y++;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(featurePanel, gbc);

        JButton btnComenzar = new JButton("C O M E N Z A R");
        estiloBoton(btnComenzar, SUCCESS);
        btnComenzar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnComenzar.addActionListener(e -> {
            cardLayout.show(mainContainer, "app");
        });
        gbc.gridy = y++;
        gbc.insets = new Insets(22, 30, 6, 30);
        panel.add(btnComenzar, gbc);

        JLabel lblFooter = new JLabel("Proyecto Programacion II  -  Mayo 2026");
        lblFooter.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblFooter.setForeground(BORDER);
        gbc.gridy = y;
        gbc.insets = new Insets(4, 30, 10, 30);
        panel.add(lblFooter, gbc);

        return panel;
    }

    private JPanel crearAppPanel() {
        JPanel appPanel = new JPanel(new BorderLayout());

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY);
        headerPanel.setPreferredSize(new Dimension(0, 68));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(12, 22, 12, 22));

        JLabel lblAppTitle = new JLabel("VetCare");
        lblAppTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblAppTitle.setForeground(Color.WHITE);
        headerPanel.add(lblAppTitle, BorderLayout.WEST);

        JPanel rightHeader = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 5));
        rightHeader.setOpaque(false);
        JLabel lblClinica = new JLabel("Clinica Veterinaria Huellitas");
        lblClinica.setFont(FONT_SUBTITLE);
        lblClinica.setForeground(STATUS_FG);
        JLabel badge = new JLabel("v1.0");
        badge.setFont(new Font("Segoe UI", Font.BOLD, 10));
        badge.setForeground(Color.WHITE);
        badge.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 1, true),
            BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));
        JButton btnSalir = new JButton("Salir");
        btnSalir.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setBackground(new Color(192, 57, 43));
        btnSalir.setFocusPainted(false);
        btnSalir.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(192, 57, 43).darker(), 1, true),
            BorderFactory.createEmptyBorder(6, 14, 6, 14)
        ));
        btnSalir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSalir.addActionListener(e -> {
            cardLayout.show(mainContainer, "welcome");
        });

        rightHeader.add(lblClinica);
        rightHeader.add(Box.createHorizontalStrut(10));
        rightHeader.add(badge);
        rightHeader.add(Box.createHorizontalStrut(12));
        rightHeader.add(btnSalir);
        headerPanel.add(rightHeader, BorderLayout.EAST);

        appPanel.add(headerPanel, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabs.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));

        panelClientes = new PanelRegistroCliente();
        panelMascotas = new PanelRegistroMascota();
        panelCitas = new PanelAgendarCita();
        panelListado = new PanelListarCitas();
        panelHistorial = new PanelHistorialClinico();

        tabs.addTab("  Clientes", panelClientes);
        tabs.addTab("  Mascotas", panelMascotas);
        tabs.addTab("  Agendar Cita", panelCitas);
        tabs.addTab("  Listado de Citas", panelListado);
        tabs.addTab("  Historial Clinico", panelHistorial);

        tabs.addChangeListener(e -> {
            int idx = tabs.getSelectedIndex();
            if (idx == 0) {
                panelClientes.cargarTabla();
            } else if (idx == 1) {
                panelMascotas.actualizarComboDuenos();
                panelMascotas.cargarTabla();
            } else if (idx == 2) {
                panelCitas.actualizarComboMascotas();
                panelCitas.cargarTabla();
            } else if (idx == 3) {
                panelListado.cargarTabla();
            } else if (idx == 4) {
                panelHistorial.actualizarComboMascotas();
            }
        });

        appPanel.add(tabs, BorderLayout.CENTER);

        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setBackground(PRIMARY);
        statusBar.setPreferredSize(new Dimension(0, 28));
        statusBar.setBorder(BorderFactory.createEmptyBorder(4, 15, 4, 15));
        JLabel statusLabel = new JLabel("Sistema listo  |  los datos se guardan automaticamente al cerrar");
        statusLabel.setFont(FONT_STATUS);
        statusLabel.setForeground(STATUS_FG);
        statusBar.add(statusLabel, BorderLayout.WEST);
        appPanel.add(statusBar, BorderLayout.SOUTH);

        return appPanel;
    }

    private void guardarAlCerrar() {
        try {
            PersistenciaArchivo.guardarDatos();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                "Error al guardar datos: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
