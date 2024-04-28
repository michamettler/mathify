import {Component, Input, OnInit} from '@angular/core';
import {Exercise} from '../../../../../../model/exercise';
import {MatButton} from "@angular/material/button";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {NgIf} from "@angular/common";
import {CdkDrag, CdkDragDrop, CdkDropList, moveItemInArray} from "@angular/cdk/drag-drop";
import {MatSnackBar} from "@angular/material/snack-bar";
import {MathExerciseService} from "../../../services/math-exercise.service";

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
export class SortingOperationComponent implements OnInit {
  @Input() exercise?: Exercise;

  numbers: number[] = []

  showSolution: boolean = false;
  showHint: boolean = false;
  hint: string = "Remember to multiply, not add.";

  constructor(private mathExerciseService: MathExerciseService, private _snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    this.numbers = JSON.parse(this.exercise?.calculationValues ?? '[]');
  }

  displaySolution(): void {
    this.showSolution = true;
  }

  toggleHint(): void {
    this.showHint = !this.showHint;
  }

  drop(event: CdkDragDrop<string[]>) {
    moveItemInArray(this.numbers, event.previousIndex, event.currentIndex);
  }

  verify(): void {
    if (this.exercise) {
      this.exercise.userResult = JSON.stringify(this.numbers);
      this.mathExerciseService.verifyExercise(this.exercise).subscribe({
        next: (response) => {
          console.log(response)
          if (response === true) {
            window.location.reload(); //TODO maybe find a better way to get new exercises
          } else {
            this._snackBar.open("Result was incorrect, sorry!", "dismiss", {
              verticalPosition: 'top',
              horizontalPosition: 'end'
            });
          }
        }
      });
    }
  }

}
