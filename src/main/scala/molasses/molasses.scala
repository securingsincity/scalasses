package molasses
import com.redis._
import molasses.adapters._
object Molasses {


  def getAdapter(client: Any): StorageAdapter = client match {
    case client: RedisClient => Redis
  }
  def activate(client: Any, key: String, percentage: Integer): Unit = {
    val adapter = getAdapter(client)
    adapter.activate(client, key, percentage)
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
          case (false, 0) => false
          case (true, 100) => true
          case (_, _) => false
      }
    }
  }

  /**def isActive(client: A, key: String): Boolean = {
      Redis.isActive(client,key)
  }*/
}
