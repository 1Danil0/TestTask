import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean w = true;
        int c = 2;
        while(w) {
            System.out.println("1 - ввод данных \n2 - выход");
            c = Integer.parseInt(scanner.nextLine());

            if(c == 1) {
                System.out.println("Введите тип сортировки (возрастание - \"a\" / убывание - \"d\")");
                String typeOfSort = scanner.nextLine();
                System.out.println("Введите тип значений (строки - \"s\" / числа - \"i\")");
                String typeOfValue = scanner.next();
                System.out.println("Введите число входящих файлов:");
                int n = scanner.nextInt();
                String[] inputFiles = new String[n];
                System.out.println("Введите полные имена файлов:");
                for (int i = 0; i < n; i++) {
                    inputFiles[i] = scanner.next();
                }
                System.out.println("Введите полное имя исходящего файла:");
                String outputFileName = scanner.next();
                scanner.nextLine();

                merge(typeOfValue, typeOfSort, outputFileName, inputFiles);
            }
            if(c == 2){
                w = false;
            }
        }
        scanner.close();
    }
    //чтение исходного файла
    public static String[] readInputFile(String fileName){
        String line;
        StringBuilder builder = new StringBuilder();
        String[] inputArray = null;
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            while ((line = reader.readLine()) != null){
                line = line.trim();
                if(line.contains(" ")){
                    continue;
                }
                builder.append(line + "\n");
            }
            inputArray = builder.toString().split("\n");
        } catch (FileNotFoundException e) {
            System.out.println(new StringBuilder("Вы ввели что-то не то" + e.getMessage()));
        } catch (IOException e) {
            System.out.println(new StringBuilder("Вы ввели что-то не то" + e.getMessage()));
        }
        return inputArray;
    }
    //преобразование строкового массива в числовой
    public static Integer[] convertStringArrayToInteger(String[] inputArray) throws NullPointerException{
        Integer[] array = new Integer[inputArray.length];
        for (int i = 0; i < inputArray.length; i++) {
            try {
                array[i] = Integer.parseInt(inputArray[i]);
            } catch (NumberFormatException e) {
                System.out.println("Вы ввели сортировку чисел, а передаете строку " + e.getMessage());
            }
        }
        return array;
    }
    //сортировка
    public static <T> List<T> sort(T[] inputArray, T[] outputArray) throws NullPointerException{
        int inputPosition = 0;
        int outputPosition = 0;
        List<T> temporaryList = new ArrayList<>();
        for (int j = 0; j < inputArray.length + outputArray.length; j++) {
            if (inputPosition == inputArray.length) {
                temporaryList.add(outputPosition + inputPosition, outputArray[outputPosition]);
                outputPosition++;
            } else if (outputPosition == outputArray.length) {
                temporaryList.add(inputPosition + outputPosition, inputArray[inputPosition]);
                inputPosition++;
            } else if (comparing(outputArray[outputPosition], inputArray[inputPosition])) {
                temporaryList.add(inputPosition + outputPosition, inputArray[inputPosition]);
                inputPosition++;
            } else {
                temporaryList.add(outputPosition + inputPosition, outputArray[outputPosition]);
                outputPosition++;
            }
        }
        return temporaryList;
    }
    //сравнение двух элементов неизвестных типов данных
    public static  <T> boolean comparing(T param, T param2){
        if(param instanceof Integer){
            return (Integer) param > (Integer) param2;
        }
        else  {
            int x = ((String) param).compareTo(param2.toString());
            return x > 0 ? true : false;
        }
    }
    //Для разворота массива из сортировки по возрастанию в убывающий
    public static <T> T[] reverseArray(T[] array){
        for(int i = 0, j = array.length - 1; i < j; i++, j--){
            T var = array[i];
            array[i] = array[j];
            array[j] = var;
        }
        return array;
    }
    // Для записи в выходной файл
    public static <T> void writeOutputArray(T[] outputArray, String outputFileName){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))){
            for(int i = 0; i < outputArray.length; i++) {
                writer.write(outputArray[i] + "\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println(new StringBuilder("Вы ввели что-то не то" + e.getMessage()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void merge(String typeOfValue, String typeOfSort, String outputFileName, String... strings){
        Integer[] outputIntegerArray = new Integer[0];
        String[] outputStringArray = new String[0];
        for(int i = 0; i < strings.length; i++){
            //Записываю данные из файла в массив строк
            try {
                String[] inputArray = readInputFile(strings[i]);
                //проверяю тип введеных данных
                if(typeOfValue.equalsIgnoreCase("i")){
                    //преобразую массив строк в массив чисел
                    Integer[] inputArrayInteger = convertStringArrayToInteger(inputArray);
                    //записываю результат сортировки 2 массивов в лист
                    List<Integer> integerSortedList = sort(inputArrayInteger, outputIntegerArray);
                    //Перезаписываю выходной массив чисел
                    outputIntegerArray = new Integer[integerSortedList.size()];
                    for(int k = 0; k < outputIntegerArray.length; k++){
                        outputIntegerArray[k] = integerSortedList.get(k);
                    }
                } else if(typeOfValue.equalsIgnoreCase("s")){
                    //так как здесь работа со строками то inputArray не недо конвертировать в числовой тип
                    //записываю результат сортировки 2 массивов в лист
                    List<String> stringSortedList = sort(inputArray, outputStringArray);
                    //перезаписываю выходной массив строк
                    outputStringArray = new String[stringSortedList.size()];
                    for(int k = 0; k < outputStringArray.length; k++){
                        outputStringArray[k] = stringSortedList.get(k);
                    }
                } else{
                    System.out.println("тип данных должен быть либо строковый, либо числовой");
                }
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }

        }
        //если тип сортировки по убыванию, то разворачиваю отсортированный массив
        if(typeOfSort.equalsIgnoreCase("d")){
            if(outputIntegerArray.length != 0) {
                outputIntegerArray = reverseArray(outputIntegerArray);
            } else {
                outputStringArray = reverseArray(outputStringArray);
            }
        }
        //Запись выходного массива в выходной файл
        if(outputIntegerArray.length != 0) {
            writeOutputArray(outputIntegerArray, outputFileName);
        } else {
            writeOutputArray(outputStringArray, outputFileName);
        }
    }
}
