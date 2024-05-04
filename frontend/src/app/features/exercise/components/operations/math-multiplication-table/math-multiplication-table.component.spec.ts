import {ComponentFixture, TestBed} from '@angular/core/testing';

import {MathMultiplicationTableComponent} from './math-multiplication-table.component';

describe('MathMultiplicationTableComponent', () => {
  let component: MathMultiplicationTableComponent;
  let fixture: ComponentFixture<MathMultiplicationTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MathMultiplicationTableComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(MathMultiplicationTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
