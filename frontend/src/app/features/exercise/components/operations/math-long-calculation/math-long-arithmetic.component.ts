import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {Exercise} from "../../../../../../model/exercise";
import {InputOtpModule} from "primeng/inputotp";
import {FormsModule} from "@angular/forms";
import {NgForOf} from "@angular/common";
import {InputTextModule} from "primeng/inputtext";
import {MatDivider} from "@angular/material/divider";
import {DividerModule} from "primeng/divider";

@Component({
  selector: 'app-math-long-arithmetic',
  standalone: true,
  imports: [
    InputOtpModule,
    FormsModule,
    NgForOf,
    InputTextModule,
    MatDivider,
    DividerModule
  ],
  templateUrl: './math-long-arithmetic.component.html',
  styleUrl: './math-long-arithmetic.component.scss'
})
export class MathLongArithmeticComponent implements OnInit, OnChanges {

  @Input() exercise?: Exercise;

  result: string = '';
  operator: string = '';

  takeOverInputs: string[] = [];
  resultInputs: string[] = [];

  operand1: string[] = [];
  operand2: string[] = [];

  ngOnInit(): void {
    this.loadExercise()
  }

  ngOnChanges() {
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
    }

    this.operand1 = JSON.parse(<string>this.exercise?.calculationValues)[0].toString().split('');
    this.operand2 = JSON.parse(<string>this.exercise?.calculationValues)[1].toString().split('');

    this.takeOverInputs = new Array(Math.floor((resultList.length - 1) / 2)).fill('');
    this.resultInputs = new Array(Math.floor((resultList.length - 1) / 2) + 1).fill('');
  }

  loadResult() {
    let reconstructedArray: string[] = [];

    for (let indexResult = this.resultInputs.length - 1; indexResult >= 0; indexResult--) {
      let indexTakeOver = indexResult - 1;
      reconstructedArray.push(JSON.parse(this.resultInputs[indexResult] ? this.resultInputs[indexResult] : '0'));
      if (indexResult === 0 && !this.takeOverInputs[indexTakeOver]) {
        break;
      } else {
        reconstructedArray.push(JSON.parse(this.takeOverInputs[indexTakeOver] ? this.takeOverInputs[indexTakeOver] : '0'));
      }
    }

    if (reconstructedArray[0] !== '0' && reconstructedArray[0] !== '') {
      reconstructedArray.push(JSON.parse(this.result));
    }
    if (this.exercise) {
      this.exercise.userResult = JSON.stringify(reconstructedArray);
    }
  }

  trackByFn(index: number): number {
    return index;
  }

}
