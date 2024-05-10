import {Component, Input, OnChanges, OnInit} from '@angular/core';
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
  templateUrl: './math-sorting-operation.component.html',
  styleUrl: './math-sorting-operation.component.scss'
})
export class MathSortingOperationComponent implements OnInit, OnChanges {
  @Input() exercise?: Exercise;

  numbers: [] = [];

  ngOnInit(): void {
    this.loadExercise();
    if (this.numbers && this.exercise) {
      this.exercise.userResult = JSON.stringify(this.numbers);
    }
  }

  ngOnChanges(): void {
    this.loadExercise();
  }

  loadExercise(): void {
    if (this.exercise?.calculationValues) {
      this.numbers = JSON.parse(this.exercise.calculationValues);
    }
  }

  drop(event: CdkDragDrop<string[]>) {
    if (this.exercise?.calculationValues) {
      moveItemInArray(this.numbers, event.previousIndex, event.currentIndex);
      this.loadResult();
    }
  }

  loadResult(): void {
    if (this.numbers && this.exercise) {
      this.exercise.userResult = JSON.stringify(this.numbers);
    }
  }

}
