import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MathLongCalculationComponent } from './math-long-calculation.component';

describe('MathLongCalculationComponent', () => {
  let component: MathLongCalculationComponent;
  let fixture: ComponentFixture<MathLongCalculationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MathLongCalculationComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MathLongCalculationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
