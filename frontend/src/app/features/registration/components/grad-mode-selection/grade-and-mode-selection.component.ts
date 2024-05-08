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
      text: 'Includes exercises on identifying neighbors (1-5), ordering and comparing numbers, plus addition and subtraction up to 100.'
    },
    {
      grade: 2,
      gradeValue: 'second',
      text: 'Features exercises such as halving and doubling (1-10), three-step arithmetic, two-digit calculations, and division up to 20.'
    },
    {
      grade: 3,
      gradeValue: 'third',
      text: 'Covers exercises in addition and subtraction, finding neighbors up to 1000, rounding numbers to 100, and longhand arithmetic.'
    }
  ]

  modeCards = [
    {
      icon: 'shuffle',
      mode: 'Mixed Mode',
      modeValue: 'mixed',
      text: 'Mixed mode to dynamically combine various types of exercises and enhance learning flexibility.',
      isEnabled: true
    },
    {
      icon: 'whatshot',
      mode: 'Challenge Mode',
      modeValue: 'challenge',
      text: 'Challenge mode introduces a time-based element, testing your speed and accuracy under pressure.',
      isEnabled: false
    },
    {
      icon: 'build',
      mode: 'Custom Mode',
      modeValue: 'custom',
      text: 'Custom Mode allows you to tailor the exercises to your specific needs and preferences.',
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
