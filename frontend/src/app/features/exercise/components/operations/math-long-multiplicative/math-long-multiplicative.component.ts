import {Component, Input, OnChanges, OnInit} from '@angular/core';
import {Exercise} from "../../../../../../model/exercise";
import {DividerModule} from "primeng/divider";
import {InputTextModule} from "primeng/inputtext";
import {NgForOf} from "@angular/common";
import {PaginatorModule} from "primeng/paginator";

@Component({
  selector: 'app-math-long-multiplicative',
  standalone: true,
  imports: [
    DividerModule,
    InputTextModule,
    NgForOf,
    PaginatorModule
  ],
  templateUrl: './math-long-multiplicative.component.html',
  styleUrl: './math-long-multiplicative.component.scss'
})
export class MathLongMultiplicativeComponent implements OnInit, OnChanges {

  @Input() exercise?: Exercise;

  result: string = '';
  operator: string = '*';

  resultInputs: string[] = [];
  multiplicationSteps: number = 0;
  steps: string[][] = [];
  stepCarryOvers: string[][] = [];

  operand1: string[] = [];
  operand2: string[] = [];

  padding: number = 0;

  ngOnInit(): void {
    this.loadExercise()
  }

  ngOnChanges() {
    this.loadExercise()
  }

  loadExercise(): void {
    let resultList = JSON.parse(<string>this.exercise?.result);
    this.result = resultList[resultList.length - 1].toString() ?? '';

    let operand1Number: string = JSON.parse(<string>this.exercise?.calculationValues)[0].toString()
    this.operand1 = operand1Number.split('');
    this.operand2 = JSON.parse(<string>this.exercise?.calculationValues)[1].toString().split('');

    this.resultInputs = new Array(this.result.length).fill('');

    this.multiplicationSteps = this.operand2.length;

    for (let i = this.multiplicationSteps - 1; i >= 0; i--) {
      let length;
      if (this.operand2[i] === '0') {
        length = (Number(operand1Number) * Number(this.operand2[i])).toString().length > this.operand2.length ? (Number(operand1Number) * Number(this.operand2[i])).toString().length : this.operand2.length;
      } else {
        length = (Number(operand1Number) * Number(this.operand2[i])).toString().length;
      }
      this.steps[i] = new Array(length).fill('');
      this.stepCarryOvers[i] = new Array(length - 1).fill('');
    }
    this.steps = this.steps.slice().reverse();
    this.stepCarryOvers = this.stepCarryOvers.slice().reverse();
  }

  loadResult() {
    let reconstructedArray: string[] = [];

    for (let i = 0; i < this.multiplicationSteps; i++) {
      for (let indexResult = this.steps[i].length - 1; indexResult >= 0; indexResult--) {
        let indexCarryOver = indexResult - 1;
        reconstructedArray.push(JSON.parse(this.steps[i][indexResult] ? this.steps[i][indexResult] : '0'));
        if (indexResult === 0 && !this.stepCarryOvers[i][indexCarryOver]) {
          break;
        } else {
          reconstructedArray.push(JSON.parse(this.stepCarryOvers[i][indexCarryOver] ? this.stepCarryOvers[i][indexCarryOver] : '0'));
        }
      }
    }

    if (reconstructedArray[0] !== '0' && reconstructedArray[0] !== '' && !this.resultInputs.includes('')) {
      reconstructedArray.push(JSON.parse(this.resultInputs.join('')));
    }
    if (this.exercise) {
      this.exercise.userResult = JSON.stringify(reconstructedArray);
    }
  }

  getSubOperator(i: number): string {
    return i === 0 ? '' : '+';
  }

  trackByFn(index: number): number {
    return index;
  }
}
