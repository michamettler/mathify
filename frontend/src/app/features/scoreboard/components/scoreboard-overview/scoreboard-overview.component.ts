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
import {NgClass, NgForOf} from "@angular/common";
import {TabViewModule} from "primeng/tabview";
import {MatTab, MatTabGroup} from "@angular/material/tabs";

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
    NgClass,
    TabViewModule,
    NgForOf,
    MatTab,
    MatTabGroup
  ],
  templateUrl: './scoreboard-overview.component.html',
  styleUrl: './scoreboard-overview.component.scss'
})
export class ScoreboardOverviewComponent implements OnInit {

  users: DisplayUser[] = [];

  displayedColumns: string[] = ['position', 'username', 'level', 'experience'];
  grades: string[] = ['first', 'second', 'third'];
  selectedGradeIndex: number = 0;

  dataSources: { [grade: string]: DisplayUser[] } = {}; // Stores user lists per grade

  constructor(private router: Router, private titleService: Title, private scoreboardOverviewService: ScoreboardOverviewService, private userRegistrationService: UserRegistrationService) {
    this.titleService.setTitle('Mathify!');
  }

  ngOnInit(): void {
    this.userRegistrationService.getUser().subscribe({
      next: (currentUser: User) => {
        if (currentUser.grade === 'first') {
          this.selectedGradeIndex = 0;
        } else if (currentUser.grade === 'second') {
          this.selectedGradeIndex = 1;
        } else if (currentUser.grade === 'third') {
          this.selectedGradeIndex = 2;
        }

        this.scoreboardOverviewService.getScoreboard().subscribe({
          next: (response) => {
            this.grades.forEach((grade) => {
              const reversedData = response[grade].slice().reverse();
              this.dataSources[grade] = reversedData.map((user: any, index: number) => ({
                ...user,
                position: index + 1
              }));

              this.dataSources[grade] =  this.dataSources[grade].map((user: DisplayUser) => {
                if (user.username === currentUser.username) {
                  user.username = 'You';
                  user.isCurrentUser = true;
                }
                return user;
              });
            });
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

  getTableHeader(grade: string): string {
    if (grade === 'first') {
      return '1st Grade';
    } else if (grade === 'second') {
      return '2nd Grade';
    } else if (grade === 'third') {
      return '3rd Grade';
    }
    return '';
  }
}
