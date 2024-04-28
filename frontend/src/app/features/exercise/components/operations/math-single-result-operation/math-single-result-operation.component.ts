import {Component, Input} from '@angular/core';
import {MatCard, MatCardContent, MatCardTitle} from "@angular/material/card";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {FormsModule} from "@angular/forms";
import {NgIf} from "@angular/common";
import {MatButton} from "@angular/material/button";
import {MatInput} from "@angular/material/input";
import {HeaderComponent} from "../../../../../core/components/header/header.component";
import {MatProgressBar} from "@angular/material/progress-bar";
import {Exercise} from "../../../../../../model/exercise";
import {MathExerciseService} from "../../../services/math-exercise.service";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-math-single-result-operation',
  standalone: true,
  imports: [
    MatCardTitle,
    MatCard,
    MatCardContent,
    MatFormField,
    FormsModule,
    NgIf,
    MatButton,
    MatInput,
    MatLabel,
    HeaderComponent,
    MatProgressBar
  ],
  templateUrl: './math-single-result-operation.component.html',
  styleUrl: './math-single-result-operation.component.scss'
})
export class MathSingleResultOperationComponent {
  @Input() exercise?: Exercise;
  userSolution: string = '';

  showSolution: boolean = false;
  showHint: boolean = false;
  hint: string = 'Placeholder hint';

  constructor(private mathExerciseService: MathExerciseService, private router: Router, private _snackBar: MatSnackBar) {
  }

  displaySolution(): void {
    this.showSolution = true;
  }

  toggleHint(): void {
    this.showHint = !this.showHint;
  }

  verify(): void {
    if (this.exercise) {
      this.exercise.userResult = JSON.stringify([Number(this.userSolution)]);
      this.mathExerciseService.verifyExercise(this.exercise).subscribe({
        next: (response) => {
          console.log(response)
          if (response === true) {
            this._snackBar.open("Result was correct, congratulations!", "dismiss", {
              verticalPosition: 'top',
              horizontalPosition: 'end'
            });
            this.router.navigate(['/exercise']);
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
