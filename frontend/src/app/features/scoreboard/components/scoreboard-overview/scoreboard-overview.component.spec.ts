import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ScoreboardOverviewComponent } from './scoreboard-overview.component';

describe('ScoreboardOverviewComponent', () => {
  let component: ScoreboardOverviewComponent;
  let fixture: ComponentFixture<ScoreboardOverviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ScoreboardOverviewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ScoreboardOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
