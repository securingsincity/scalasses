package molasses.adapters

trait StorageAdapter {
    def activate(client: Any, key: String): Unit
    def activate(client: Any, key: String, percentage: Integer): Unit
    def activate(client: Any, key: String, users: String): Unit
    def activate(client: Any, key: String, userList: List[Any]): Unit
    def deactivate(client: Any, key: String): Unit
    def getFeature(client: Any, key: String): Option[Map[String, Any]]
}