package hello

import groovy.json.JsonSlurper
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.HttpServerErrorException
import org.springframework.web.client.RestTemplate

@RestController
class HelloController {
    String VERSION_INFO = "version"

    @RequestMapping("/")
    String index() {
        def weather = getWeather()
        return htmlFormatted("Greetings from Gradle Summit!", weather, getApplicationVersion());
    }

    String htmlFormatted(String welcomeMessage, def weather, String applicationVersions) {
        return """
<html>
    <head>
        <style>

            body {

               height: 100%;

               background: #1F2D38;
               background-color: #1F2D38;

               font-family: 'Lato', sans-serif;
               font-size: 36px;
               font-weight: normal;
               text-align: center;

               color: #ffffff; }

            .wrapper {
                min-height: 100%;
                height: auto !important;
                height: 100%;
                margin: 0 auto -4em;}

            .footer, .push {
                height: 4em;}

        </style>
    </head>
    <body>
        <div class="wrapper">
            <p align="center">
                <h1 color="white">$welcomeMessage</h1>
                Weather: ${weather.description}<br/>
            </p> <br/><br/><br/>
            <img src="bg_intro.png">
            <div class="push"></div>
        </div>
        <div class="footer">
            <p>Copyright (c) 2015, v. <em>$applicationVersions</em></p>
        </div>
    </body>
</html>

"""
    }

    private def getWeather() {
        def restTemplate = new RestTemplate()

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Mashape-Key", "Rrh1EvZ43jmsh2xjoYW6XKFN6xsRp18e5GyjsnxMHQm2OGbkWS");
        headers.set("Other-Header", "othervalue");

        HttpEntity entity = new HttpEntity(headers);

        def url = "https://community-open-weather-map.p.mashape.com/weather?callback=test&lang=en&q=Santa+Clara%2C+CA&units=%22metric%22"
        try {
            HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class)

            def body = response.body
            def json = body.substring(body.indexOf("{"), body.lastIndexOf(")"));
            def slurper = new JsonSlurper().parseText(json)

            return [description: slurper.weather.description[0]]
        }catch(HttpServerErrorException exception){
            return [description: "Weather Service is Down"]
        }

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