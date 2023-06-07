package com.gorest.crudtest;

import com.gorest.gorestinfo.GoRestSteps;
import com.gorest.testbase.TestBase;
import com.gorest.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.HashMap;
import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)

public class CRUDTest extends TestBase {
    static int userId;
    static String name = "Pinak" + TestUtils.getRandomValue();
    static String email = "Patel" + TestUtils.getRandomValue() + "@gmail.com";
    static String gender = "male";
    static String status = "Active";

    @Steps
    GoRestSteps goSteps;

    @Title("Creating new user.")
    @Test
    public void test001() {
        ValidatableResponse response = goSteps.createUser(name, email, gender, status);
        response.log().all().statusCode(201);
    }

    @Title("Verifying user was created.")
    @Test
    public void test002() {
        HashMap<String, Object> userMap = goSteps.getInfoByEmail(email);
        Assert.assertThat(userMap, hasValue(email));
        userId = (int) userMap.get("id");
    }

    @Title("Updating the user and verifying updated information.")
    @Test
    public void test003() {
        name = name + "-Updated";
        goSteps.updateUser(userId, name, email, gender, status)
                .log().all().statusCode(200);
        HashMap<String, Object> userMap = goSteps.getInfoByEmail(email);
        Assert.assertThat(userMap, hasValue(email));
    }

    @Title("Deleting the user and verifying user was deleted.")
    @Test
    public void test004() {
        goSteps.deleteUser(userId).statusCode(204);
        goSteps.getUserById(userId).statusCode(404);
    }
}
