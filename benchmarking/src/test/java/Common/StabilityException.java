package Common;

public class StabilityException extends Exception {

  private static final long serialVersionUID = 1L;

  @Override
  public String getMessage() {
//    return super.getMessage();
    return new String("Stability issue");
  }

}
