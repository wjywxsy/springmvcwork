package com.wjyxsy.utils;

public class SortUtils {


    public static int[] mpSort(int [] array) {

        boolean changeFlag = true;
        for (int i = 0; i < array.length && changeFlag; i++) {
            changeFlag = false;

            for (int j = 0; j < array.length-i-1; j++) {
                if (array[j+1] < array[j]) {
                    // 相邻交换
                    changeFlag = true;
                    int temp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = temp;
                }
            }
        }

        return array;
    }

    public static void main(String[] args) {
        int[] result = mpSort(new int[]{2, 3, 1, 7, 4, 3, 8, 9});

        for (int i : result) {
            System.out.println(i);
        }
    }
}
