
abstract class Expr {
  def eval: Int
  def print: String
  def copy: Expr
  def duplicate: Expr
}

object Expr {
  case class Plus(e1: Expr, e2: Expr) extends Expr {
    def eval = e1.eval + e2.eval
    def print = "(+ " + e1.print + " " + e2.print + ")"
    def copy: Expr = Plus(e1.copy, e2.copy)
    def duplicate: Expr = {
      Plus(e1.copy.duplicate, e2.copy.duplicate)
    }
  }

  case class Times(e1: Expr, e2: Expr) extends Expr {
    def eval = e1.eval * e2.eval
    def print = "(* " + e1.print + " " + e2.print + ")"
    def copy: Expr = Times(e1.copy, e2.copy)
    def duplicate: Expr = {
      if (e1.eval == 2) {
        Plus(e2.duplicate, e2.duplicate)
      } else if (e2.eval == 2) {
        Plus(e1.duplicate, e1.duplicate)
      } else {
        Times(e1.duplicate, e2.duplicate)
      }
    }
  }

  case class Num(value: Int) extends Expr {
    def eval = value
    def print = value.toString
    def copy: Expr = Num(value)
    def duplicate: Expr = {
      Num(value)
    }
  }

  // One simple test case:
  // (* 2 (+ 1 (* 3 2))) should duplicate to (+ (+ 1 (+ 3 3)) (+ 1 (+ 3 3)))
  def main(argv: Array[String]) {
    //val e: Expr = Times(Num(2), Plus(Num(1), Times(Num(2), Plus(Num(1), Times(Num(2), Num(3))))))
    // val e: Expr = Times(Num(2), Plus(Num(1), Times(Num(2), Plus(Num(1), Times(Num(2),  Times(Num(2), Num(2)))))))
    val e: Expr = Times(Num(2), Plus(Num(1), Times(Num(2), Plus(Num(1), Times(Num(2), Times(Num(2), Num(3)))))))
    Console.out.print(e.print + ".duplicate")
    Console.out.print(" ->  ")
    Console.out.print(e.duplicate.print)
    Console.out.print(" => ")
    Console.out.println(e.copy.eval)

  }
}