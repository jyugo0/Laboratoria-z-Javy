package pwr.karmil;

import org.junit.jupiter.api.Test;

public class DiscreteFuncTest {

    @Test
    public void compareExecutionTimes() {
        for (int k=0; k < 30; k++){
            int size = 10000;
            double[][] mock = new double[size][size];

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    mock[i][j] = Math.random() * 255.0;
                }
            }

            double[][] gausCore = {
                    {1/16.0, 2/16.0, 1/16.0},
                    {2/16.0, 4/16.0, 2/16.0},
                    {1/16.0, 2/16.0, 1/16.0}
            };

            long startJava = System.nanoTime();
            DiscreteFunc.normalFunc(gausCore, mock);
            long endJava = System.nanoTime();

            long startNative = System.nanoTime();
            DiscreteFunc.nativeFunc(gausCore, mock);
            long endNative = System.nanoTime();

            long timeJavaMs = (endJava - startJava) / 1_000_000;
            long timeNativeMs = (endNative - startNative) / 1_000_000;

            System.out.println("Macierz " + size + "x" + size);
            System.out.println("Czas javy: " + timeJavaMs);
            System.out.println("Czas c++: " + timeNativeMs);

            if (timeNativeMs < timeJavaMs) {
                System.out.println("C++ jest szybszy o " + (timeJavaMs - timeNativeMs) + " ms");
            } else {
                System.out.println("Java jest szybsza o " + (timeNativeMs -timeJavaMs) + " ms");
            }
        }

    }
}