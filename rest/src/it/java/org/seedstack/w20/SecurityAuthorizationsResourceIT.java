/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.w20;


import com.jayway.restassured.response.ResponseBody;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.seedstack.seed.it.AbstractSeedWebIT;

import java.net.URL;

import static com.jayway.restassured.RestAssured.given;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;


public class SecurityAuthorizationsResourceIT extends AbstractSeedWebIT {

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class);
    }


    @Test
    @RunAsClient
    public void connectThePoltergeist(@ArquillianResource URL baseURL) throws Exception {
        ResponseBody r = given().auth().basic("ThePoltergeist", "bouh").expect().statusCode(200).when().get(baseURL.toString() + "seed-w20/security/authorizations").body();
        String expected = "{\"id\":\"ThePoltergeist\",\"type\":\"user\",\"principals\":{\"userId\":\"ThePoltergeist\"},\"roles\":[{\"name\":\"jedi\",\"attributes\":{},\"permissions\":[[\"academy\",\"*\"],[\"lightSaber\",\"*\"]]},{\"name\":\"ghost\",\"attributes\":{\"scope\":[\"MU\",\"PY\"]},\"permissions\":[[\"site\",\"haunt\"]]}],\"permissions\":[]}";
        assertEquals(expected, r.asString(), false);
    }
}
