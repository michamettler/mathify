import {Component, Input, OnInit} from '@angular/core';
import {Exercise} from "../../../../../../model/exercise";
import {MathExerciseService} from "../../../services/math-exercise.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {MatButton} from "@angular/material/button";
import {NgForOf, NgIf} from "@angular/common";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-math-multiplication-table',
  standalone: true,
  imports: [
    MatButton,
    NgIf,
    NgForOf,
    MatFormField,
    MatInput,
    MatLabel,
    FormsModule
  ],
  templateUrl: './math-multiplication-table.component.html',
  styleUrl: './math-multiplication-table.component.scss'
})
export class MathMultiplicationTableComponent implements OnInit {

  @Input() exercise?: Exercise;

  numbers: string[] = Array(10).fill('');
  number: number | undefined;

  showSolution: boolean = false;
  showHint: boolean = false;
  hint: string = "Remember to multiply, not add.";
  userInputs: number[] = [];
  protected readonly Number = Number;

  constructor(private mathExerciseService: MathExerciseService, private _snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    this.number = JSON.parse(this.exercise?.calculationValues ?? '[]')[0];
  }

  displaySolution(): void {
    this.showSolution = true;
  }

  toggleHint(): void {
    this.showHint = !this.showHint;
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
