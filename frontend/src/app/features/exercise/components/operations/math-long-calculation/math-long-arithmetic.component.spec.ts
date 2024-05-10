import {ComponentFixture, TestBed} from '@angular/core/testing';

import {MathLongArithmeticComponent} from './math-long-arithmetic.component';

describe('MathLongCalculationComponent', () => {
  let component: MathLongArithmeticComponent;
  let fixture: ComponentFixture<MathLongArithmeticComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MathLongArithmeticComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(MathLongArithmeticComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
