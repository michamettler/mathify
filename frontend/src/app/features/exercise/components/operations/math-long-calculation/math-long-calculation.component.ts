import {Component, Input, OnInit} from '@angular/core';
import {Exercise} from "../../../../../../model/exercise";
import {UserInputs} from "../../../../../../model/userInputs";
import {InputOtpModule} from "primeng/inputotp";
import {FormsModule} from "@angular/forms";
import {NgForOf} from "@angular/common";
import {InputTextModule} from "primeng/inputtext";
import {MatDivider} from "@angular/material/divider";
import {DividerModule} from "primeng/divider";

@Component({
  selector: 'app-math-long-calculation',
  standalone: true,
  imports: [
    InputOtpModule,
    FormsModule,
    NgForOf,
    InputTextModule,
    MatDivider,
    DividerModule
  ],
  templateUrl: './math-long-calculation.component.html',
  styleUrl: './math-long-calculation.component.scss'
})
export class MathLongCalculationComponent implements OnInit {

  @Input() exercise?: Exercise;
  @Input() userInputs?: UserInputs;

  result: string = '';
  operator: string = '';

  takeOverInputs: string[] = [];
  resultInputs: string[] = [];

  operand1: string[] = [];
  operand2: string[] = [];

  ngOnInit(): void {
    this.loadExercise()
  }

  loadExercise(): void {
    let resultList = JSON.parse(<string>this.exercise?.result);
    this.result = resultList[resultList.length - 1].toString() ?? '';
    let exerciseSplit = this.exercise?.exercise.split(' ');
    let operatorText = exerciseSplit ? exerciseSplit[exerciseSplit.length - 1] : '';
    if (operatorText === 'addition') {
      this.operator = '+';
    } else if (operatorText === 'subtraction') {
      this.operator = '-';
    } else if (operatorText === 'multiplication') {
      this.operator = '*';
    }

    this.operand1 = JSON.parse(<string>this.exercise?.calculationValues)[0].toString().split('');
    this.operand2 = JSON.parse(<string>this.exercise?.calculationValues)[1].toString().split('');

    this.takeOverInputs = new Array(this.operand1.length + 1).fill('');
    this.resultInputs = new Array(this.operand1.length + 2).fill('');
  }

  handleUserInput() {
    let reconstructedArray: string[] = [];

    for (let indexResult = this.resultInputs.length - 1; indexResult >= 0; indexResult--) {
      let indexTakeOver = indexResult - 1;
      reconstructedArray.push(this.resultInputs[indexResult] ? this.resultInputs[indexResult] : '0');
      if ((this.resultInputs[indexResult - 1] !== '' || this.resultInputs[indexResult - 1] !== '0') && this.checkLeftTakeOver(this.resultInputs, indexResult - 1)) {
        reconstructedArray.push(this.takeOverInputs[indexTakeOver] ? this.takeOverInputs[indexTakeOver] : '0');
      } else {
        if (!this.checkLeftTakeOver(this.takeOverInputs, indexTakeOver)) {
          break;
        } else {
          reconstructedArray.push(this.takeOverInputs[indexTakeOver] ? this.takeOverInputs[indexTakeOver] : '0');
        }
      }
    }

    reconstructedArray.push(this.result);
    if (this.exercise) {
      this.exercise.userResult = JSON.stringify(reconstructedArray);
    }
  }

  trackByFn(index: number): number {
    return index;
  }

  private checkLeftTakeOver(resultInputs: string[], number: number) {
    let leftTakeOver = false;
    for (let i = number; i >= 0; i--) {
      if (resultInputs[i] !== '' && (resultInputs[i] !== '0')) {
        leftTakeOver = true;
      }
    }
    return leftTakeOver;
  }
}
