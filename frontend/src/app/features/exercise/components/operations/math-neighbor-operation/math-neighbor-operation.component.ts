import {Component, Input} from '@angular/core';
import {Exercise} from "../../../../../../model/exercise";
import {MatButton} from "@angular/material/button";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {NgIf} from "@angular/common";
import {User} from "../../../../../../model/user";

@Component({
  selector: 'app-math-neighbor-operation',
  standalone: true,
  imports: [
    MatButton,
    MatFormField,
    MatInput,
    MatLabel,
    NgIf
  ],
  templateUrl: './math-neighbor-operation.component.html',
  styleUrl: './math-neighbor-operation.component.scss'
})
export class MathNeighborOperationComponent {
  @Input() exercise?: Exercise;

  showSolution: boolean = false;
  showHint: boolean = false;
  hint: string = "Remember to multiply, not add.";

  firstAnswer: string = '';
  secondAnswer: string = '';

  @Input() user: User = { //TODO read from session
    grade: 'third',
    username: 'System_Admin',
    password: 'fg6i7i4bMa',
    level: 1,
    experience: 30
  };

  constructor() {
  }

  displaySolution(): void {
    this.showSolution = true;
  }

  toggleHint(): void {
    this.showHint = !this.showHint;
  }

}
