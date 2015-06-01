package controllers;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import play.mvc.Http.Request;
import play.mvc.Http.Response;
import play.test.Fixtures;
import play.test.FunctionalTest;

public class BottledMessageTest extends FunctionalTest {
    private static final String TEST_UUID = "123";

    @Before
    public void setUpDB() {
        Fixtures.deleteDatabase();
        Fixtures.loadModels("data.yml");
    }

    @Test
    public void testRoutes() {
        Response response = GET("/");
        assertStatus(200, response);

        response = POST("/submit");
        assertStatus(200, response);

        Request request = newRequest();
        request.action = "BottledMessage.view";
        request.querystring = TEST_UUID;
        response = GET(request, "/");
        assertStatus(200, response);

        response = GET("/" + TEST_UUID + "/open");
        assertStatus(200, response);

        response = GET("/" + TEST_UUID + "/back");
        assertStatus(302, response);

        response = GET("/lang/en");
        assertStatus(302, response);

        response = GET("/notfound");
        assertStatus(404, response);

        response = GET("/" + UUID.randomUUID().toString());
        assertStatus(404, response);
    }
}
