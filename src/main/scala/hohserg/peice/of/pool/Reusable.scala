package hohserg.peice.of.pool

trait Reusable[-A] {
  def restate(v: A): Unit
}

object Reusable {
  def forJavaInterface[A <: IReusable](v: A): Reusable[A] =
    (v: A) => v.restate()

}
