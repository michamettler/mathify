import {ComponentFixture, TestBed} from '@angular/core/testing';

import {SortingOperationComponent} from './sorting-operation.component';

describe('MathMultipleStepOperationComponent', () => {
  let component: SortingOperationComponent;
  let fixture: ComponentFixture<SortingOperationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SortingOperationComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(SortingOperationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
