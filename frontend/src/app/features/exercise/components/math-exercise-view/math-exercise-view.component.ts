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
  MathSingleResultOperationComponent
} from "../operations/math-single-result-operation/math-single-result-operation.component";
import {
  MathMultipleResultOperationComponent
} from "../operations/math-multiple-result-operation/math-multiple-result-operation.component";
import {SortingOperationComponent} from "../operations/math-sorting-operation/sorting-operation.component";
import {MathExerciseSubType} from "../../../../../model/mathExerciseSubType";

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
    MathSingleResultOperationComponent,
    MathMultipleResultOperationComponent,
    SortingOperationComponent
  ],
  templateUrl: './math-exercise-view.component.html',
  styleUrl: './math-exercise-view.component.scss'
})
export class MathExerciseViewComponent implements OnInit {

  exercise?: Exercise;
  category?: string;

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
          exercise: response.exercise + ' = ?',
          result: response.result,
          exerciseSubType: this.findCategory(response.exerciseSubType),
          userResult: undefined,
          calculationValues: undefined
        }
      }
    });
  }

  findCategory(operation: string): string {
    const {SingleResultOperation, MultipleResultOperation, SortingOperation, TableOperation} = MathExerciseSubType;

    if (Object.values(SingleResultOperation).includes(operation as any)) {
      this.category = 'SingleResultOperation';
      return operation
    } else if (Object.values(MultipleResultOperation).includes(operation as any)) {
      this.category = 'MultipleResultOperation';
      return operation;
    } else if (Object.values(SortingOperation).includes(operation as any)) {
      this.category = 'SortingOperation';
      return operation;
    } else if (Object.values(TableOperation).includes(operation as any)) {
      this.category = 'TableOperation';
      return operation;
    }

    return 'Unknown Category';
  }

  verify(): void {
    //TODO verify user result
  }

}
