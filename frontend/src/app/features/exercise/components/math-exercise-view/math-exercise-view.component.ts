import {Component, OnInit, ViewChild} from '@angular/core';
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
import {Message, MessageService} from "primeng/api";
import {ToastModule} from "primeng/toast";
import {UserInputs} from "../../../../../model/userInputs";
import {MessagesModule} from "primeng/messages";
import {SpeedDialModule} from "primeng/speeddial";
import {OverlayPanelModule} from "primeng/overlaypanel";
import {UserRegistrationService} from "../../../registration/services/user-registration.service";
import Swal from 'sweetalert2'
import {MathLongCalculationComponent} from "../operations/math-long-calculation/math-long-calculation.component";

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
    ToastModule,
    MessagesModule,
    SpeedDialModule,
    OverlayPanelModule,
    MathLongCalculationComponent
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
  @ViewChild(MathLongCalculationComponent) mathLongCalculationComponent: MathLongCalculationComponent | undefined;

  exercise?: Exercise;
  category?: string;
  hintMessage: Message[] = [{severity: 'info', detail: ''}]
  showHint: boolean = false;

  userInputs: UserInputs = {
    singleSolution: '',
    lowerNeighbor: '',
    upperNeighbor: '',
    numbersSorting: [],
    numbersMultiplicationTable: Array(10).fill(''),
    numbersLongCalculation: Array(10).fill('')
  };

  user?: User;

  constructor(private mathExerciseService: MathExerciseService, private userRegistrationService: UserRegistrationService,
              private router: Router, private messageService: MessageService) {
  }

  ngOnInit(): void {
    this.loadExercise();

    this.userRegistrationService.getUser().subscribe({
      next: (response: User) => {
        this.user = response;
      }
    });
  }

  loadExercise() {
    this.mathExerciseService.retrieveExercise().subscribe({
      next: (response) => {
        this.exercise = {
          exerciseSubType: this.findCategory(response.exerciseSubType),
          exercise: response.exercise,
          result: response.result,
          userResult: '',
          calculationValues: response.calculationValues,
          hint: response.hint ? response.hint : ''
        }
        this.hintMessage = [{severity: 'info', detail: this.exercise.hint}]
        if (this.category === 'SortingOperation') {
          if (this.exercise && this.exercise.calculationValues) {
            this.exercise.userResult = this.exercise.calculationValues;
            this.userInputs.numbersSorting = JSON.parse(this.exercise.calculationValues);
          }
        }
      }
    });
  }

  findCategory(operation: string): string {
    const {
      SingleResultOperation,
      NeighborOperation,
      SortingOperation,
      TableOperation,
      LongOperations
    } = MathExerciseSubType;

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
    } else if (Object.values(LongOperations).includes(operation as any)) {
      this.category = 'LongCalculationOperation';
      return operation;
    }

    return 'Unknown Category';
  }

  skipExercise() {
    if (this.exercise) {
      this.messageService.add({
        severity: 'info',
        summary: 'Experience + 0 XP!',
        detail: 'Exercise skipped, result would have been: ' + (JSON.parse(this.exercise.result).join(', '))
      })
      this.clear();
      this.loadExercise();
    }
  }

  verify(): void {
    if (this.hasEmptyFields()) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Input missing!',
        detail: 'Please fill in all fields before verifying!'
      })
    } else {
      if (this.exercise) {
        this.mathExerciseService.verifyExercise(this.exercise).subscribe({
          next: (response: any) => {
            if (this.user) {
              this.user.experience = response.experience
              this.user.level = response.level
            }
            if (response.experience < response.experienceBefore) {
              Swal.fire({
                icon: "success",
                title: "Level Up!",
                text: "Congratulations! Keep it going!",
                showConfirmButton: false,
                timer: 4000,
                backdrop: `
                rgba(0,0,123,0.4)
                url("https://sweetalert2.github.io/images/nyan-cat.gif")
                left top
                no-repeat
              `
              })
              this.clear();
              this.loadExercise();
            } else {
              if (JSON.parse(response.correct) === true) {
                this.messageService.add({
                  severity: 'success',
                  summary: 'Experience + ' + (response.experience - response.experienceBefore) + ' XP!',
                  detail: 'Congratulations! You got it right! Keep it up!'
                })
                this.clear();
                this.loadExercise();
              } else {
                this.messageService.add({
                  severity: 'error',
                  summary: 'Experience + ' + (response.experience - response.experienceBefore) + ' XP!',
                  detail: 'Dont worry, Im sure you will get it the next time!'
                })
                this.clear();
                this.loadExercise();
              }
            }
          }
        });
      }
    }
  }

  clear(): void {
    this.userInputs = {
      singleSolution: '',
      lowerNeighbor: '',
      upperNeighbor: '',
      numbersSorting: [],
      numbersMultiplicationTable: Array(10).fill(''),
      numbersLongCalculation: Array(10).fill('')
    };
  }

  hasEmptyFields(): boolean {
    return this.exercise?.userResult === ''
  }

  toggleHint(): void {
    this.showHint = !this.showHint;
  }

  exit() {
    this.router.navigate(['/grade-mode-selection']);
  }
}
