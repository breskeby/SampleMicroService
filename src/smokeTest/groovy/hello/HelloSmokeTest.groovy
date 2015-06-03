package hello
import geb.spock.GebSpec

class HelloSmokeTest extends GebSpec {

    def "hello service is succesfully deployed"() {
        when:
        go "http://serene-eyrie-2685.herokuapp.com/"
        then:
        driver.pageSource.contains("Greetings from Gradle Summit")
    }

    def "correct version is deployed"() {
        when:
        go "http://serene-eyrie-2685.herokuapp.com/"
        then:
        driver.pageSource.contains("version ${expectedVersion()}")
    }

    private String expectedVersion() {
        return System.getProperty("expectedVersion")
    }
}
