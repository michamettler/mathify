import {Component, Input} from '@angular/core';
import {MatCard, MatCardContent, MatCardTitle} from "@angular/material/card";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {FormsModule} from "@angular/forms";
import {NgIf} from "@angular/common";
import {MatButton} from "@angular/material/button";
import {MatInput} from "@angular/material/input";
import {HeaderComponent} from "../../../../../core/components/header/header.component";
import {MatProgressBar} from "@angular/material/progress-bar";
import {Exercise} from "../../../../../../model/exercise";
import {User} from "../../../../../../model/user";

@Component({
  selector: 'app-math-single-result-operation',
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
    MatProgressBar
  ],
  templateUrl: './math-single-result-operation.component.html',
  styleUrl: './math-single-result-operation.component.scss'
})
export class MathSingleResultOperationComponent {
  @Input() exercise?: Exercise;
  showSolution: boolean = false;
  showHint: boolean = false;
  hint: string = "Remember to multiply, not add.";

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
