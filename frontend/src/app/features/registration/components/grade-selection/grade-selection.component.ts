import {Component} from '@angular/core';
import {HeaderComponent} from "../../../../core/components/header/header.component";
import {MatButtonToggle, MatButtonToggleGroup} from "@angular/material/button-toggle";
import {NgForOf} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {MatGridList, MatGridTile} from "@angular/material/grid-list";
import {MatTab, MatTabGroup} from "@angular/material/tabs";

@Component({
  selector: 'app-grade-selection',
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
  ],
  templateUrl: './grade-selection.component.html',
  styleUrl: './grade-selection.component.scss'
})
export class GradeSelectionComponent {

  gradeCards = [
    {grade: 1, text: 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam'},
    {grade: 2, text: 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam'},
    {grade: 3, text: 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam'}
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

  selectedGrade: number | null = null;
  selectedMode: string | null = null;

  constructor() {
  }

  onGradeChange(event: any) {
    this.selectedGrade = event.value;
    console.log('Selected Grade:', this.selectedGrade);
    // Additional actions based on the grade selection...
  }

  OnModeChange(event: any) {
    this.selectedMode = event.value;
    console.log('Selected Mode:', this.selectedMode);
    // Additional actions based on the grade selection...
  }

}
