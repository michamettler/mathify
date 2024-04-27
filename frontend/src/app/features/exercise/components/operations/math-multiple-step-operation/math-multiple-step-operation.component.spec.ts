import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MathMultipleStepOperationComponent } from './math-multiple-step-operation.component';

describe('MathMultipleStepOperationComponent', () => {
  let component: MathMultipleStepOperationComponent;
  let fixture: ComponentFixture<MathMultipleStepOperationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MathMultipleStepOperationComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MathMultipleStepOperationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
