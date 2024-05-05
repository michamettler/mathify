import {Component, OnInit} from '@angular/core';
import {HeaderComponent} from "../../../../core/components/header/header.component";
import {Router} from "@angular/router";
import {Title} from "@angular/platform-browser";
import {ScoreboardOverviewService} from "../../services/scoreboard-overview.service";
import {User} from "../../../../../model/user";
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell,
  MatHeaderCellDef,
  MatHeaderRow,
  MatHeaderRowDef,
  MatRow,
  MatRowDef,
  MatTable
} from "@angular/material/table";
import {MatButton} from "@angular/material/button";

export interface DisplayUser extends User {
  position: number;  // Add position for display purposes
}

@Component({
  selector: 'app-scoreboard-overview',
  standalone: true,
  imports: [
    HeaderComponent,
    MatTable,
    MatColumnDef,
    MatHeaderCell,
    MatCell,
    MatHeaderRow,
    MatRow,
    MatCellDef,
    MatHeaderCellDef,
    MatHeaderRowDef,
    MatRowDef,
    MatButton
  ],
  templateUrl: './scoreboard-overview.component.html',
  styleUrl: './scoreboard-overview.component.scss'
})
export class ScoreboardOverviewComponent implements OnInit {

  users: DisplayUser[] = [];

  displayedColumns: string[] = ['position', 'username', 'level', 'experience', 'grade'];
  dataSource: DisplayUser[] = [];

  constructor(private router: Router, private titleService: Title, private scoreboardOverviewService: ScoreboardOverviewService) {
    this.titleService.setTitle('Mathify!');
  }

  ngOnInit(): void {
    this.scoreboardOverviewService.getScoreboard().subscribe({
      next: (response) => {
        const reversedData = response.slice().reverse();
        this.dataSource = reversedData.map((user: any, index: number) => ({
          ...user,
          position: index + 1
        }));
      },
      error: (err) => console.error('Failed to load scoreboard:', err)
    });
  }

  startGame() {
    this.router.navigate(['/exercise']);
  }

}
