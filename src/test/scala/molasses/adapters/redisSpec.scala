package molasses.adapters
import com.redis._
import org.scalatest._

class RedisSpec extends FlatSpec with Matchers {
  "Redis object" should "should have an set and get function" in {
    val r = new RedisClient("localhost", 6379)
    Redis.set(r, "my_feature", "fooobar")
    Redis.get(r, "my_feature") shouldEqual Some("fooobar")
    r.flushdb
  }

  it should "should have an activate function that sets the value to 100" in {
    val r = new RedisClient("localhost", 6379)
    Redis.activate(r, "activate_feature")
    r.get("molasses_activate_feature") shouldEqual Some("true||100")
    r.flushdb
  }

  it should "should have an deactivate function that sets the value to 0" in {
    val r = new RedisClient("localhost", 6379)
    Redis.activate(r, "activate_feature")
    Redis.deactivate(r, "activate_feature")
    r.get("molasses_activate_feature") shouldEqual Some("false||0")
    r.flushdb
  }

  it should "should have an activate function that sets the value to a percentage" in {
    val r = new RedisClient("localhost", 6379)
    Redis.activate(r, "activate_feature", 78)
    r.get("molasses_activate_feature") shouldEqual Some("true||78|")
    r.flushdb
  }

  it should "should have an activate function that sets the value to a list of users" in {
    val r = new RedisClient("localhost", 6379)
    Redis.activate(r, "activate_feature", List("abc", "def", "ghi"))
    r.get("molasses_activate_feature") shouldEqual Some("true|abc,def,ghi|100")
    r.flushdb
  }

  it should "should have an activate function that sets the value to a user group" in {
    val r = new RedisClient("localhost", 6379)
    Redis.activate(r, "activate_feature","abc")
    r.get("molasses_activate_feature") shouldEqual Some("true|abc|100")
    r.flushdb
  }

  it should "should have an activate function that sets the value to a list of user ids" in {
    val r = new RedisClient("localhost", 6379)
    Redis.activate(r, "activate_feature", List(1, 2, 3))
    r.get("molasses_activate_feature") shouldEqual Some("true|1,2,3|100")
    r.flushdb
  }

  it should "should run get feature and return that feature " in {
    val r = new RedisClient("localhost", 6379)
    Redis.activate(r, "activate_feature", List(1, 2, 3))
    val a = Redis.getFeature(r, "activate_feature")
    a.get("name") shouldEqual "activate_feature"
    a.get("percentage") shouldEqual 100
    r.flushdb
  }
}