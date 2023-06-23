package smarthorses;
import java.util.Random;

public class World {

    private int numbers[] = {1,2,3,4,5,6,7};
    private int ancho;
    private int alto;
    private int[][] matrix;

    public World(int ancho, int alto){
        this.ancho = ancho;
        this.alto = alto;
        this.matrix = new int[ancho][alto];
    }

    //generando numeros aleatorios en el rango de 0 - 7
    public static int randomPosition(int item){
            Random random = new Random();
            item = (int) (random.nextDouble() * 8);
            return item;
    }

    //getters
    public int[][] getMatrix() {
        return matrix;
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }

    //setters

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }


    public void RandomWorld(){

        int itemX = 0;
        int itemY = 0;
        int caballoBlancoX = 0;
        int caballoBlancoY = 0;
        int caballoNegroX = 0;
        int caballoNegroY = 0;

        for (int row = 0; row < this.ancho; row++) {
            for (int column = 0; column < this.alto; column++) {
                this.matrix[row][column] = 0;
            }
        }

        //Generando los numeros de manera aleatoria 
        for (int i = 0; i < this.numbers.length; i++) {
            if(!(this.matrix[randomPosition(itemX)][randomPosition(itemY)] == numbers[i])){
            this.matrix[randomPosition(itemX)][randomPosition(itemY)] = numbers[i];
        }  
        }

        /* 
         Generando caballos:
          - Se le asina el numero 8 a la representacion del caballo blanco
          - Se le asina el numero 9 a la representacion del caballo blanco
        */

        boolean isMissingWhiteHorse = true;
        while(isMissingWhiteHorse) {
            
            if(this.matrix[randomPosition(caballoBlancoX) ][randomPosition(caballoBlancoY)] == 0) {
                this.matrix[randomPosition(caballoBlancoX)][randomPosition(caballoBlancoY)] = 8;
                isMissingWhiteHorse = false;
            }
        }
        boolean isMissingBlackHorse = true;
        while(isMissingBlackHorse) {
            
            if(this.matrix[randomPosition(caballoNegroX) ][randomPosition(caballoNegroY)] == 0) {
                this.matrix[randomPosition(caballoNegroX)][randomPosition(caballoNegroY)] = 9;
                isMissingBlackHorse = false;
            }
        }

    }

    public boolean HayCaballo(int x, int y) {
        if (this.getMatrix()[x][y] == 8 || this.getMatrix()[x][y] == 9) {
            return true;
        } else {
            return false;
        }
    }

    public void printWorld() {
        for (int[] x : this.matrix) {
            for (int y : x) {
                System.out.print(y + " ");
            }
            System.out.println();
        }
    }
}


