package vetcare.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

public class UIConstantes {

    private UIConstantes() {}

    public static final Color PRIMARY = new Color(44, 62, 80);
    public static final Color SECONDARY = new Color(52, 152, 219);
    public static final Color SUCCESS = new Color(39, 174, 96);
    public static final Color DANGER = new Color(192, 57, 43);
    public static final Color WARNING = new Color(243, 156, 18);
    public static final Color BG_LIGHT = new Color(236, 240, 241);
    public static final Color TEXT_DARK = new Color(44, 62, 80);
    public static final Color TABLE_HEADER_BG = new Color(52, 73, 94);
    public static final Color ROW_ALT = new Color(245, 247, 250);
    public static final Color BORDER = new Color(189, 195, 199);
    public static final Color STATUS_FG = new Color(189, 195, 199);
    public static final Color INPUT_BG = Color.WHITE;

    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 26);
    public static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_SECTION = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_LABEL = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FONT_TABLE = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font FONT_MONO = new Font("Consolas", Font.PLAIN, 12);
    public static final Font FONT_HEADER_LABEL = new Font("Segoe UI", Font.BOLD, 11);
    public static final Font FONT_STATUS = new Font("Segoe UI", Font.PLAIN, 12);

    public static void estiloTabla(JTable tabla) {
        tabla.setRowHeight(28);
        tabla.setShowGrid(false);
        tabla.setIntercellSpacing(new Dimension(0, 0));
        tabla.setFont(FONT_TABLE);
        tabla.setSelectionBackground(new Color(52, 152, 219, 80));
        tabla.setSelectionForeground(TEXT_DARK);
        tabla.setBackground(Color.WHITE);

        JTableHeader header = tabla.getTableHeader();
        header.setFont(FONT_HEADER_LABEL);
        header.setBackground(TABLE_HEADER_BG);
        header.setForeground(Color.WHITE);
        header.setReorderingAllowed(false);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, SECONDARY));

        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value,
                    boolean selected, boolean hasFocus, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, value, selected, hasFocus, row, col);
                c.setBackground(TABLE_HEADER_BG);
                c.setForeground(Color.WHITE);
                setFont(FONT_HEADER_LABEL);
                setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
                return c;
            }
        });

        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value,
                    boolean isSelected, boolean hasFocus, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, col);
                ((JLabel) c).setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : ROW_ALT);
                } else {
                    c.setBackground(new Color(52, 152, 219, 60));
                }
                return c;
            }
        });
    }

    public static TitledBorder bordeTitulado(String titulo) {
        return BorderFactory.createTitledBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1, true),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
            ),
            titulo,
            TitledBorder.LEFT,
            TitledBorder.TOP,
            FONT_SECTION,
            PRIMARY
        );
    }

    public static void estiloBoton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(FONT_BUTTON);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bg.darker(), 1, true),
            BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public static JTextField crearCampoTexto(int cols) {
        JTextField tf = new JTextField(cols);
        tf.setFont(FONT_LABEL);
        tf.setBackground(INPUT_BG);
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER),
            BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));
        return tf;
    }

    public static JComboBox<String> crearCombo(String[] items) {
        JComboBox<String> cb = new JComboBox<>(items);
        cb.setFont(FONT_LABEL);
        cb.setBackground(INPUT_BG);
        cb.setBorder(BorderFactory.createLineBorder(BORDER));
        return cb;
    }

    public static JTextArea crearAreaTexto(int filas, int cols) {
        JTextArea ta = new JTextArea(filas, cols);
        ta.setFont(FONT_LABEL);
        ta.setBackground(INPUT_BG);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER),
            BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));
        return ta;
    }

    public static JPanel crearPanelBoton(JButton... botones) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panel.setBackground(Color.WHITE);
        for (JButton b : botones) {
            panel.add(b);
        }
        return panel;
    }
}
