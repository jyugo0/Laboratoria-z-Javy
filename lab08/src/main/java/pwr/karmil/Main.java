package pwr.karmil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class Main {
    double[][] gausCore = {{ 1 /16.0,2.0/16.0,1/16.0},{2/16.0,4/16.0,2/16.0},{1/16.0,2/16.0,1/16.0}};
    double[][] sharpenCore = {{0,-1,0},{-1,5,-1},{0,-1,0}};
    double[][] sobel1Core = {{-1,0,1},{-2,0,2},{-1,0,1}};
    double[][] sobel2Core = {{1,2,1},{0,0,0},{-1,-2,-1}};

    static double[][] result_matrix_r;
    static double[][] result_matrix_g;
    static double[][] result_matrix_b;

    public static void main(String[] args) {
        Main app = new Main();
            app.useFilter("img_1.png","sobel");
    }

    public void useFilter(String img_name, String filter){
        try {

            List<double[][]> image_matrixes = loadPicture(img_name);
            double[][] r = padding(image_matrixes.get(0));
            double[][] g = padding(image_matrixes.get(1));
            double[][] b = padding(image_matrixes.get(2));

            if ("gaus".equals(filter)){
                result_matrix_r = DiscreteFunc.normalFunc(gausCore,r);
                result_matrix_g = DiscreteFunc.normalFunc(gausCore,g);
                result_matrix_b = DiscreteFunc.normalFunc(gausCore,b);
                saveMatrixToImage(result_matrix_r,result_matrix_g,result_matrix_b, "wynik1.png");
                result_matrix_r = DiscreteFunc.nativeFunc(gausCore,r);
                result_matrix_g = DiscreteFunc.nativeFunc(gausCore,g);
                result_matrix_b = DiscreteFunc.nativeFunc(gausCore,b);
                saveMatrixToImage(result_matrix_r,result_matrix_g,result_matrix_b, "wynik2.png");
            } else if ("sobel".equals(filter)) {
                result_matrix_r = DiscreteFunc.normalFunc(sobel1Core,r);
                result_matrix_r = DiscreteFunc.normalFunc(sobel2Core,result_matrix_r);
                result_matrix_g = DiscreteFunc.normalFunc(sobel1Core,g);
                result_matrix_g = DiscreteFunc.normalFunc(sobel2Core,result_matrix_g);
                result_matrix_b = DiscreteFunc.normalFunc(sobel1Core,b);
                result_matrix_b = DiscreteFunc.normalFunc(sobel2Core,result_matrix_b);
                saveMatrixToImage(result_matrix_r,result_matrix_g,result_matrix_b, "wynik1.png");
                result_matrix_r = DiscreteFunc.nativeFunc(sobel1Core,r);
                result_matrix_r = DiscreteFunc.nativeFunc(sobel2Core,result_matrix_r);
                result_matrix_g = DiscreteFunc.nativeFunc(sobel1Core,g);
                result_matrix_g = DiscreteFunc.nativeFunc(sobel2Core,result_matrix_g);
                result_matrix_b = DiscreteFunc.nativeFunc(sobel1Core,b);
                result_matrix_b = DiscreteFunc.nativeFunc(sobel2Core,result_matrix_b);
                saveMatrixToImage(result_matrix_r,result_matrix_g,result_matrix_b, "wynik2.png");
            } else {
                result_matrix_r = DiscreteFunc.normalFunc(sharpenCore,r);
                result_matrix_g = DiscreteFunc.normalFunc(sharpenCore,g);
                result_matrix_b = DiscreteFunc.normalFunc(sharpenCore,b);
                saveMatrixToImage(result_matrix_r,result_matrix_g,result_matrix_b, "wynik1.png");
                result_matrix_r = DiscreteFunc.nativeFunc(sharpenCore,r);
                result_matrix_g = DiscreteFunc.nativeFunc(sharpenCore,g);
                result_matrix_b = DiscreteFunc.nativeFunc(sharpenCore,b);
                saveMatrixToImage(result_matrix_r,result_matrix_g,result_matrix_b, "wynik2.png");
            }
        } catch (IOException e) {
            System.err.println("Błąd " + e.getMessage());
        }
    }

    public double[][] padding(double[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        double[][] padded = new double[rows + 2][cols + 2];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                padded[i + 1][j + 1] = matrix[i][j];
            }
        }
        return padded;
    }

    public List<double[][]> loadPicture(String picture) throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream(picture);
        if (is == null) throw new IOException("Nie znaleziono pliku: " + picture);

        BufferedImage pic = ImageIO.read(is);
        int width = pic.getWidth();
        int height = pic.getHeight();

        double[][] r = new double[height][width];
        double[][] g = new double[height][width];
        double[][] b = new double[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = pic.getRGB(x, y);
                r[y][x] = (rgb >> 16) & 0xFF;
                g[y][x] = (rgb >> 8) & 0xFF;
                b[y][x] = rgb & 0xFF;
            }
        }
        return Arrays.asList(r, g, b);
    }

    public void saveMatrixToImage(double[][] rm, double[][] gm, double[][] bm, String path) throws IOException {
        int height = rm.length;
        int width = rm[0].length;
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int r = (int) Math.min(255, Math.max(0, rm[y][x]));
                int g = (int) Math.min(255, Math.max(0, gm[y][x]));
                int b = (int) Math.min(255, Math.max(0, bm[y][x]));
                int rgb = (r << 16) | (g << 8) | b;
                img.setRGB(x, y, rgb);
            }
        }
        ImageIO.write(img, "png", new File(path));
    }
}
