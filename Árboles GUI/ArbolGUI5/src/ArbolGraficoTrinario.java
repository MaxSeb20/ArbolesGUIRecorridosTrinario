import javax.swing.*;
import java.awt.*;

public class ArbolGraficoTrinario extends JPanel {
    private ArbolTrinario arbol;

    public ArbolGraficoTrinario(ArbolTrinario arbol) {
        this.arbol = arbol;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if (arbol.getRaiz() != null) {
            dibujarArbol(g2d, arbol.getRaiz(), getWidth() / 2, 50, getWidth() / 4, 50);
        }
    }

    public void dibujarArbol(Graphics2D g2d, NodoTrinario nodo, int x, int y, int dx, int dy) {
        if (nodo != null) {
            // Asigna las coordenadas reales del nodo
            nodo.x = x;
            nodo.y = y;

            // Dibuja el nodo
            g2d.fillOval(x - 15, y - 15, 30, 30);
            g2d.drawString(nodo.etiqueta, x - 10, y + 5);

            // Dibuja la línea y llama recursivamente para el nodo izquierdo
            if (nodo.izquierda != null) {
                g2d.drawLine(x, y, x - dx, y + dy);
                dibujarArbol(g2d, nodo.izquierda, x - dx, y + dy, dx / 2, dy);
            }

            // Dibuja la línea y llama recursivamente para el nodo medio
            if (nodo.medio != null) {
                g2d.drawLine(x, y, x, y + dy);
                dibujarArbol(g2d, nodo.medio, x, y + dy, dx / 2, dy);
            }

            // Dibuja la línea y llama recursivamente para el nodo derecho
            if (nodo.derecha != null) {
                g2d.drawLine(x, y, x + dx, y + dy);
                dibujarArbol(g2d, nodo.derecha, x + dx, y + dy, dx / 2, dy);
            }
        }
    }


}

