package ch.zhaw.mathify.controller.apicontroller;

import ch.zhaw.mathify.maths.ExerciseGenerator;
import ch.zhaw.mathify.model.Grade;
import ch.zhaw.mathify.model.exercise.ExerciseDto;
import ch.zhaw.mathify.model.exercise.ExerciseSubType;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

        if (exerciseSubTypeOptional.isPresent() && gradeOptional.isPresent()) {
            LOG.info("Generating exercise with exerciseSubType: {} and grade: {}", exerciseSubTypeOptional.get(), gradeOptional.get());
            ExerciseSubType exerciseSubType = ExerciseSubType.valueOfIgnoreCase(exerciseSubTypeOptional.get());
            Grade grade = Grade.valueOfIgnoreCase(gradeOptional.get());

            ctx.json(ExerciseGenerator.generate(grade, exerciseSubType).toDto());
        } else {
            LOG.error("Missing query parameters");
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
            LOG.error("Invalid request body");
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
