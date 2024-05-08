import {ComponentFixture, TestBed} from '@angular/core/testing';

import {GradeAndModeSelectionComponent} from './grade-and-mode-selection.component';

describe('GradeModeSelectionComponent', () => {
  let component: GradeAndModeSelectionComponent;
  let fixture: ComponentFixture<GradeAndModeSelectionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GradeAndModeSelectionComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(GradeAndModeSelectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
