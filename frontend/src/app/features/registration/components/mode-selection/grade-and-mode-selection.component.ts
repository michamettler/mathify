import {Component} from '@angular/core';
import {HeaderComponent} from "../../../../core/components/header/header.component";
import {MatButtonToggle, MatButtonToggleGroup} from "@angular/material/button-toggle";
import {NgForOf} from "@angular/common";
import {FormControl, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatGridList, MatGridTile} from "@angular/material/grid-list";
import {MatTab, MatTabGroup} from "@angular/material/tabs";
import {Router} from "@angular/router";
import {MatButton} from "@angular/material/button";
import {Title} from "@angular/platform-browser";
import {MatError, MatFormField, MatLabel} from "@angular/material/form-field";
import {MatOption, MatSelect} from "@angular/material/select";
import {Grade} from "../../../../../model/grade";

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
export class GradeAndModeSelectionComponent {

  gradeCards = [
    {
      grade: 1,
      text: 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam'
    },
    {
      grade: 2,
      text: 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam'
    },
    {
      grade: 3,
      text: 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam'
    }
  ]

  grades: Grade[] = [
    {title: 'Grade 1', value: 'first'},
    {title: 'Grade 2', value: 'second'},
    {title: 'Grade 3', value: 'third'},
  ];

  modeCards = [
    {
      icon: 'shuffle',
      mode: 'Mixed Mode',
      text: 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam',
      isEnabled: true
    },
    {
      icon: 'whatshot',
      mode: 'Challenge Mode',
      text: 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam',
      isEnabled: false
    },
    {
      icon: 'build',
      mode: 'Custom Mode',
      text: 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam',
      isEnabled: false
    }
  ];

  gradeControl: FormControl<Grade | null> = new FormControl<Grade | null>(this.grades[0], Validators.required);

  selectedMode: string | null = null;

  constructor(private router: Router, private titleService: Title) {
    this.titleService.setTitle('Mathify!');
  }

  OnModeChange(event: any) {
    this.selectedMode = event.value;
    console.log('Selected Mode:', this.selectedMode);
  }

  isGameReadyToStart() {
    return this.gradeControl.value && this.selectedMode;
  }

  startGame() {
    this.router.navigate(['/scoreboard']);
  }
}
