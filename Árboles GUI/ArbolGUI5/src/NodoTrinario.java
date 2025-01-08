public class NodoTrinario {
    int x, y;
    String etiqueta;
    NodoTrinario izquierda, medio, derecha;

    public NodoTrinario(int x, int y, String etiqueta) {
        this.x = x;
        this.y = y;
        this.etiqueta = etiqueta;
        this.izquierda = null;
        this.medio = null;
        this.derecha = null;
    }

    @Override
    public String toString() {
        return etiqueta + " (" + x + ", " + y + ")";
    }
}
