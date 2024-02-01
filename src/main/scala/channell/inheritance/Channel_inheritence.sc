import java.io.FileOutputStream
import scala.util.Using

//pros:
// unique responsibility in the channel
// easy to test
// unhandled type -> compile error

//cons:
// how do we extend classes we don't have control over (eg: Int)
// only one implementation
// overloaded interface (the encode() method); thi becomes a problem in classes with many traits

trait ByteEncodable {
  def encode () : Array[Byte]
}

trait  Channel {
  def write(obj : ByteEncodable) : Unit
}

case class FullName(firstName: String, lastName: String) extends  ByteEncodable
{
  override def encode(): Array[Byte] = {
    firstName.getBytes ++ lastName.getBytes
  }
}

object FileChannel extends Channel
{
  override def write(obj: ByteEncodable): Unit = {

    val bytes : Array[Byte] = obj.encode()

    Using(new FileOutputStream("c:/temp/test123.txt")){ os=>
      os.write(bytes)
      os.flush()

    }
  }
}

FileChannel.write(FullName("johnny", "black"))