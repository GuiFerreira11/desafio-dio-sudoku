package desafio.dio.sudoku.util;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ReadConfig {

  private static StringBuilder sb = new StringBuilder();

  public static Map<String, String> importConfig(String filePath){
    try(Scanner scanner = new Scanner(new File(filePath))) {
      while (scanner.hasNextLine()) {
        sb.append(scanner.nextLine());
      }
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }
    String[] config = sb.toString().split(" ");
    return Arrays.stream(config).collect(Collectors.toMap(k -> k.split(";")[0], v -> v.split(";")[1]));
  }


}
