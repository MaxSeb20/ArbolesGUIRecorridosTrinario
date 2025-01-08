import java.util.*;

public class ArbolTrinario {
    private NodoTrinario raiz;
    private ArrayList<NodoTrinario> nodos;
    private int numNodos;

    public ArbolTrinario() {
        raiz = null;
        nodos = new ArrayList<>();
        numNodos = 0;
    }

    public void anadirNodo(NodoTrinario nodo, NodoTrinario padre, String posicion) {
        if (padre == null) {
            if (raiz == null) {
                raiz = nodo;
            } else {
                throw new IllegalArgumentException("La raíz ya existe");
            }
        } else {
            switch (posicion.toLowerCase()) {
                case "izquierda":
                    if (padre.izquierda == null) {
                        padre.izquierda = nodo;
                    } else {
                        throw new IllegalArgumentException("Hijo izquierdo ya existe");
                    }
                    break;
                case "medio":
                    if (padre.medio == null) {
                        padre.medio = nodo;
                    } else {
                        throw new IllegalArgumentException("Hijo medio ya existe");
                    }
                    break;
                case "derecha":
                    if (padre.derecha == null) {
                        padre.derecha = nodo;
                    } else {
                        throw new IllegalArgumentException("Hijo derecho ya existe");
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Posición inválida");
            }
        }
        nodos.add(nodo);
    }
    public String bfs() {
        if (raiz == null) return "";

        StringBuilder resultado = new StringBuilder();
        Queue<NodoTrinario> queue = new LinkedList<>();
        queue.add(raiz);

        while (!queue.isEmpty()) {
            NodoTrinario nodo = queue.poll();
            resultado.append(nodo.etiqueta).append(" ");
            if (nodo.izquierda != null) queue.add(nodo.izquierda);
            if (nodo.medio != null) queue.add(nodo.medio);
            if (nodo.derecha != null) queue.add(nodo.derecha);
        }

        return resultado.toString().trim();
    }

    public String dfs() {
        if (raiz == null) return "";

        StringBuilder resultado = new StringBuilder();
        Stack<NodoTrinario> stack = new Stack<>();
        stack.push(raiz);

        while (!stack.isEmpty()) {
            NodoTrinario nodo = stack.pop();
            resultado.append(nodo.etiqueta).append(" ");
            if (nodo.derecha != null) stack.push(nodo.derecha);
            if (nodo.medio != null) stack.push(nodo.medio);
            if (nodo.izquierda != null) stack.push(nodo.izquierda);
        }

        return resultado.toString().trim();
    }
    public Object[][] getMatrizAdyacencia() {
        int tam = nodos.size();
        Object[][] matriz = new Object[tam][tam];
        Map<String, Integer> etiquetaAIndice = new HashMap<>();

        // Inicializar mapa y matriz
        for (int i = 0; i < tam; i++) {
            etiquetaAIndice.put(nodos.get(i).etiqueta, i);
            for (int j = 0; j < tam; j++) {
                matriz[i][j] = 0;
            }
        }

        // Llenar matriz con conexiones
        for (NodoTrinario nodo : nodos) {
            int desdeIndice = etiquetaAIndice.get(nodo.etiqueta);
            if (nodo.izquierda != null) {
                int hastaIndice = etiquetaAIndice.get(nodo.izquierda.etiqueta);
                matriz[desdeIndice][hastaIndice] = 1;
            }
            if (nodo.medio != null) {
                int hastaIndice = etiquetaAIndice.get(nodo.medio.etiqueta);
                matriz[desdeIndice][hastaIndice] = 1;
            }
            if (nodo.derecha != null) {
                int hastaIndice = etiquetaAIndice.get(nodo.derecha.etiqueta);
                matriz[desdeIndice][hastaIndice] = 1;
            }
        }

        return matriz;
    }
    public boolean eliminarNodo(String etiqueta) {
        NodoTrinario nodoAEliminar = buscarNodo(etiqueta);
        if (nodoAEliminar == null) {
            System.out.println("Nodo no encontrado: " + etiqueta);
            return false; // No se encontró el nodo
        }

        // Caso 1: Nodo sin hijos
        if (nodoAEliminar.izquierda == null && nodoAEliminar.medio == null && nodoAEliminar.derecha == null) {
            eliminarReferenciaPadre(nodoAEliminar);
            nodos.remove(nodoAEliminar);
            return true;
        }

        // Caso 2: Nodo con hijos
        // Por simplicidad, reemplazamos con el hijo izquierdo más a la derecha (si existe)
        NodoTrinario reemplazo = obtenerReemplazo(nodoAEliminar);
        if (reemplazo != null) {
            nodoAEliminar.etiqueta = reemplazo.etiqueta;
            nodoAEliminar.izquierda = reemplazo.izquierda;
            nodoAEliminar.medio = reemplazo.medio;
            nodoAEliminar.derecha = reemplazo.derecha;

            // Eliminar el nodo reemplazado del árbol
            eliminarReferenciaPadre(reemplazo);
            nodos.remove(reemplazo);
        } else {
            eliminarReferenciaPadre(nodoAEliminar);
            nodos.remove(nodoAEliminar);
        }

        return true;
    }

    // Buscar nodo por etiqueta
    private NodoTrinario buscarNodo(String etiqueta) {
        for (NodoTrinario nodo : nodos) {
            if (nodo.etiqueta.equals(etiqueta)) {
                return nodo;
            }
        }
        return null;
    }

    // Eliminar referencia del padre hacia el nodo
    private void eliminarReferenciaPadre(NodoTrinario nodo) {
        for (NodoTrinario padre : nodos) {
            if (padre.izquierda == nodo) {
                padre.izquierda = null;
            } else if (padre.medio == nodo) {
                padre.medio = null;
            } else if (padre.derecha == nodo) {
                padre.derecha = null;
            }
        }
    }

    // Obtener nodo para reemplazo
    private NodoTrinario obtenerReemplazo(NodoTrinario nodo) {
        if (nodo.izquierda != null) return nodo.izquierda;
        if (nodo.medio != null) return nodo.medio;
        if (nodo.derecha != null) return nodo.derecha;
        return null;
    }



    public ArrayList<NodoTrinario> getNodos() {
        return nodos;
    }

    public NodoTrinario getRaiz() {
        return raiz;
    }

    public String preorden() {
        return preordenImpresion(raiz).trim();
    }
    public String getEtiquetaNodoSiguiente() {
        return String.valueOf((char) ('A' + numNodos++));
    }


    private String preordenImpresion(NodoTrinario nodo) {
        if (nodo == null) return "";
        return nodo.etiqueta + " " + preordenImpresion(nodo.izquierda) +
                preordenImpresion(nodo.medio) + preordenImpresion(nodo.derecha);
    }

    public String inorden() {
        return inordenImpresion(raiz).trim();
    }

    private String inordenImpresion(NodoTrinario nodo) {
        if (nodo == null) return "";
        return inordenImpresion(nodo.izquierda) + nodo.etiqueta + " " +
                inordenImpresion(nodo.medio) + inordenImpresion(nodo.derecha);
    }

    public String postorden() {
        return postordenImpresion(raiz).trim();
    }

    private String postordenImpresion(NodoTrinario nodo) {
        if (nodo == null) return "";
        return postordenImpresion(nodo.izquierda) + postordenImpresion(nodo.medio) +
                postordenImpresion(nodo.derecha) + nodo.etiqueta + " ";
    }
}
