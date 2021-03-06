package molasses
import com.redis._
import molasses.adapters._
import java.util.zip.CRC32
object Molasses {


  def getAdapter(client: Any): StorageAdapter = client match {
    case client: RedisClient => Redis
  }
  def activate(client: Any, key: String, percentage: Integer): Unit = {
    val adapter = getAdapter(client)
    adapter.activate(client, key, percentage)
  }

  def activate(client: Any, key: String, list: List[Int]): Unit = {
    val adapter = getAdapter(client)
    adapter.activate(client, key, list)
  }
  def deactivate(client: Any, key: String): Unit = {
    val adapter = getAdapter(client)
    adapter.deactivate(client, key)
  }
  def activate(client: Any, key: String): Unit = {
    val adapter = getAdapter(client)
    adapter.activate(client, key)
  }

  def isActive(client: Any, key: String): Boolean = {
    var adapter = getAdapter(client)
    var result = adapter.getFeature(client, key)
    if (result == None) {
      false
    } else {
      (result.get("active"), result.get("percentage"))  match {
          case (false, _) => false
          case (_, 0) => false
          case (true, 100) => true
          case (_, 100) => false
          case (true, _) => false
          case (_, _) => false
      }
    }
  }

  def isActive(client: Any, key: String, userId: Integer): Boolean = {
    var adapter = getAdapter(client)
    var result = adapter.getFeature(client, key)
    if (result == None) {
      false
    } else {
      (result.get("active"), result.get("percentage"), result.get("users"))  match {
          case (false, _, _) => false
          case (_, 0, _) => false
          case (_, 100, List()) => true
          case (_, percentage:Int, List()) => {
            val id = userId.toString()
            val crc= new CRC32
            crc.update(id.getBytes)
            val crcResult:Int = (crc.getValue % 100).toInt.abs
            crcResult <= percentage
          }
          case (_, _, List()) => false
          case (_, 100, users:List[Int]) =>
            users.contains(userId)
          case (_, _, _ ) => false
      }
    }
  }


}
