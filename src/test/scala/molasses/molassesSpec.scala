package molasses
import com.redis._
import org.scalatest._

class MolassesSpec extends FlatSpec with Matchers {
  "Molasses object" should "should have an activate function" in {
    val r = new RedisClient("localhost", 6379)
    Molasses.activate(r, "my_feature")
    Molasses.isActive(r, "my_feature") shouldEqual true
    Molasses.deactivate(r, "my_feature")
    Molasses.activate(r, "my_feature", 50)
    Molasses.isActive(r, "my_feature") shouldEqual false
    Molasses.deactivate(r, "my_feature")
    Molasses.isActive(r, "my_feature") shouldEqual false
    r.flushdb
  }

  it should "should have an isActive/3 function" in {
    val r = new RedisClient("localhost", 6379)
    Molasses.activate(r, "my_feature")
    Molasses.isActive(r, "my_feature", 34) shouldEqual true
    Molasses.deactivate(r, "my_feature")
    Molasses.activate(r, "my_feature", 50)
    Molasses.isActive(r, "my_feature", 34) shouldEqual true
    Molasses.activate(r, "my_feature", 3)
    Molasses.isActive(r, "my_feature", 34) shouldEqual false
    Molasses.deactivate(r, "my_feature")
    Molasses.isActive(r, "my_feature", 34) shouldEqual false
    r.flushdb
  }


}
