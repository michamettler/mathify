package ch.zhaw.mathify.api.controller;

import ch.zhaw.mathify.api.security.SessionHandler;
import ch.zhaw.mathify.maths.ExerciseGenerator;
import ch.zhaw.mathify.model.Grade;
import ch.zhaw.mathify.model.User;
import ch.zhaw.mathify.model.exercise.ExerciseDto;
import ch.zhaw.mathify.model.exercise.ExerciseSubType;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;

/**
 * Controller for handling exercise API requests.
 */
public class ExerciseApiController {
    private static final Logger LOG = LoggerFactory.getLogger(ExerciseApiController.class);

    /**
     * Generates an exercise with the given exerciseSubType and grade.
     *
     * @param ctx Context of the request
     */
    public void getExerciseFromSubtypeAndGrade(Context ctx) {
        Optional<String> exerciseSubTypeOptional = Optional.ofNullable(ctx.queryParam("exerciseSubType"));
        Optional<String> gradeOptional = Optional.ofNullable(ctx.queryParam("grade"));
        Optional<String> token = Optional.ofNullable(ctx.header("Authorization"));
        token.ifPresent(s -> System.out.println("Token: " + s));

        if (exerciseSubTypeOptional.isPresent() && gradeOptional.isPresent() && token.isPresent()) {
            try {
                User user = SessionHandler.getInstance().getUserByToken(token.get());
                LOG.info("Generating exercise with exerciseSubType: {} and grade: {} for user: {}",
                        exerciseSubTypeOptional.get(), gradeOptional.get(), user.getUsername());
                ExerciseSubType exerciseSubType = ExerciseSubType.valueOfIgnoreCase(exerciseSubTypeOptional.get());
                Grade grade = Grade.valueOfIgnoreCase(gradeOptional.get());
                int technicalScore = user.getTechnicalScore().entrySet().stream()
                        .filter(entry -> entry.getKey().equals(exerciseSubType))
                        .mapToInt(Map.Entry::getValue)
                        .findFirst()
                        .orElse(1);

                ctx.json(ExerciseGenerator.generate(grade, exerciseSubType, technicalScore).toDto());
            } catch (IllegalArgumentException e) {
                String responseMessage = "Invalid query parameters - Exercise sub type or grade not found";
                LOG.error(responseMessage);
                ctx.result(responseMessage);
                ctx.status(400);
            }
        } else {
            String responseMessage = "Missing query parameters";
            LOG.error(responseMessage);
            ctx.result(responseMessage);
            ctx.status(400);
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
