import {ComponentFixture, TestBed} from '@angular/core/testing';

import {GradeSelectionComponent} from './grade-selection.component';

describe('GradeSelectionComponent', () => {
  let component: GradeSelectionComponent;
  let fixture: ComponentFixture<GradeSelectionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GradeSelectionComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(GradeSelectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
