public class MultiplyTask implements Runnable {
    int [] [] matrix1, matrix2, result;
    int startRow, endRow;

    // Constructor inside the class
    public MultiplyTask(int[][] m1, int[][] m2, int[][] res, int start, int end) {
        this.matrix1 = m1;
        this.matrix2 = m2;
        this.result = res;
        this.startRow = start;
        this.endRow = end;
    }

    // run method required by Runnable interface
    public void run() {
        for (int i = startRow; i < endRow; i++) {
            for (int j = 0; j < matrix2[0].length; j++) {
                result[i][j] = 0;
                for (int k = 0; k < matrix1[0].length; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }
    }

    // Main method to test
    public static void main(String[] args) throws InterruptedException {
        int size = 4;
        int[][] matrix1 = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 0, 1, 2},
            {3, 4, 5, 6}
        };

        int[][] matrix2 = {
            {7, 8, 9, 0},
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 0, 1, 2}
        };

        int[][] result = new int[size][size];

        int numThreads = 2;
        Thread[] threads = new Thread[numThreads];
        int rowsPerThread = size / numThreads;

        for (int i = 0; i < numThreads; i++) {
            int start = i * rowsPerThread;
            int end = (i == numThreads - 1) ? size : start + rowsPerThread;
            threads[i] = new Thread(new MultiplyTask(matrix1, matrix2, result, start, end));
            threads[i].start();
        }

        // Wait for all threads to finish
        for (int i = 0; i < numThreads; i++) {
            threads[i].join();
        }

        // Print the result matrix
        System.out.println("Result Matrix:");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(result[i][j] + " ");
            }
            System.out.println();
        }
    }
}