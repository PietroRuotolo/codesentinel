package com.pietro.codesentinel;

import com.pietro.codesentinel.service.LogAnalyzer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    //./logs.txt
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args){
        System.out.print("Copy the file path here: ");
        String fileName = scanner.nextLine();
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            LogAnalyzer errorAnalyzer = new LogAnalyzer();
            errorAnalyzer.filterByError(br);
        }catch (IOException e){
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}

