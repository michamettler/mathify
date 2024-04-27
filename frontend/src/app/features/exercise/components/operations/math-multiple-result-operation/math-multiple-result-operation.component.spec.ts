import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MathMultipleResultOperationComponent } from './math-multiple-result-operation.component';

describe('MathMultipleResultOperationComponent', () => {
  let component: MathMultipleResultOperationComponent;
  let fixture: ComponentFixture<MathMultipleResultOperationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MathMultipleResultOperationComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MathMultipleResultOperationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
