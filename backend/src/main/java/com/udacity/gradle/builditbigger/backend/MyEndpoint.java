/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.udacity.gradle.builditbigger.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import javax.inject.Named;

import ie.ianbuttimer.joker.Joker;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.builditbigger.gradle.udacity.com",
                ownerName = "backend.builditbigger.gradle.udacity.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    /** Name of sayHi method */
    public static final String SAY_HI_METHOD = "sayHi";
    /** Name of tellAJoke method */
    public static final String TELL_A_JOKE_METHOD = "tellAJoke";

    private Joker mJoker;

    /**
     * Constructor
     */
    public MyEndpoint() {
        mJoker = new Joker();
    }

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = SAY_HI_METHOD)
    public MyBean sayHi(@Named("name") String name) {
        return response("Hi, " + name);
    }

    /**
     * A simple endpoint method that returns a joke
     */
    @ApiMethod(name = TELL_A_JOKE_METHOD)
    public MyBean tellAJoke() {
        return response(mJoker.getAJoke());
    }

    /**
     * Create a response
     * @param data  Response data
     * @return  Response object
     */
    private MyBean response(String data) {
        MyBean response = new MyBean();
        response.setData(data);
        return response;
    }

}
