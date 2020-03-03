package hohserg.peice.of.pool

import scala.collection.mutable

class MicroPool[A: Reusable](_create: () => A) {

  private val closeable = implicitly[Reusable[A]]

  private val values = new mutable.ArrayBuffer[A]()
  private var firstFreeIndex = 0

  def create: A = {
    if (firstFreeIndex < values.size) {
      val r = values(firstFreeIndex)
      firstFreeIndex += 1
      r
    } else {
      val r = _create()
      values += r
      firstFreeIndex += 1
      r
    }
  }

  def free(): Unit = {
    for (i <- 0 until firstFreeIndex)
      closeable.restate(values(i))
    firstFreeIndex = 0
  }

}
