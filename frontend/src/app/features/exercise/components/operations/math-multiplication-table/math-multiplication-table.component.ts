import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {Exercise} from "../../../../../../model/exercise";
import {MatButton} from "@angular/material/button";
import {NgForOf, NgIf} from "@angular/common";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-math-multiplication-table',
  standalone: true,
  imports: [
    MatButton,
    NgIf,
    NgForOf,
    MatFormField,
    MatInput,
    MatLabel,
    FormsModule
  ],
  templateUrl: './math-multiplication-table.component.html',
  styleUrl: './math-multiplication-table.component.scss'
})
export class MathMultiplicationTableComponent implements OnInit, OnChanges {

  @Input() exercise?: Exercise;
  numbersMultiplicationTable: string[] = Array(10).fill('');
  number: number | undefined;

  ngOnInit(): void {
    this.loadExercise();
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.loadExercise();
  }

  loadExercise(): void {
    this.number = JSON.parse(this.exercise?.calculationValues ?? '[]')[0];
    this.numbersMultiplicationTable = Array(10).fill('');
  }

  loadResult(): void {
    if (this.exercise) {
      this.exercise.userResult = JSON.stringify(this.numbersMultiplicationTable);
    }
  }

  trackByFn(index: number): number {
    return index;
  }


}
