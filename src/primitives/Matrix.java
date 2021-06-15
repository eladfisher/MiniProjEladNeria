package primitives;

/******************************************************************************
 *  A class that represents a mathematical matrix
 ******************************************************************************/

final public class Matrix {
    /**
     * the rows of the matrix
     */
    private final int M;
    
    /**
     * the columns of the matrix
     */
    private final int N;
    
    /**
     *  // M-by-N array of the data
     */
    private final double[][] data;
    
    //region ctors
    /**
     * create M-by-N matrix of 0's
     * @param M the rows of the matrix
     * @param N the columns of the matrix
     */
    public Matrix(int M, int N) {
        this.M = M;
        this.N = N;
        data = new double[M][N];
    }
    
    /**
     * create matrix based on 2d array
     * @param data the data for the the matrix in form of array of arrays
     */
    public Matrix(double[][] data) {
        M = data.length;
        N = data[0].length;
        this.data = new double[M][N];
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                this.data[i][j] = data[i][j];
    }
    
    /**
     * crate a 3X1 matrix of a point
     * @param p the point of the matrix
     */
    public Matrix(Point3D p) {
        M = 3;
        N = 1;
        this.data = new double[M][N];

        data[0][0] = p.getX();
        data[1][0] = p.getY();
        data[2][0] = p.getZ();
    }
    //endregion
    
    //region methods
    /**
     * multiply the matrix A by B
     * @param B the right matrix of the multiplication
     * @return the result of A(this)XB
     */
    public Matrix times(Matrix B) {
        Matrix A = this;
        if (A.N != B.M) throw new RuntimeException("Illegal matrix dimensions.");
        Matrix C = new Matrix(A.M, B.N);
        for (int i = 0; i < C.M; i++)
            for (int j = 0; j < C.N; j++)
                for (int k = 0; k < A.N; k++)
                    C.data[i][j] += (A.data[i][k] * B.data[k][j]);
        return C;
    }
    
    /**
     * getter the data of the matrix
     * @return the data of the matrix in form of array of arrays
     */
    public double[][] getData() {
        int nM = data.length;
        int nN = data[0].length;
        double[][] ndata = new double[nM][nN];
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                ndata[i][j] = data[i][j];

        return ndata;
    }
    //endregion
}
