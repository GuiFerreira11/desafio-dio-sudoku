package desafio.dio.sudoku.model;

import java.util.Collection;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static desafio.dio.sudoku.model.StatusEnum.*;

public class Grid {

  private final List<List<Cell>> CELLS;

  public Grid(List<List<Cell>> cells) {
    this.CELLS = cells;
  }

  public List<List<Cell>> getCELLS() {
    return CELLS;
  }

  public StatusEnum getStatus() {
    if (CELLS.stream().flatMap(Collection::stream).noneMatch(c -> !c.isVISIBLE() && nonNull(c.getNumber()))) {
      return NON_STARTED;
    }
    return CELLS.stream().flatMap(Collection::stream).anyMatch(c -> isNull(c.getNumber())) ? INCOMPLETE : COMPLETE;
  }

  public boolean hasErrors() {
    if (getStatus().equals(NON_STARTED)) {
      return false;
    }
    return CELLS.stream().flatMap(Collection::stream)
        .anyMatch(c -> nonNull(c.getNumber()) && !c.getNumber().equals(c.getEXPECTED()));
  }

  public boolean changeNumber(int row, int col, int number) {
    Cell cell = CELLS.get(row).get(col);
    if (cell.isVISIBLE()) {
      return false;
    }
    cell.setNumber(number);
    return true;
  }
  
  public boolean clearNumber(int row, int col) {
    Cell cell = CELLS.get(row).get(col);
    if (cell.isVISIBLE()) {
      return false;
    }
    cell.clear();
    return true;
  }

  public void reset(){
    CELLS.forEach(row -> row.forEach(Cell::clear));
  }

  public boolean finished(){
    return !hasErrors() && getStatus().equals(COMPLETE);
  }

}
