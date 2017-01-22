package molasses
import com.redis._
import org.scalatest._

class MolassesSpec extends FlatSpec with Matchers {
  "Molasses object" should "should have an activate function" in {
    val r = new RedisClient("localhost", 6379)
    Molasses.activate(r, "my_feature")
    Molasses.isActive(r, "my_feature") shouldEqual true
    Molasses.deactivate(r, "my_feature")
    Molasses.isActive(r, "my_feature") shouldEqual false
    r.del("molasses_my_feature")
  }
}
