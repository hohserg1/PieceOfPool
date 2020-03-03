package hohserg.peice.of.pool

trait Reusable[-A] {
  def restate(v: A): Unit
}

object Reusable {
  implicit def forJavaInterface[A <: IReusable]: Reusable[A] =
    (v: A) => v.restate()

}
