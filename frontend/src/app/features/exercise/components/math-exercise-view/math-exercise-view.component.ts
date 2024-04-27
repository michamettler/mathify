import {Component, Input, OnInit} from '@angular/core';
import {HeaderComponent} from "../../../../core/components/header/header.component";
import {MatButton} from "@angular/material/button";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {NgIf} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {User} from "../../../../../model/user";
import {MathExerciseService} from "../../services/math-exercise.service";
import {Router} from "@angular/router";
import {MatCard, MatCardContent, MatCardTitle} from "@angular/material/card";
import {MatProgressBar} from "@angular/material/progress-bar";
import {Exercise} from "../../../../../model/exercise";
import {
  MathExerciseBasicOperationComponent
} from "../math-exercise-basic-operation/math-exercise-basic-operation.component";

@Component({
  selector: 'app-math-exercise-view',
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
    MatProgressBar,
    MathExerciseBasicOperationComponent
  ],
  templateUrl: './math-exercise-view.component.html',
  styleUrl: './math-exercise-view.component.scss'
})
export class MathExerciseViewComponent implements OnInit {

  exercise: Exercise | undefined;

  @Input() user: User = { //TODO read from session
    grade: 'first',
    username: 'System_Admin',
    password: 'fg6i7i4bMa',
    level: 1,
    experience: 30
  };

  constructor(private mathExerciseService: MathExerciseService, private router: Router) {
  }

  ngOnInit(): void {
    this.mathExerciseService.retrieveExercise().subscribe({
      next: (response) => {
        this.exercise = {
          exercise: response.exercise,
          result: response.result,
          exerciseSubType: response.exerciseSubType,
          userResult: undefined,
          calculationValues: undefined
        }
      }
    });
  }

}
