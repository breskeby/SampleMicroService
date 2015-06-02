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
        driver.pageSource.contains("version ${versionUnderTest()}")
    }

    private String versionUnderTest() {
        try {
            final URL resource = getClass().getClassLoader().getResource("hello/versioninfo.properties");
            Properties props = new Properties()
            props.load(resource.openConnection().inputStream)
            return props.getProperty("version");
        } catch (IOException E) {
            // handle
        }
        return "[unknown]";
    }


}
