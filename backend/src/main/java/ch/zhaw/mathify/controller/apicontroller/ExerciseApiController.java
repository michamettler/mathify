package ch.zhaw.mathify.controller.apicontroller;

import ch.zhaw.mathify.maths.ExerciseGenerator;
import ch.zhaw.mathify.model.Grade;
import ch.zhaw.mathify.model.exercise.ExerciseSubType;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class ExerciseApiController {
    private static final Logger LOG = LoggerFactory.getLogger(ExerciseApiController.class);

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
}
