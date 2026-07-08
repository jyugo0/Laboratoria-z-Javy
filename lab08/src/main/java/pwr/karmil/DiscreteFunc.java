package pwr.karmil;

public class DiscreteFunc {
    private static double[][] result;

    static {
        System.loadLibrary("libnative");
    }
    public static double[][] normalFunc(double[][] core, double[][] matrix){
        result = new double[matrix.length-2][matrix[0].length-2];
        for (int i=1; i< matrix.length-1; i++){
            for (int j=1; j<matrix[0].length-1; j++) {
                double val =
                        matrix[i - 1][j - 1] * core[0][0] + matrix[i - 1][j] * core[0][1] + matrix[i - 1][j + 1] * core[0][2]
                                + matrix[i][j - 1] * core[1][0] + matrix[i][j] * core[1][1] + matrix[i][j + 1] * core[1][2]
                                + matrix[i + 1][j - 1] * core[2][0] + matrix[i + 1][j] * core[2][1] + matrix[i + 1][j + 1] * core[2][2];

                result[i - 1][j - 1] = val;
            }
        }
        return result;
    }
    public static native double[][] nativeFunc(double[][] core, double[][] matrix);

}
