
import java.io.FileOutputStream
import java.nio.ByteBuffer
import scala.util.Using

//pros:
//  simple interface; one method one parameter
//cons:
//  unhandled type => exception
//  2 responsibilities (getting the bytes; writing the bytes)

trait Channel {
  def write (obj: Any): Unit
}

object FileChannel extends Channel
{
  override def write(obj: Any): Unit = {
    val bytes : Array[Byte] = obj match {
      case n:Int =>
        val bb= ByteBuffer.allocate(4) //4 bytes for Int
        bb.putInt(n)
        bb.array()

      case s:String =>
        s.getBytes()

      case invalid => throw new Exception("unhandled type")
    }

    Using(new FileOutputStream("c:/temp/test123.txt")){ os=>
      os.write(bytes)
      os.flush()

    }
  }
}


FileChannel.write("hello")