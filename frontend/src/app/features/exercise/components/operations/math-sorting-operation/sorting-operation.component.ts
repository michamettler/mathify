import {Component, Input} from '@angular/core';
import {Exercise} from '../../../../../../model/exercise';
import {MatButton} from "@angular/material/button";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {NgIf} from "@angular/common";
import {CdkDrag, CdkDragDrop, CdkDropList, moveItemInArray} from "@angular/cdk/drag-drop";

@Component({
  selector: 'app-math-sorting-operation',
  standalone: true,
  imports: [
    MatButton,
    MatFormField,
    MatInput,
    MatLabel,
    NgIf,
    CdkDropList,
    CdkDrag
  ],
  templateUrl: './sorting-operation.component.html',
  styleUrl: './sorting-operation.component.scss'
})
export class SortingOperationComponent {
  @Input() exercise?: Exercise;

  timePeriods = [
    '1',
    '3',
    '6',
  ];

  showSolution: boolean = false;
  showHint: boolean = false;
  hint: string = "Remember to multiply, not add.";

  constructor() {
  }

  displaySolution(): void {
    this.showSolution = true;
  }

  toggleHint(): void {
    this.showHint = !this.showHint;
  }

  drop(event: CdkDragDrop<string[]>) {
    moveItemInArray(this.timePeriods, event.previousIndex, event.currentIndex);
  }

}
