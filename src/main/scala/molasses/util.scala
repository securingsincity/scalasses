package molasses

object Util {
    def convertToList(listString: String): List[Int] = {
        var a = listString.split(",")
        if (listString.isEmpty)
            List()
        else
            a.isEmpty match {
                case true => List()
                case false =>
                    a.map(x => x.toInt).toList
            }
    }
}