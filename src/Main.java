import java.io.*;

public class Main {
    public static void main(String[] args) {
        String[] s = {"/C:/Users/zer0c/Desktop/Butusov/input4.txt","/C:/Users/zer0c/Desktop/Butusov/input2.txt", "/C:/Users/zer0c/Desktop/Butusov/input3.txt"};
        Main main = new Main();
        main.mergeSort(s);
    }
    public void mergeSort( String... strings){
        String line;
        String[] outputArray = new String[0];

        for(int i = 0; i < strings.length; i++){
            try(BufferedReader reader = new BufferedReader(new FileReader(strings[i]))) {
                String inputLine = "";
                while ((line = reader.readLine()) != null){
                    inputLine += line + "\n";
                }
                String[] inputArray = inputLine.split("\n");
                String[] resultArray = new String[inputArray.length + outputArray.length];
                int inputPosition = 0;
                int outputPosition = 0;
                for(int j = 0; j < resultArray.length; j++){
                    if(inputPosition == inputArray.length){
                        resultArray[j] = outputArray[outputPosition++];
                    } else if (outputPosition == outputArray.length) {
                        resultArray[j] = inputArray[inputPosition++];
                    } else if(inputArray[inputPosition].compareTo(outputArray[outputPosition]) < 0){
                        resultArray[j] = inputArray[inputPosition++];
                    } else{
                        resultArray[j] = outputArray[outputPosition++];
                    }
                }
                outputArray = new String[resultArray.length];
                for(int j = 0; j < resultArray.length; j++){
                    outputArray[j] = resultArray[j];
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("/C:/Users/zer0c/Desktop/Butusov/output2.txt"))){
            for(int i = 0; i < outputArray.length; i++){
                writer.write(outputArray[i] + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}