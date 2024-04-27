import {Component, Input, OnInit} from '@angular/core';
import {MatCard, MatCardContent, MatCardTitle} from "@angular/material/card";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {FormsModule} from "@angular/forms";
import {NgIf} from "@angular/common";
import {MatButton} from "@angular/material/button";
import {MatInput} from "@angular/material/input";
import {HeaderComponent} from "../../../../core/components/header/header.component";
import {MatProgressBar} from "@angular/material/progress-bar";
import {Exercise} from "../../../../../model/exercise";

@Component({
  selector: 'app-exercise-basic-operation',
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
  templateUrl: './math-exercise-basic-operation.component.html',
  styleUrl: './math-exercise-basic-operation.component.scss'
})
export class MathExerciseBasicOperationComponent implements OnInit {
  exercise: string = '';
  userAnswer = '';
  solution: string = '';
  exerciseType: 'addition' | 'subtraction' = this.getRandomSubtype();
  showSolution: boolean = false;
  showHint: boolean = false;
  hint: string = "Remember to multiply, not add.";

  @Input() user: User = { //TODO read from session
    grade: 'third',
    username: 'System_Admin',
    password: 'fg6i7i4bMa',
    level: 1,
    experience: 30
  };

  constructor(private mathExerciseService: MathExerciseService, private router: Router) {
  }

  ngOnInit(): void {
    this.mathExerciseService.retrieveExercise(this.user, this.exerciseType).subscribe({
      next: (response) => {
        this.exercise = response.exercise
        this.solution = response.result
      }
    });
  }

  displaySolution(): void {
    this.showSolution = true;
  }

  toggleHint(): void {
    this.showHint = !this.showHint;
  }

}
