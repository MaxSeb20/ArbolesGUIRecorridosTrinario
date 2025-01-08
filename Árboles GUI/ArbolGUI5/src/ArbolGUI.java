import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class ArbolGUI {
    private JTable tbMatrizAdyacencia;
    private JTextArea textArea;
    private JTextField txtRaiz;
    private JButton btnAgregarNodo;
    private JButton btnDibujarArbol;
    private JButton btnRecorridoAnchura;
    private JButton btnRecorridoProfundidad;
    private JButton btnPreorden;
    private JButton btnInorden;
    private JLabel lblNodo;
    private JLabel lblRaiz;
    private JLabel lblHoja;
    private JComboBox<String> cbHijoPosicion;
    private JPanel panelArbol;
    private JPanel panelGeneral;
    private JPanel panelDatos;
    private JLabel lblRecorridos;
    private JButton btnPostorden;
    private JButton btnMatrizAdyacencia;
    private JButton btnEliminarNodo;


    private ArbolTrinario arbol = new ArbolTrinario();
    private ArbolGraficoTrinario arbolGrafico = new ArbolGraficoTrinario(arbol);
    private DefaultTableModel modeloTabla = new DefaultTableModel();

    public ArbolGUI() {
        initComponents();

        btnAgregarNodo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String etiqueta = arbol.getEtiquetaNodoSiguiente();
                    NodoTrinario nuevoNodo = new NodoTrinario(0, 0, etiqueta);
                    String etiquetaPadre = txtRaiz.getText().trim();
                    String posicionHijo = cbHijoPosicion.getSelectedItem().toString();

                    NodoTrinario nodoPadre = null;
                    for (NodoTrinario nodo : arbol.getNodos()) {
                        if (nodo.etiqueta.equals(etiquetaPadre)) {
                            nodoPadre = nodo;
                            break;
                        }
                    }
                    arbol.anadirNodo(nuevoNodo, nodoPadre, posicionHijo);
                    imprimirArbol();
                    dibujarArbolEnPanel();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al agregar nodo: " + ex.getMessage());
                }
            }
        });

        btnRecorridoAnchura.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String resultado = arbol.bfs();
                textArea.append("Recorrido en Anchura (BFS): " + resultado + "\n");
            }
        });

        btnRecorridoProfundidad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String resultado = arbol.dfs();
                textArea.append("Recorrido en Profundidad (DFS): " + resultado + "\n");
            }
        });

        btnPreorden.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String resultado = arbol.preorden();
                textArea.append("Preorden: " + resultado + "\n");
            }
        });

        btnInorden.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String resultado = arbol.inorden();
                textArea.append("Inorden: " + resultado + "\n");
            }
        });

        btnPostorden.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String resultado = arbol.postorden();
                textArea.append("Postorden: " + resultado + "\n");
            }
        });

        btnMatrizAdyacencia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarMatrizAdyacencia();
            }
        });

        btnDibujarArbol.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dibujarArbolEnPanel();
            }
        });
        btnEliminarNodo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String etiqueta = txtRaiz.getText().trim(); // Obtener etiqueta del nodo a eliminar
                if (etiqueta.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese la etiqueta del nodo a eliminar.");
                    return;
                }

                boolean eliminado = arbol.eliminarNodo(etiqueta); // Llamar a la lógica de eliminación
                if (eliminado) {
                    JOptionPane.showMessageDialog(null, "Nodo eliminado correctamente.");
                    imprimirArbol(); // Actualizar el área de texto
                    dibujarArbolEnPanel(); // Redibujar el árbol
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar el nodo. Verifique la etiqueta.");
                }
            }
        });

    }

    private void initComponents() {
        if (btnAgregarNodo == null) btnAgregarNodo = new JButton("Agregar Nodo");
        if (btnDibujarArbol == null) btnDibujarArbol = new JButton("Dibujar Árbol");
        if (btnRecorridoAnchura == null) btnRecorridoAnchura = new JButton("Recorrido Anchura");
        if (btnRecorridoProfundidad == null) btnRecorridoProfundidad = new JButton("Recorrido Profundidad");
        if (btnPreorden == null) btnPreorden = new JButton("Preorden");
        if (btnInorden == null) btnInorden = new JButton("Inorden");
        if (btnPostorden == null) btnPostorden = new JButton("Postorden");
        if (btnMatrizAdyacencia == null) btnMatrizAdyacencia = new JButton("Matriz Adyacencia");
        if (txtRaiz == null) txtRaiz = new JTextField(10);
        if (cbHijoPosicion == null) cbHijoPosicion = new JComboBox<>(new String[]{"izquierda", "medio", "derecha"});
        if (panelArbol == null) panelArbol = new JPanel();
        if (textArea == null) textArea = new JTextArea(10, 30);
        if (panelGeneral == null) panelGeneral = new JPanel(new BorderLayout());
        if (panelDatos == null) panelDatos = new JPanel(new FlowLayout());
        if (btnEliminarNodo == null) btnEliminarNodo = new JButton("Eliminar Nodo");

// Añadir el botón al panel de controles
        panelDatos.add(btnEliminarNodo);


        panelDatos.add(new JLabel("Etiqueta Padre:"));
        panelDatos.add(txtRaiz);
        panelDatos.add(new JLabel("Posición Hijo:"));
        panelDatos.add(cbHijoPosicion);
        panelDatos.add(btnAgregarNodo);
        panelDatos.add(btnDibujarArbol);
        panelDatos.add(btnRecorridoAnchura);
        panelDatos.add(btnRecorridoProfundidad);
        panelDatos.add(btnPreorden);
        panelDatos.add(btnInorden);
        panelDatos.add(btnPostorden);
        panelDatos.add(btnMatrizAdyacencia);

        JScrollPane scrollPane = new JScrollPane(textArea);
        panelGeneral.add(scrollPane, BorderLayout.EAST);
        panelGeneral.add(panelArbol, BorderLayout.CENTER);
        panelGeneral.add(panelDatos, BorderLayout.NORTH);

    }

    private void dibujarArbolEnPanel() {
        Graphics g = panelArbol.getGraphics();

        if (g != null) {
            Graphics2D g2d = (Graphics2D) g;
            int panelWidth = panelArbol.getWidth();
            int panelHeight = panelArbol.getHeight();
            int x = panelWidth / 2;
            int y = 50;
            int dimensionX = panelWidth / 4;
            int dimensionY = 50;

            arbolGrafico.dibujarArbol(g2d, arbol.getRaiz(), x, y, dimensionX, dimensionY);
        }
    }

    private void imprimirArbol() {
        textArea.setText("");
        textArea.append("Nodos:\n");
        for (NodoTrinario nodo : arbol.getNodos()) {
            textArea.append(nodo.etiqueta + ": " + nodo.toString() + "\n");
        }
    }

    private void mostrarMatrizAdyacencia() {
        Object[][] matriz = arbol.getMatrizAdyacencia();
        String[] nombreColumnas = new String[matriz.length];
        for (int i = 0; i < matriz.length; i++) {
            nombreColumnas[i] = String.valueOf((char) ('A' + i));
        }

        modeloTabla.setDataVector(matriz, nombreColumnas);
        tbMatrizAdyacencia.setModel(modeloTabla);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ArbolGUI");
        frame.setContentPane(new ArbolGUI().panelGeneral);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
