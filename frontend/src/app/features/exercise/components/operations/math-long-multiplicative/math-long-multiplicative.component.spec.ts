import {ComponentFixture, TestBed} from '@angular/core/testing';

import {MathLongMultiplicativeComponent} from './math-long-multiplicative.component';

describe('MathLongMultiplicativeComponent', () => {
  let component: MathLongMultiplicativeComponent;
  let fixture: ComponentFixture<MathLongMultiplicativeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MathLongMultiplicativeComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(MathLongMultiplicativeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
