import {TestBed} from '@angular/core/testing';

import {ScoreboardOverviewService} from './scoreboard-overview.service';

describe('ScoreboardOverviewService', () => {
  let service: ScoreboardOverviewService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ScoreboardOverviewService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
