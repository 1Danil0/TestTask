import java.io.*;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите тип сортировки (возрастание - \"a\" / убывание - \"d\":)");
        String typeOfSort = scanner.nextLine();
        System.out.println("Введите тип значений (строки - \"s\" / числа - \"i\")");
        String typeOfValue  = scanner.next();
        System.out.println("Введите число входящих файлов:");
        int n = scanner.nextInt();
        String[] inputFiles = new String[n];
        System.out.println("Введите полные имена файлов:");
        for(int i = 0; i < n; i++){
            inputFiles[i] = scanner.next();
        }
        System.out.println("Введите полное имя исходящего файла:");
        String outputFileName = scanner.next();
        mergeSort(typeOfSort, typeOfValue, outputFileName, inputFiles);
    }
    public static void mergeSort(String typeOfSort, String typeOfValue, String outputFileName, String... strings) {
        String line;
        if (typeOfValue.equalsIgnoreCase("s")) {
            String[] outputArray = new String[0];
            for (int i = 0; i < strings.length; i++) {
                try (BufferedReader reader = new BufferedReader(new FileReader(strings[i]))) {
                    String inputLine = "";
                    while ((line = reader.readLine()) != null) {
                        inputLine += line + "\n";
                    }
                    String[] inputArray = inputLine.split("\n");
                    String[] temporaryArray = new String[inputArray.length + outputArray.length];
                    int inputPosition = 0;
                    int outputPosition = 0;
                    for (int j = 0; j < temporaryArray.length; j++) {
                        if (inputPosition == inputArray.length) {
                            temporaryArray[j] = outputArray[outputPosition++];
                        } else if (outputPosition == outputArray.length) {
                            temporaryArray[j] = inputArray[inputPosition++];
                        } else if (inputArray[inputPosition].compareTo(outputArray[outputPosition]) < 0) {
                            temporaryArray[j] = inputArray[inputPosition++];
                        } else {
                            temporaryArray[j] = outputArray[outputPosition++];
                        }
                    }
                    outputArray = new String[temporaryArray.length];
                    for (int j = 0; j < temporaryArray.length; j++) {
                        outputArray[j] = temporaryArray[j];
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("You entered something wrong " + e.getMessage());;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
                if(typeOfSort.equalsIgnoreCase("d")){
                    for(int j = 0, k = outputArray.length - 1; j < k; j++, k--){
                        String temp = outputArray[k];
                        outputArray[k] = outputArray[j];
                        outputArray[j] = temp;
                    }
                }
                for (int i = 0; i < outputArray.length; i++) {
                    writer.write(outputArray[i] + "\n");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else {
            int[] outputArray = new int[0];
            for (int i = 0; i < strings.length; i++) {
                try (BufferedReader reader = new BufferedReader(new FileReader(strings[i]))) {
                    String inputLine = "";
                    while ((line = reader.readLine()) != null) {
                        inputLine += line + "\n";
                    }
                    String[] array = inputLine.split("\n");
                    int[] inputArray = new int[array.length];
                    for(int j = 0; j < inputArray.length; j++){
                        inputArray[j] = Integer.parseInt(array[j]);
                    }
                    int[] temporaryArray = new int[inputArray.length + outputArray.length];
                    int inputPosition = 0;
                    int outputPosition = 0;
                    for (int j = 0; j < temporaryArray.length; j++) {
                        if (inputPosition == inputArray.length) {
                            temporaryArray[j] = outputArray[outputPosition++];
                        } else if (outputPosition == outputArray.length) {
                            temporaryArray[j] = inputArray[inputPosition++];
                        } else if (inputArray[inputPosition] < (outputArray[outputPosition])) {
                            temporaryArray[j] = inputArray[inputPosition++];
                        } else {
                            temporaryArray[j] = outputArray[outputPosition++];
                        }
                    }
                    outputArray = new int[temporaryArray.length];
                    for (int j = 0; j < temporaryArray.length; j++) {
                        outputArray[j] = temporaryArray[j];
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("You entered something wrong " + e.getMessage());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
                if(typeOfSort.equalsIgnoreCase("d")){
                    for(int j = 0, k = outputArray.length - 1; j < k; j++, k--){
                        int temp = outputArray[k];
                        outputArray[k] = outputArray[j];
                        outputArray[j] = temp;
                    }
                }
                for (int i = 0; i < outputArray.length; i++) {
                    writer.write(outputArray[i] + "\n");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}