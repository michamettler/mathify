import {Component, OnInit} from '@angular/core';
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
import {MessagesModule} from "primeng/messages";
import {SpeedDialModule} from "primeng/speeddial";
import {OverlayPanelModule} from "primeng/overlaypanel";
import {UserRegistrationService} from "../../../registration/services/user-registration.service";
import Swal from 'sweetalert2'
import {MathLongArithmeticComponent} from "../operations/math-long-arithmetic/math-long-arithmetic.component";
import {
  MathLongMultiplicativeComponent
} from "../operations/math-long-multiplicative/math-long-multiplicative.component";

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
    MathLongArithmeticComponent,
    MathLongMultiplicativeComponent
  ],
  providers: [MessageService],
  templateUrl: './math-exercise-view.component.html',
  styleUrl: './math-exercise-view.component.scss'
})
export class MathExerciseViewComponent implements OnInit {

  exercise?: Exercise;
  category?: string;
  hintMessage: Message[] = [{severity: 'info', detail: ''}]
  showHint: boolean = false;

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
      }
    });
  }

  findCategory(operation: string): string {
    const {
      SingleResultOperation,
      NeighborOperation,
      SortingOperation,
      TableOperation,
      LongArithmeticOperation,
      LongMultiplicativeOperation
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
    } else if (Object.values(LongArithmeticOperation).includes(operation as any)) {
      this.category = 'LongArithmeticOperation';
      return operation;
    } else if (Object.values(LongMultiplicativeOperation).includes(operation as any)) {
      this.category = 'LongMultiplicativeOperation';
      return operation;
    }
    this.category = 'Unknown';
    return operation;
  }

  skipExercise() {
    if (this.exercise) {
      this.messageService.add({
        severity: 'info',
        summary: 'Experience + 0 XP!',
        detail: 'Exercise skipped, result would have been: ' + this.getResult(this.exercise)
      })
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
              this.loadExercise();
            } else {
              if (JSON.parse(response.correct) === true) {
                this.messageService.add({
                  severity: 'success',
                  summary: 'Experience + ' + (response.experience - response.experienceBefore) + ' XP!',
                  detail: 'Congratulations! You got it right! Keep it up!'
                })
                this.loadExercise();
              } else {
                this.messageService.add({
                  severity: 'error',
                  summary: 'Experience + ' + (response.experience - response.experienceBefore) + ' XP!',
                  detail: 'Dont worry, Im sure you will get it the next time! result would have been: ' + this.getResult(this.exercise)
                })
                this.loadExercise();
              }
            }
          }
        });
      }
    }
  }

  getResult(exercise: Exercise | undefined): string {
    if (exercise) {
      if (this.category === 'LongArithmeticOperation' || this.category === 'LongMultiplicativeOperation') {
        let resultList = JSON.parse(exercise.result);
        return resultList[resultList.length - 1];
      } else {
        return (JSON.parse(exercise.result).join(', '))
      }
    }
    return '';
  }

  hasEmptyFields(): boolean {
    if (this.exercise) {
      if (this.category === 'TableOperation') {
        return this.exercise.userResult === '' || JSON.parse(this.exercise.userResult).includes('');
      } else {
        return this.exercise.userResult === ''
      }
    } else {
      return true;
    }
  }

  toggleHint(): void {
    this.showHint = !this.showHint;
  }

  exit() {
    this.router.navigate(['/grade-mode-selection']);
  }
}
