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
  @Input() userInputs: UserInputs = {} as UserInputs;

  number: number | undefined;

  protected readonly Number = Number;

  ngOnInit(): void {
    this.number = JSON.parse(this.exercise?.calculationValues ?? '[]')[0];
  }

  handleChange(event: Event, i: number) {
    if (this.userInputs && (event.target as HTMLInputElement).value) {
      this.userInputs.numbersMultiplicationTable[i] = JSON.parse((event.target as HTMLInputElement).value);
      this.loadResult();
    }
  }

  loadResult(): void {
    if (this.exercise && this.userInputs) {
      this.exercise.userResult = JSON.stringify(this.userInputs.numbersMultiplicationTable);
    }
  }

  trackByFn(index: number): number {
    return index;
  }
}
