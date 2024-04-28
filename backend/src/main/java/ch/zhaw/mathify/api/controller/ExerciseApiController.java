package ch.zhaw.mathify.api.controller;

import ch.zhaw.mathify.api.security.SessionHandler;
import ch.zhaw.mathify.model.User;
import ch.zhaw.mathify.model.exercise.Exercise;
import ch.zhaw.mathify.model.exercise.ExerciseDto;
import ch.zhaw.mathify.service.ExerciseService;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.NoSuchElementException;

/**
 * Controller for handling exercise API requests.
 */
public class ExerciseApiController {
    private static final Logger LOG = LoggerFactory.getLogger(ExerciseApiController.class);
    private final SessionHandler sessionHandler;
    private final ExerciseService exerciseService;

    public ExerciseApiController(){
        this.sessionHandler = SessionHandler.getInstance();
        this.exerciseService = new ExerciseService();
    }

    /**
     * Generates an exercise with the given exerciseSubType and grade.
     *
     * @param ctx Context of the request
     */
    public void getExerciseFromSubtypeAndGrade(Context ctx) {
        User user;
        try{
            user = sessionHandler.getUserByToken(ctx.header("Authorization"));
            Exercise exercise = exerciseService.createExerciseForUser(user);
            ctx.json(exercise.toDto());
        } catch (NoSuchElementException e){
            String responseMessage = "Invalid token";
            LOG.error(responseMessage);
            ctx.result(responseMessage);
            ctx.status(401);
        }
    }

    /**
     * Handles the result of an exercise.
     *
     * @param ctx Context of the request
     */
    public void handleResult(Context ctx) {
        LOG.info("Handling result...");
        ExerciseDto exerciseDto = ctx.bodyAsClass(ExerciseDto.class);
        if (exerciseDto == null) {
            String responseMessage = "Invalid request body";
            LOG.error(responseMessage);
            ctx.result(responseMessage);
            ctx.status(400);
            return;
        }

        if (exerciseDto.fromDto().verifyResult()) {
            LOG.info("Result is correct");
            ctx.status(200);
            ctx.json(true);
        } else {
            LOG.info("Result is incorrect");
            ctx.status(200);
            ctx.json(false);
        }
    }
}
