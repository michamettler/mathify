import {Component, Input} from '@angular/core';
import {Exercise} from "../../../../../../model/exercise";
import {MatButton} from "@angular/material/button";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {NgIf} from "@angular/common";
import {User} from "../../../../../../model/user";
import {MathExerciseService} from "../../../services/math-exercise.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-math-neighbor-operation',
  standalone: true,
  imports: [
    MatButton,
    MatFormField,
    MatInput,
    MatLabel,
    NgIf,
    FormsModule
  ],
  templateUrl: './math-neighbor-operation.component.html',
  styleUrl: './math-neighbor-operation.component.scss'
})
export class MathNeighborOperationComponent {
  @Input() exercise?: Exercise;

  showSolution: boolean = false;
  showHint: boolean = false;
  hint: string = "Remember to multiply, not add.";

  firstAnswer: string = '';
  secondAnswer: string = '';

  @Input() user: User = { //TODO read from session
    grade: 'third',
    username: 'System_Admin',
    password: 'fg6i7i4bMa',
    level: 1,
    experience: 30
  };

  constructor(private mathExerciseService: MathExerciseService, private _snackBar: MatSnackBar) {
  }

  displaySolution(): void {
    this.showSolution = true;
  }

  toggleHint(): void {
    this.showHint = !this.showHint;
  }

  verify(): void {
    if (this.exercise) {
      this.exercise.userResult = JSON.stringify([Number(this.firstAnswer), Number(this.secondAnswer)]);
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
