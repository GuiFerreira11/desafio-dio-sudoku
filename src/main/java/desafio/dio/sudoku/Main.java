package desafio.dio.sudoku;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.List;

import desafio.dio.sudoku.model.Grid;
import desafio.dio.sudoku.model.Cell;
import desafio.dio.sudoku.util.ReadConfig;

import static desafio.dio.sudoku.util.Menu.MENU;
import static desafio.dio.sudoku.util.GridTemplate.GRID_TEMPLATE;
import static java.util.Objects.nonNull;
import static java.util.Objects.isNull;

public class Main {

  private static Scanner scanner = new Scanner(System.in);

  private static Grid grid;

  public static void main(String[] args) {

    Map<String, String> sudokuMap = ReadConfig.importConfig("sudoku.txt");

    while (true) {

    System.out.println(MENU);
    int option = scanner.nextInt();
      switch (option) {
        case 1 -> startGame(sudokuMap);
        case 2 -> inputNumber();
        case 3 -> removeNumber();
        case 4 -> showGameGrid();
        case 5 -> showGameStatus();
        case 6 -> clearGame();
        case 7 -> finishGame();
        case 8 -> System.exit(0);
        default -> System.out.println("Opção inválida, selecione uma opção válida do menu.");
      }
    }
  }

  private static void startGame(Map<String, String> sudokuMap) {
    if (nonNull(grid)) {
      System.out.println("O jogo já foi iniciado!");
      return;
    }

    List<List<Cell>> cells = new ArrayList<>();
    for (int i = 0; i < 9; i++) {
      cells.add(new ArrayList<>());
      for (int j = 0; j < 9; j++) {
        String cellConfig = sudokuMap.get("%s,%s".formatted(i, j));
        int numberExpected = Integer.valueOf(cellConfig.split(",")[0]);
        boolean visible = Boolean.valueOf(cellConfig.split(",")[1]);
        Cell cell = new Cell(visible, numberExpected);
        cells.get(i).add(cell);
      }
    }
    grid = new Grid(cells);
    System.out.println("O jogo está pronto para começar");
  }

  private static void inputNumber() {
    if (isNull(grid)) {
      System.out.println("O jogo ainda não foi iniciado!");
      return;
    }

    System.out.println("Informe a linha em que o numero será inserido:");
    int row = getValidNumber(0, 8);

    System.out.println("Informe a coluna em que o numero será inserido:");
    int col = getValidNumber(0, 8);

    System.out.println("Informe o número que será inserido na posição %s,%s:".formatted(row, col));
    int number = getValidNumber(1, 9);

    if (!grid.changeNumber(row, col, number)) {
      System.out.println("A posição selecionada contém um valor fixo!");
    }
  }

  private static void removeNumber() {
    if (isNull(grid)) {
      System.out.println("O jogo ainda não foi iniciado!");
      return;
    }

    System.out.println("Informe a linha em que o numero será inserido:");
    int row = getValidNumber(0, 8);

    System.out.println("Informe a coluna em que o numero será inserido:");
    int col = getValidNumber(0, 8);

    if (!grid.clearNumber(row, col)) {
      System.out.println("A posição selecionada contém um valor fixo!");
    }
  }

  private static void showGameGrid() {
    if (isNull(grid)) {
      System.out.println("O jogo ainda não foi iniciado!");
    }

    List<String> displayNumber = new ArrayList<>();

    for (List<Cell> col : grid.getCELLS()) {
      for (Cell cell : col) {
        String display = " " + (isNull(cell.getNumber()) ? " " : cell.getNumber());
        displayNumber.add(display);
      }
    }

    System.out.println("O jogo encontra-se da seguinte forma:");
    System.out.println(GRID_TEMPLATE.formatted(displayNumber.toArray()));

  }

  private static void showGameStatus() {
    if (isNull(grid)) {
      System.out.println("O jogo ainda não foi iniciado!");
    }
    System.out.println("O jogo atualmente se encontra no status %s".formatted(grid.getStatus().getStatus()));
    if(grid.hasErrors()){
      System.out.println("O jogo contém erros");
    } else {
      System.out.println("O jogo não contém erros");
    }
  }

  private static void clearGame() {
    if (isNull(grid)) {
      System.out.println("O jogo ainda não foi iniciado!");
    }

    System.out.println("Tem certeza que deseja limpar seu jogo e perder todo o seu progresso?");
    String confirm = scanner.next();
    while(!confirm.equalsIgnoreCase("sim") && !confirm.equalsIgnoreCase("não")){
      System.out.println("Informe 'sim' ou 'não':");
      confirm = scanner.next();
    }
    if (confirm.equalsIgnoreCase("sim")){
      grid.reset();
    }
  }

  private static void finishGame() {
    if (isNull(grid)) {
      System.out.println("O jogo ainda não foi iniciado!");
    }
    if(grid.finished()){
      System.out.println("Parabéns, você concluiu o jogo!");
      showGameStatus();
      grid = null;
    } else if(grid.hasErrors()) {
      System.out.println("Seu jogo contém erros, verifique os número e ajuste-os.");
    } else {
      System.out.println("Você ainda tem espaços em branco para preencher!");
    }
  }

  private static int getValidNumber(int min, int max) {
    int number = scanner.nextInt();
    while (number < min || number > max) {
      System.out.println("Informe um número entre %s e $s".formatted(min, max));
      number = scanner.nextInt();
    }
    return number;
  }
}
