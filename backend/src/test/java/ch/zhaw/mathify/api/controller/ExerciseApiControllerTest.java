package ch.zhaw.mathify.api.controller;

import ch.zhaw.mathify.api.controller.ExerciseApiController;
import ch.zhaw.mathify.model.exercise.ExerciseDto;
import io.javalin.http.Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ExerciseApiControllerTest {
    private Context ctxMock;
    private ExerciseApiController exerciseApiController;

    @BeforeEach
    public void setUp() {
        exerciseApiController = new ExerciseApiController();
    }

    @Test
    void testHandleResultPositive() {
        ctxMock = mock(Context.class);
        when(ctxMock.bodyAsClass(ExerciseDto.class))
                .thenReturn(
                        new ExerciseDto(
                                "1",
                                "1",
                                "exercise",
                                "1",
                                "addition"
                        )
                );

        exerciseApiController.handleResult(ctxMock);

        ArgumentCaptor<Integer> statusCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(ctxMock).status(statusCaptor.capture());
        assertEquals(200, statusCaptor.getValue());

        verify(ctxMock).json(true);
    }

    @Test
    void testHandleResultNegative() {
        ctxMock = mock(Context.class);
        when(ctxMock.bodyAsClass(ExerciseDto.class))
                .thenReturn(
                        new ExerciseDto(
                                "1",
                                "2",
                                "exercise",
                                "1",
                                "addition"
                        )
                );

        exerciseApiController.handleResult(ctxMock);

        ArgumentCaptor<Integer> statusCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(ctxMock).status(statusCaptor.capture());
        assertEquals(200, statusCaptor.getValue());

        verify(ctxMock).json(false);
    }
}
