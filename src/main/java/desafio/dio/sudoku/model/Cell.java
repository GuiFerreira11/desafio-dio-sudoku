package desafio.dio.sudoku.model;

public class Cell {

  private final boolean VISIBLE;
  private final int EXPECTED;
  private Integer number;

  public Cell(boolean visible, int expected) {
    this.VISIBLE = visible;
    this.EXPECTED = expected;
    if (VISIBLE) {
      this.number = expected;
    }
  }

  public boolean isVISIBLE() {
    return VISIBLE;
  }

  public int getEXPECTED() {
    return EXPECTED;
  }

  public Integer getNumber() {
    return number;
  }

  public void setNumber(Integer number) {
    if (VISIBLE) {
      return;
    }
    this.number = number;
  }

  public void clear() {
    number = null;
  }

}
