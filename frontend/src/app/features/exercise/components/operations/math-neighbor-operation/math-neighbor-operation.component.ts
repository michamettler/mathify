import {Component, Input} from '@angular/core';
import {Exercise} from "../../../../../../model/exercise";
import {MatButton} from "@angular/material/button";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {NgIf} from "@angular/common";
import {MathExerciseService} from "../../../services/math-exercise.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {FormsModule} from "@angular/forms";
import {UserInputs} from "../../../../../../model/userInputs";

@Component({
  selector: 'app-math-neighbor-operation',
  standalone: true,
  imports: [
    MatButton,
    MatFormField,
    MatInput,
    MatLabel,
    NgIf,
    FormsModule
  ],
  templateUrl: './math-neighbor-operation.component.html',
  styleUrl: './math-neighbor-operation.component.scss'
})
export class MathNeighborOperationComponent {
  @Input() exercise?: Exercise;
  @Input() userInputs?: UserInputs;

  showSolution: boolean = false;
  showHint: boolean = false;
  hint: string = "Remember to multiply, not add.";

  displaySolution(): void {
    this.showSolution = true;
  }

  toggleHint(): void {
    this.showHint = !this.showHint;
  }

  changeLowerNeighbor(event: Event) {
    if (this.userInputs) {
      this.userInputs.lowerNeighbor = (event.target as HTMLInputElement).value;
      this.loadResult();
    }
  }

  changeUpperNeighbor(event: Event) {
    if (this.userInputs) {
      this.userInputs.upperNeighbor = (event.target as HTMLInputElement).value;
      this.loadResult();
    }
  }

  loadResult(): void {
    if (this.exercise && this.userInputs) {
      this.exercise.userResult = JSON.stringify([Number(this.userInputs.lowerNeighbor), Number(this.userInputs.upperNeighbor)]);
    }
  }
}
