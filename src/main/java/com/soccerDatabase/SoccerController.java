//-------------------------------------------------------------------------------------------------------------//
// Code based on a tutorial by Shekhar Gulati of SparkJava at
// https://blog.openshift.com/developing-single-page-web-applications-using-java-8-spark-mongodb-and-angularjs/
//-------------------------------------------------------------------------------------------------------------//

package com.soccerDatabase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

import static spark.Spark.*;

public class SoccerController {

    private static final String API_CONTEXT = "/api/v1";

    private final SoccerService soccerService;

    private final Logger logger = LoggerFactory.getLogger(SoccerController.class);

    public SoccerController(SoccerService soccerService) {
        this.soccerService = soccerService;
        setupEndpoints();
    }

    private void setupEndpoints() {

        get(API_CONTEXT + "/initialize", "application/json", (request, response)-> {
            try {
                soccerService.readCSV();
                soccerService.initializeTeam();
                soccerService.initializeTeamAttributes();
                soccerService.initializeTeamMemberShip();
                soccerService.initializePlayer();
                soccerService.initializePlayerAttributes();
                soccerService.initializeReferee();
                soccerService.initializeManager();
                response.status(201);
            } catch  (SoccerService.SoccerServiceException ex) {
                logger.error("Failed to fetch the list of todos");
                response.status(500);
            }
            return Collections.EMPTY_MAP;
        }, new JsonTransformer());
    }
}
