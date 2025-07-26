#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>

#define SIZE 4
#define THREADS 2

int matrix1[SIZE][SIZE] = {
    {1, 2, 3, 4},
    {5, 6, 7, 8},
    {9, 0, 1, 2},
    {3, 4, 5, 6}
};

int matrix2[SIZE][SIZE] = {
    {7, 8, 9, 0},
    {1, 2, 3, 4},
    {5, 6, 7, 8},
    {9, 0, 1, 2}
};

int result[SIZE][SIZE];

typedef struct {
    int start_row;
    int end_row;
} thread_data_t;

void* multiply_rows(void* arg) {
    thread_data_t* data = (thread_data_t*)arg;

    for (int i = data->start_row; i < data->end_row; i++) {
        for (int j = 0; j < SIZE; j++) {
            result[i][j] = 0;
            for (int k = 0; k < SIZE; k++) {
                result[i][j] += matrix1[i][k] * matrix2[k][j];
            }
        }
    }
    return NULL;
}

int main() {
    pthread_t threads[THREADS];
    thread_data_t thread_data[THREADS];

    int rows_per_thread = SIZE / THREADS;

    for (int i = 0; i < THREADS; i++) {
        thread_data[i].start_row = i * rows_per_thread;
        thread_data[i].end_row = (i == THREADS - 1) ? SIZE : (i + 1) * rows_per_thread;
        pthread_create(&threads[i], NULL, multiply_rows, &thread_data[i]);
    }

    // Wait for all threads to complete
    for (int i = 0; i < THREADS; i++) {
        pthread_join(threads[i], NULL);
    }

    // Print the result matrix
    printf("Result matrix:\n");
    for (int i = 0; i < SIZE; i++) {
        for (int j = 0; j < SIZE; j++) {
            printf("%d ", result[i][j]);
        }
        printf("\n");
    }

    return 0;
}
