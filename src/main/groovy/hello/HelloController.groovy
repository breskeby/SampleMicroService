package hello
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {
    String VERSION_INFO = "version"

    @RequestMapping("/")
    String index() {
        getApplicationVersion();
        return "<p style='align=center'><h1>Hello from Gradle Summit!</h1><font size='2'>(version ${getApplicationVersion()})</font></p>";
    }

    private String getApplicationVersion() {
        try {
            final URL resource = getClass().getClassLoader().getResource("hello/versioninfo.properties");
            Properties props = new Properties()
                    props.load(resource.openConnection().inputStream)
            return props.getProperty(VERSION_INFO);
        } catch (IOException E) {
            // handle
        }
        return "[unknown]";
    }

}