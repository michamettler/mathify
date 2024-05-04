import {Component, OnInit} from '@angular/core';
import {HeaderComponent} from "../../../../core/components/header/header.component";
import {Router} from "@angular/router";
import {Title} from "@angular/platform-browser";
import {ScoreboardOverviewService} from "../../services/scoreboard-overview.service";

@Component({
  selector: 'app-scoreboard-overview',
  standalone: true,
  imports: [
    HeaderComponent
  ],
  templateUrl: './scoreboard-overview.component.html',
  styleUrl: './scoreboard-overview.component.scss'
})
export class ScoreboardOverviewComponent implements OnInit {

  constructor(private router: Router, private titleService: Title, private scoreboardOverviewService: ScoreboardOverviewService) {
    this.titleService.setTitle('Mathify!');
  }

  ngOnInit(): void {
    this.scoreboardOverviewService.getScoreboard().subscribe({
      next: (response) => {
       console.log(response);
      }
    });
  }

}
