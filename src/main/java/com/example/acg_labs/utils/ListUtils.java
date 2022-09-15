package com.example.acg_labs.utils;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {
    public static void printList(List<List> list) {
        for (var vertex : list) {
            for (var coordinate: vertex) {
                System.out.print(coordinate + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void printFacesList(ArrayList<ArrayList<ArrayList<Integer>>> faces) {
        for (var faceGroup: faces) {
            for (var face : faceGroup) {
                for (var coordinate : face) {
                    System.out.print(coordinate + "/");
                }
                System.out.print(" ");
            }
            System.out.println();
        }

    }
}
