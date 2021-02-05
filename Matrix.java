import java.util.Scanner;

//As a matrix has it's respective vectors (or sub-arrays) listed out one next to the other (not one ontop of the other)
//This program prints out the sub arrays of a matrix side by side.

public class Matrix {

    
    public static void main(String[] args){
        float[][][] m1 = getMatrix();
        printComp(determMat(m1));
    } 


    /*  LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC */
    /*  LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC */
    /*  LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC LOGIC */


    //Determinant of any matrix as long as it is square!!!
    //Recusrively goes through and finds the matrix
    static float[] determMat(float [][][] m1){
        if(m1.length == 2 && m1[0].length == 2){
            return determ2x2Mat(m1);
        } else if(m1.length == m1[0].length){
            float[] determinant = new float[]{0 ,0 };
            for(int i = 0; i < m1.length ; i ++){
               
                //The Coefficent by which we will multiply the determinant
                float[] coefficent = m1[i][0];


                //Getting the Matrix that is composed of everything but the row and collumn which our coefficent is in 
                float[][][] subMat = new float[m1.length - 1][m1[0].length - 1][];
                int newVecIndex = 0; 
                for(int orgVecIndex = 0; orgVecIndex < m1.length; orgVecIndex ++ ){
                    //Skips the Column if nessecary
                    if(orgVecIndex == i) {
                        orgVecIndex ++;
                    }

                    //Automatically skips the first row everytime
                    if(orgVecIndex < m1.length){
                        for(int orgComIndex = 1; orgComIndex < m1[0].length; orgComIndex ++){
                            subMat[newVecIndex][orgComIndex - 1] = m1[orgVecIndex][orgComIndex]; 
                        }
                    }

                    newVecIndex ++; 
                }
                float[] subDeterminant = determMat(subMat);
                subDeterminant = mulCom(coefficent, subDeterminant);
                
                //Adding or Subtracting to accumulate actual Determinant
                if(i % 2 == 0){
                    determinant = addCom(determinant, subDeterminant);
                } else {
                    determinant = subCom(determinant, subDeterminant);
                }
            }

            return determinant;
        } else {
            System.out.println("determMat(): WARNING! Input must be a square Matirx");
            return new float[]{0, 0};
        }
    }

    //Determinant of 2x2 Matrix 
    static float[] determ2x2Mat(float[][][] m1){
        float[] ad = mulCom(m1[0][0] , m1[1][1]);
        float[] bc = mulCom(m1[1][0], m1[0][1]);
        return subCom(ad, bc);

    }


    //Returns Adjoint of a matrix
    static float[][][] adjMat(float[][][] m1){
        return conjMat(tposeMat(m1)); 
    }


    //Returns the transpose of a matrix
    static float[][][] tposeMat(float[][][] m1){
        float[][][] transpose = new float[m1[0].length][m1.length][]; 
        for(int i = 0; i < transpose.length ; i ++){
            for(int j = 0; j < transpose[i].length; j ++){
                transpose[i][j] = m1[j][i];
            }
        }
        return transpose; 
    }


    //Is java pass by reference or not lets find out
    static float[][][] conjMat(float[][][] m1){
        float[][][] matrix = new float[m1.length][m1[0].length][];
        for(int i = 0; i < m1.length; i ++){
            for(int j = 0; j < m1[i].length ; j ++){
                matrix[i][j] = conjCom(m1[i][j]);
            }
        }
        return matrix; 
    }
 
 
    //Multiply Two Matrixes
    //To keep in touch with the MathNotation this is the equivalent of doing a matrix multiplication with m1 on the left and m2 on the right
    //
    static float[][][] mulMatMat(float[][][] m1, float[][][] m2){
        float[][][] matrix = new float[m2.length][][]; 
        for(int vec = 0 ; vec < m2.length; vec ++){
            //Applying matrix 1 to each vector in matrix 2
            matrix[vec] = mulMatVec(m1, m2[vec]); 
        }
        return matrix; 
    }

    //Transforms a vector by the properties defined in the given matrix
    static float[][] mulMatVec(float[][][] mat, float[][] vec){
        //Mult each entry in Mat by coresponding entry in Vec, then sum them
        float[][][] newMat = new float[mat.length][mat[0].length][]; 
        for(int i = 0 ; i < vec.length; i++){
            for(int j = 0; j < mat[0].length ; j++){
                newMat[i][j] = mulCom(mat[i][j], vec[i]);
            }
        }
        return sumMat(newMat);
    } 

    //Compresses a Matrix
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

    //Sums all the numbers in a vector and returns the cmplx nmbr
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

    //Subtract c2 from c1
    static float[] subCom(float[] c1, float[] c2){
        return new float[]{c1[0] - c2[0], c1[1] - c2[1]}; 
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
    /* INPUT INPUT INPUT INPUT INPUT INPUT INPUT INPUT INPUT INPUT INPUT INPUT INPUT INPUT INPUT INPUT  INPUT INPUT INPUT INPUT INPUT INPUT INPUT INPUT   */
    /* INPUT INPUT INPUT INPUT INPUT INPUT INPUT INPUT INPUT INPUT INPUT INPUT INPUT INPUT INPUT INPUT  INPUT INPUT INPUT INPUT INPUT INPUT INPUT INPUT   */


    static float[][][] getMatrix(){
        Scanner dog = new Scanner(System.in);
        boolean good = false;
        float[][][] returnMatrix = new float[0][][]; 
        while(!good){
        //Reinitializing working Matrix
        System.out.println("How many vectors will be in this matrix?");
        int vectors = dog.nextInt();
        System.out.println("What will be the length of each vector?");
        int length = dog.nextInt(); 
        float[][][] matrix = new float[vectors][][];

        //Getting Matrix From user
        for(int i = 0; i < vectors; i ++){
            System.out.print("For Vector " + i + "  ");
            matrix[i] = getVector(length); 
        }

        //Checking to make sure the Matrix is good
        System.out.println("This is your Matrix: ");
        printMatrix(matrix);
        System.out.println("Is this good? (y/n)");
        String response = dog.next();
        if(response.charAt(0) == 'y' ) good = true;

        //Setting returnMatrix to point to working matrix
        returnMatrix = matrix;  
        }
        return returnMatrix; 
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
    /* OUTPUT OUTPUT OUTPUT OUTPUT OUTPUT OUTPUT OUTPUT OUTPUT OUTPUT OUTPUT OUTPUT OUTPUT OUTPUT OUTPUT OUTPUT OUTPUT OUTPUT OUTPUT*/
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
            if(comp[0] < 0)
            System.out.print("["+comp[0] + " " + comp[1] + "i]  ");
            else 
            System.out.print("[ "+comp[0] + " " + comp[1] + "i]  ");
        }
        else{
            if(comp[0] < 0)
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