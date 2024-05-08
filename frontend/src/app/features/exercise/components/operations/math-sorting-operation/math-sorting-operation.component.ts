import {Component, Input} from '@angular/core';
import {Exercise} from '../../../../../../model/exercise';
import {MatButton} from "@angular/material/button";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {NgIf} from "@angular/common";
import {CdkDrag, CdkDragDrop, CdkDropList, moveItemInArray} from "@angular/cdk/drag-drop";
import {UserInputs} from "../../../../../../model/userInputs";

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
  templateUrl: './math-sorting-operation.component.html',
  styleUrl: './math-sorting-operation.component.scss'
})
export class MathSortingOperationComponent {
  @Input() exercise?: Exercise;
  @Input() userInputs?: UserInputs;

  drop(event: CdkDragDrop<string[]>) {
    if (this.userInputs) {
      moveItemInArray(this.userInputs.numbersSorting, event.previousIndex, event.currentIndex);
      this.loadResult();
    }
  }

  loadResult(): void {
    if (this.userInputs) {
      if (this.exercise) {
        this.exercise.userResult = JSON.stringify(this.userInputs.numbersSorting);
      }
    }
  }

}
