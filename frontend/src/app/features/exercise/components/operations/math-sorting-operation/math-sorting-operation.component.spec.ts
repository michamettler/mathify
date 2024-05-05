import {ComponentFixture, TestBed} from '@angular/core/testing';

import {MathSortingOperationComponent} from './math-sorting-operation.component';

describe('MathMultipleStepOperationComponent', () => {
  let component: MathSortingOperationComponent;
  let fixture: ComponentFixture<MathSortingOperationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MathSortingOperationComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(MathSortingOperationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
