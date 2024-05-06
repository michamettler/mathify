import {Component, Input, OnInit} from '@angular/core';
import {Exercise} from "../../../../../../model/exercise";
import {MatButton} from "@angular/material/button";
import {NgForOf, NgIf} from "@angular/common";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {FormsModule} from "@angular/forms";
import {UserInputs} from "../../../../../../model/userInputs";

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
export class MathMultiplicationTableComponent implements OnInit {

  @Input() exercise?: Exercise;
  @Input() userInputs?: UserInputs;

  numbers: string[] = Array(10).fill('');
  number: number | undefined;

  showSolution: boolean = false;
  protected readonly Number = Number;

  ngOnInit(): void {
    this.number = JSON.parse(this.exercise?.calculationValues ?? '[]')[0];
  }

  displaySolution(): void {
    this.showSolution = true;
  }

  handleChange(event: Event, i: number) {
    if (this.userInputs) {
      this.userInputs.numbersMultiplicationTable[i] = (event.target as HTMLInputElement).value;
      this.loadResult();
    }
  }

  loadResult(): void {
    if (this.exercise && this.userInputs) {
      this.exercise.userResult = JSON.stringify(this.numbers);
    }
  }
}
