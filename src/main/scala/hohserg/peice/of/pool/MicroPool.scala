package hohserg.peice.of.pool

import scala.collection.mutable

class MicroPool[A: Reusable](_create: () => A) {

  private val closeable = implicitly[Reusable[A]]

  private val freeValues = new mutable.ArrayBuffer[A]()
  private val inUseValues = new mutable.ArrayBuffer[A]()

  def create: A = {
    if (freeValues.nonEmpty) {
      val r = freeValues.remove(0)
      inUseValues += r
      r
    } else {
      val r = _create()
      inUseValues += r
      r
    }
  }

  def free(): Unit = {
    inUseValues.foreach(closeable.restate)
    freeValues ++= inUseValues
    inUseValues.clear()
  }

}
