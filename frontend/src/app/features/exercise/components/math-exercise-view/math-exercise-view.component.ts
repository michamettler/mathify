import {Component, Input, OnInit, ViewChild} from '@angular/core';
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
import {MathNeighborOperationComponent} from "../operations/math-neighbor-operation/math-neighbor-operation.component";
import {MathSortingOperationComponent} from "../operations/math-sorting-operation/math-sorting-operation.component";
import {MathExerciseSubType} from "../../../../../model/mathExerciseSubType";
import {
  MathMultiplicationTableComponent
} from "../operations/math-multiplication-table/math-multiplication-table.component";
import {MessageService} from "primeng/api";
import {ToastModule} from "primeng/toast";
import {UserInputs} from "../../../../../model/userInputs";

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
    MathNeighborOperationComponent,
    MathSortingOperationComponent,
    MathMultiplicationTableComponent,
    ToastModule
  ],
  providers: [MessageService],
  templateUrl: './math-exercise-view.component.html',
  styleUrl: './math-exercise-view.component.scss'
})
export class MathExerciseViewComponent implements OnInit {

  @ViewChild(MathSingleResultOperationComponent) mathSingleResultOperationComponent: MathSingleResultOperationComponent | undefined;
  @ViewChild(MathNeighborOperationComponent) mathNeighborOperationComponent: MathNeighborOperationComponent | undefined;
  @ViewChild(MathMultiplicationTableComponent) mathMultiplicationTableComponent: MathMultiplicationTableComponent | undefined;
  @ViewChild(MathSortingOperationComponent) mathSortingOperationComponent: MathSortingOperationComponent | undefined;

  exercise?: Exercise;
  category?: string;

  userInputs: UserInputs = {
    singleSolution: '',
    lowerNeighbor: '',
    upperNeighbor: '',
    numbersSorting: [],
    numbersMultiplicationTable: Array(10).fill('')
  };

  @Input() user: User = { //TODO read from session
    grade: 'first',
    username: 'System_Admin',
    password: 'fg6i7i4bMa',
    level: 1,
    experience: 30
  };

  constructor(private mathExerciseService: MathExerciseService, private router: Router, private messageService: MessageService) {
  }

  ngOnInit(): void {
    this.loadExercise();
  }

  loadExercise() {
    this.mathExerciseService.retrieveExercise().subscribe({
      next: (response) => {
        this.exercise = {
          exerciseSubType: this.findCategory(response.exerciseSubType, response.calculationValues),
          exercise: response.exercise,
          result: response.result,
          userResult: '',
          calculationValues: response.calculationValues,
          //hint: response.hint ? response.hint : '' //TODO activate after it is implemented
        }
        if (this.category === 'SortingOperation') {
          if (this.exercise && this.exercise.calculationValues) {
            this.exercise.userResult = this.exercise.calculationValues;
            this.userInputs.numbersSorting = JSON.parse(this.exercise.calculationValues);
          }
        }
      }
    });
  }

  findCategory(operation: string, calculationValues: string): string {
    const {SingleResultOperation, NeighborOperation, SortingOperation, TableOperation} = MathExerciseSubType;

    if (Object.values(SingleResultOperation).includes(operation as any)) {
      this.category = 'SingleResultOperation';
      return operation
    } else if (Object.values(NeighborOperation).includes(operation as any)) {
      this.category = 'NeighborOperation';
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
    if (this.exercise) {
      this.mathExerciseService.verifyExercise(this.exercise).subscribe({
        next: (response) => {
          console.log(response)
          if (response === true) {
            this.messageService.add({
              severity: 'success',
              summary: 'Input was correct',
              detail: 'Congratulations! You got it right! Keep it up!'
            })
            this.clear();
            this.loadExercise();
          } else {
            this.messageService.add({
              severity: 'error',
              summary: 'User result was wrong',
              detail: 'Dont worry, Im sure you will get it the next time!'
            })
          }
        }
      });
    }
  }

  clear(): void {
    this.userInputs = {
      singleSolution: '',
      lowerNeighbor: '',
      upperNeighbor: '',
      numbersSorting: [],
      numbersMultiplicationTable: Array(10).fill('')
    };
  }

}
