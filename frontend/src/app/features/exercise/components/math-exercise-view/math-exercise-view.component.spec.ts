import {ComponentFixture, TestBed} from '@angular/core/testing';

import {MathExerciseViewComponent} from './math-exercise-view.component';

describe('MathExerciseViewComponent', () => {
  let component: MathExerciseViewComponent;
  let fixture: ComponentFixture<MathExerciseViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MathExerciseViewComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(MathExerciseViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
