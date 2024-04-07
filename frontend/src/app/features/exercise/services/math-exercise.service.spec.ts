import {TestBed} from '@angular/core/testing';

import {MathExerciseService} from './math-exercise.service';

describe('MathExerciseService', () => {
  let service: MathExerciseService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MathExerciseService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
