package hohserg.peice.of.pool

import scala.collection.mutable

class MicroPool[A: Closeable](_create: () => A) {

  private val closeable = implicitly[Closeable[A]]

  private val freeValues = new mutable.ArrayBuffer[A]()
  private val inUseValues = new mutable.ArrayBuffer[A]()

  private var averageCount: Int = -1

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
    if (averageCount == -1)
      averageCount = inUseValues.size
    else
      averageCount = (averageCount + inUseValues.size) / 2

    inUseValues.foreach(closeable.close)
    freeValues ++= inUseValues
    inUseValues.clear()
  }

}