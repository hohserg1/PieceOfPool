package hohserg.peice.of.pool

trait Closeable[-A] {
  def close(v: A): Unit
}

object Closeable {
  def forJavaInterface[A <: ICloseable](v: A): Closeable[A] =
    (v: A) => v.close()

}
