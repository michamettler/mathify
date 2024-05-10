import {Component, Input, OnChanges, OnInit} from '@angular/core';
import {Exercise} from "../../../../../../model/exercise";

@Component({
  selector: 'app-math-long-multiplicative',
  standalone: true,
  imports: [],
  templateUrl: './math-long-multiplicative.component.html',
  styleUrl: './math-long-multiplicative.component.scss'
})
export class MathLongMultiplicativeComponent implements OnInit, OnChanges {

  @Input() exercise?: Exercise;

  result: string = '';
  operator: string = '';

  resultInputs: string[] = [];

  operand1: string[] = [];
  operand2: string[] = [];

  ngOnInit(): void {
    this.loadExercise()
  }

  ngOnChanges() {
    this.loadExercise()
  }


  private loadExercise() {

  }
}
