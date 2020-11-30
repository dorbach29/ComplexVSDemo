import java.util.Scanner;

//As a matrix has it's respective vectors (or sub-arrays) listed out one next to the other (not one ontop of the other)
//This program prints out the sub arrays of a matrix

public class Matrix {

    
    public static void main(String[] args){
        float[][][] m1 = getMatrix();
        float[][][] m2 = getMatrix();
        printMatrix(mulMatMat(m1, m2));
    } 
    /*  LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC */



 
 
    //To keep in touch with the MathNotation this is the equivalent of doing a matrix multiplication with m1 on the left and m2 on the right
    static float[][][] mulMatMat(float[][][] m1, float[][][] m2){
        float[][][] matrix = new float[m2.length][][]; 
        for(int vec = 0 ; vec < m2.length; vec ++){
            //Applying matrix 1 to each vector in matrix 2
            matrix[vec] = mulMatVec(m1, m2[vec]); 
        }
        return matrix; 
    }

    static float[][] mulMatVec(float[][][] mat, float[][] vec){
        float[][][] newMat = new float[mat.length][mat[0].length][]; 
        for(int i = 0 ; i < vec.length; i++){
            for(int j = 0; j < mat[0].length ; j++){
                newMat[i][j] = mulCom(mat[i][j], vec[i]);
            }
        }
        return sumMat(newMat);
    } 

    //Sums all the vectors in one matrix into one single vector 
    static float[][] sumMat(float[][][] mat){
        float[][] vector = new float[mat[0].length][];
        //For each row in the vectors of the matrix
        for(int row = 0; row < mat[0].length; row ++){
            float[] sum = new float[]{0 ,0};
            //Adding up the values at that row for all vectors
            for(int vec = 0; vec < mat.length; vec ++){
                sum = addCom(sum, mat[vec][row]);
            }
            vector[row] = sum; 
        }
        return vector; 
    }

    static float[] sumVec(float[][] vector){
        float[] c = new float[]{0, 0};
        for(int i = 0; i < vector.length; i ++ ){
            c[0] += vector[i][0];
            c[1] += vector[1][1]; 
        }
        return c; 
    }

    //add c1 and c2
    static float[] addCom(float[] c1, float[] c2){
        return new float[]{c1[0] + c2[0], c1[1] + c2[1]}; 
    }

    //multiply c1 by c2
    static float[] mulCom(float[] c1, float[] c2){
        return new float[]{
            c1[0] * c2[0] - c1[1] * c2[1],
            c1[0] * c2[1] + c1[1] * c2[0]
        };
    }

    //conjugate a complex number
    static float[] conjCom(float[] c1){
        return new float[]{c1[0], -c1[1]}; 
    }

    //Divide c1 by c2
    static float[] divCom(float[] c1, float []c2){
        float denominator = c2[0] * c2[0] + c2[1] * c2[1];
        float[] c3 = new float[2];
        c3[0] = (c1[0] * c2[0] + c1[1] * c2[1]) / denominator ; 
        c3[1] = (c2[0]*c1[1] - c1[0]*c2[1]) / denominator ;
        return c3; 
    }


    /* INPUT INPUT INPUT INPUT INPUT INPUT INPUT INPUT INPUT INPUT INPUT INPUT INPUT INPUT INPUT INPUT  INPUT INPUT INPUT INPUT INPUT INPUT INPUT INPUT   */

    static float[][][] getMatrix(){
        Scanner dog = new Scanner(System.in);
        System.out.println("How many vectors will be in this matrix?");
        int vectors = dog.nextInt();
        System.out.println("What will be the length of each vector?");
        int length = dog.nextInt(); 
        float[][][] matrix = new float[vectors][][];

        boolean good = false;
        while(!good){
        for(int i = 0; i < vectors; i ++){
            System.out.print("For Vector " + i + "  ");
            matrix[i] = getVector(length); 
        }
        
        System.out.println("This is your Matrix: ");
        printMatrix(matrix);
        System.out.println("Is this good? (y- 1/n- 0)");
        int response = dog.nextInt();
        if(response == 1) good = true; 
        }
        return matrix; 
    }

    static float[][][] getMatrix(int vectors, int length){
        float[][][] matrix = new float[vectors][][];
        for(int i = 0; i < vectors; i ++){
            System.out.print("For Vector " + i + "  ");
            matrix[i] = getVector(length); 
        }
        return matrix; 
    }

    static float[][] getVector(int length){
        float[][] vector = new float[length][];
        for(int i = 0; i < length; i ++){
            System.out.print("For Row " + i + "  ");
            vector[i] = getComplex();
        }
        return vector; 
    }

    static float[][] getVector(){
        Scanner dog = new Scanner(System.in);
        System.out.println("What will be the length of your Vector?");
        int length = dog.nextInt();
        float[][] vector = new float[length][]; 
        for(int i = 0; i < vector.length; i++){
            System.out.print("For Row " + i + "  ");
            vector[i] = getComplex(); 
        }
        return vector; 
    }

    static float[] getComplex(){
        Scanner dog = new Scanner(System.in);
        System.out.println("Please give me a");
        float a = (float)dog.nextInt();
        System.out.println("Please give me b");
        float b = (float)dog.nextInt();
        return new float[]{a , b}; 

    }
    

    /* OUTPUT OUTPUT OUTPUT OUTPUT OUTPUT OUTPUT OUTPUT OUTPUT OUTPUT OUTPUT OUTPUT OUTPUT OUTPUT OUTPUT OUTPUT OUTPUT OUTPUT OUTPUT*/

    //Note that we print each array in the matrix vertically as opposed to horizontally
    //This is how they are represented in Mathematics and this program attempts to emulate that as well as the math logic and reasoning behind
    //The different ways Matrices and Vectors function in cordination with one another 
    static void printMatrix(float[][][] matrix){
        for(int j = 0 ; j < matrix[0].length; j ++){
            for(int i = 0 ; i < matrix.length; i ++){
                printComp(matrix[i][j]);
            }
            System.out.println(); 
        }
    }

    static void printComp(float[] comp){
        if(comp[1] < 0){
            if(comp[0] < 1)
            System.out.print("["+comp[0] + " " + comp[1] + "i]  ");
            else 
            System.out.print("[ "+comp[0] + " " + comp[1] + "i]  ");
        }
        else{
            if(comp[0] < 1)
                System.out.print("["+comp[0] + " +" + comp[1] +"i]  ");
            else
                System.out.print("[ "+comp[0] + " +" + comp[1] +"i]  ");
        }
    }

    static void printVector(float[][] vector){
        for(int i = 0; i < vector.length; i++){
            printComp(vector[i]);
            System.out.println(); 
        }
    }

}