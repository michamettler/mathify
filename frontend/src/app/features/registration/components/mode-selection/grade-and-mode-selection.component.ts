import {Component, OnInit} from '@angular/core';
import {HeaderComponent} from "../../../../core/components/header/header.component";
import {MatButtonToggle, MatButtonToggleGroup} from "@angular/material/button-toggle";
import {NgForOf} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatGridList, MatGridTile} from "@angular/material/grid-list";
import {MatTab, MatTabGroup} from "@angular/material/tabs";
import {Router} from "@angular/router";
import {MatButton} from "@angular/material/button";
import {Title} from "@angular/platform-browser";
import {MatError, MatFormField, MatLabel} from "@angular/material/form-field";
import {MatOption, MatSelect} from "@angular/material/select";
import {UserRegistrationService} from "../../services/user-registration.service";
import {User} from "../../../../../model/user";

@Component({
  selector: 'app-grade-mode-selection',
  standalone: true,
  imports: [
    HeaderComponent,
    MatButtonToggleGroup,
    MatButtonToggle,
    NgForOf,
    FormsModule,
    MatGridList,
    MatGridTile,
    MatTabGroup,
    MatTab,
    MatButton,
    MatFormField,
    MatSelect,
    MatOption,
    ReactiveFormsModule,
    MatLabel,
    MatError,
  ],
  templateUrl: './grade-and-mode-selection.component.html',
  styleUrl: './grade-and-mode-selection.component.scss'
})
export class GradeAndModeSelectionComponent implements OnInit {

  gradeCards = [
    {
      grade: 1,
      gradeValue: 'first',
      text: 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam'
    },
    {
      grade: 2,
      gradeValue: 'second',
      text: 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam'
    },
    {
      grade: 3,
      gradeValue: 'third',
      text: 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam'
    }
  ]

  modeCards = [
    {
      icon: 'shuffle',
      mode: 'Mixed Mode',
      modeValue: 'mixed',
      text: 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam',
      isEnabled: true
    },
    {
      icon: 'whatshot',
      mode: 'Challenge Mode',
      modeValue: 'challenge',
      text: 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam',
      isEnabled: false
    },
    {
      icon: 'build',
      mode: 'Custom Mode',
      modeValue: 'custom',
      text: 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam',
      isEnabled: false
    }
  ];

  selectedMode: string | null = null;
  selectedGrade: string | null = null;

  constructor(private router: Router, private titleService: Title, private userRegistrationService: UserRegistrationService) {
    this.titleService.setTitle('Mathify!');
  }

  ngOnInit() {
    this.userRegistrationService.getUser().subscribe({
      next: (response: User) => {
        this.selectedGrade = response.grade ? response.grade : null;
      }
    });
  }

  OnModeChange(event: any) {
    this.selectedMode = event.value;
  }

  OnGradeChange(event: any) {
    this.selectedGrade = event.value;
  }

  isGameReadyToStart() {
    return this.selectedGrade && this.selectedMode;
  }

  startGame() {
    this.router.navigate(['/scoreboard']);
  }
}
