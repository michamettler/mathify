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
import {UserRegistrationService} from "../../../registration/services/user-registration.service";
import {NgClass} from "@angular/common";

export interface DisplayUser extends User {
  position: number;
  isCurrentUser?: boolean;
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
    MatButton,
    NgClass
  ],
  templateUrl: './scoreboard-overview.component.html',
  styleUrl: './scoreboard-overview.component.scss'
})
export class ScoreboardOverviewComponent implements OnInit {

  users: DisplayUser[] = [];

  displayedColumns: string[] = ['position', 'username', 'level', 'experience', 'grade'];
  dataSource: DisplayUser[] = [];

  constructor(private router: Router, private titleService: Title, private scoreboardOverviewService: ScoreboardOverviewService, private userRegistrationService: UserRegistrationService) {
    this.titleService.setTitle('Mathify!');
  }

  ngOnInit(): void {
    this.userRegistrationService.getUser().subscribe({
      next: (currentUser: User) => {

        this.scoreboardOverviewService.getScoreboard().subscribe({
          next: (response) => {
            if (currentUser.grade && response[currentUser.grade]) {
              const reversedData = response[currentUser.grade].slice().reverse();
              this.dataSource = reversedData.map((user: any, index: number) => ({
                ...user,
                position: index + 1
              }));

              this.dataSource = this.dataSource.map((user: DisplayUser) => {
                if (user.username === currentUser.username) {
                  user.username = 'You';
                  user.isCurrentUser = true;
                }
                return user;
              });
            }
          },
          error: (err) => console.error('Failed to load scoreboard:', err)
        });
      },
      error: (err) => console.error('Failed to load user:', err)
    });

  }

  startGame() {
    this.router.navigate(['/exercise']);
  }

}
