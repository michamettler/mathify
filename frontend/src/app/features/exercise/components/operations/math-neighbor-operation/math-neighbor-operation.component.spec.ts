import {ComponentFixture, TestBed} from '@angular/core/testing';

import {MathNeighborOperationComponent} from './math-neighbor-operation.component';

describe('MathMultipleResultOperationComponent', () => {
  let component: MathNeighborOperationComponent;
  let fixture: ComponentFixture<MathNeighborOperationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MathNeighborOperationComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(MathNeighborOperationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
