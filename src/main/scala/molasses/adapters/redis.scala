package molasses.adapters
import com.redis._
object Redis extends StorageAdapter {
    def get(client: Any, key: String): Option[String] = {
        var newClient:RedisClient = client.asInstanceOf[RedisClient]
        newClient.get(s"molasses_$key")
    }

    def set(client: Any, key: String, value: String): Unit = {
        var newClient:RedisClient = client.asInstanceOf[RedisClient]
        newClient.set(s"molasses_$key", value)
    }

    def activate(client: Any, key: String): Unit = {
        set(client, key,  "true||100")
    }

    def activate(client: Any, key: String, percentage: Integer): Unit = {
        set(client, key,  s"true||$percentage|")
    }

    def activate(client: Any, key: String, users: String): Unit = {
        set(client, key,  s"true|$users|100")
    }

    def activate(client: Any, key: String, userList: List[Any]): Unit = {
        var activated_users = userList.mkString(",")
        set(client, key, s"true|$activated_users|100")
    }

    def deactivate(client: Any, key: String): Unit = {
        set(client, key, "false||0")
    }

    def getFeature(client: Any, key: String): Option[Map[String, Any]] = {
        get(client, key) match {
            case None => None
            case Some(result) => {
                val Array(active,  users, percentage) = result.split('|')
                Some(Map(
                    "name" -> key,
                    "active" -> active.toBoolean,
                    "percentage" -> percentage.toInt,
                    "users" -> users
                ))
            }
        }
    }
}