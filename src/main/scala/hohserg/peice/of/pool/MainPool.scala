package hohserg.peice.of.pool

import scala.collection.mutable

object MainPool {
  private val free = new mutable.HashSet[MicroPool[_]]()
  private val inUse = new mutable.HashSet[MicroPool[_]]()

  type Id = String

  private val microPools = new mutable.HashMap[Id, MicroPool[_]]()

  def startPool[A: Closeable](id: Id, create: () => A): MicroPool[A] =
    microPools.getOrElseUpdate(id, new MicroPool[A](create)).asInstanceOf[MicroPool[A]]

}
