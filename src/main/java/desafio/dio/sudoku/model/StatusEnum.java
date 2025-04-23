package desafio.dio.sudoku.model;

public enum StatusEnum{
  NON_STARTED("Não iniciado"),
  INCOMPLETE("Incompleto"),
  COMPLETE("Completo");

  private String status;

  StatusEnum(String status){
    this.status = status;
  }

  public String getStatus(){
    return status;
  }
}
