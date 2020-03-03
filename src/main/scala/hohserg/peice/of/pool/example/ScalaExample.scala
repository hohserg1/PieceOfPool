package hohserg.peice.of.pool.example

import java.util.UUID

import hohserg.peice.of.pool.{MainPool, Reusable}

import scala.collection.generic.Clearable
import scala.collection.mutable.ListBuffer

object ScalaExample extends App {

  val id = UUID.randomUUID().toString

  implicit val collectionCloseable: Reusable[Clearable] = (v: Clearable) => v.clear()

  def some(): Unit = {
    //get pool for ListBuffer[Int]
    val pool = MainPool.startPool(id, () => new ListBuffer[Int])

    //get pooled list
    val list = pool.create

    list += 1
    println(list)

    //clear all used lists and return to pool
    pool.free()
  }

  for (i <- 0 until 10)
    some()

}
