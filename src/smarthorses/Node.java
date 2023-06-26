package smarthorses;

public class Node {

    private String tipoNodo; //max, min or undefined
    private int profundidad;
    private int utilidad; 
    private Node padre;
    private int maxValue;
    private int minValue;


    public Node(){
        this.tipoNodo = "INDEFINIDO";
        this.profundidad = 0;
        this.utilidad = 0;
        this.maxValue = 0;
        this.minValue = 0;
    }


    //GETTERS
    public String getTipoNodo(){
        return this.tipoNodo;
    }

    public int getProfundidad(){
        return this.profundidad;
    }

    public int getUtilidad(){
        return this.utilidad;
    }

    public int getMaxValue(){
        return this.maxValue;
    }

    public int getMinValue(){
        return this.minValue;
    }

    public Node getPadre(){
        return this.padre;
    }


    //SETTERS
    public void setTipoNodo(String tipo){
        this.tipoNodo = tipo;
    }

    public void setProfundidad(int profundidad){
        this.profundidad = profundidad;
    }

    public void setUtilidad(int utilidad){
        this.utilidad = utilidad;
    }

    public void setPadre(Node nodo){
        this.padre = nodo;
    }

    public void setMaxValue(int max){
        this.maxValue = max;
    }
    
    public void setMinValue(int min){
        this.minValue = min;
    }

    
}
