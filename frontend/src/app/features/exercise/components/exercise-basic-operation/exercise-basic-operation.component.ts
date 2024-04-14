import {Component, Input, OnInit} from '@angular/core';
import {MatCard, MatCardContent, MatCardTitle} from "@angular/material/card";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {FormsModule} from "@angular/forms";
import {NgIf} from "@angular/common";
import {MatButton} from "@angular/material/button";
import {MatInput} from "@angular/material/input";
import {HeaderComponent} from "../../../../core/components/header/header.component";
import {MatProgressBar} from "@angular/material/progress-bar";
import {User} from "../../../../../model/user";

@Component({
  selector: 'app-exercise-basic-operation',
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
  templateUrl: './exercise-basic-operation.component.html',
  styleUrl: './exercise-basic-operation.component.scss'
})
export class ExerciseBasicOperationComponent implements OnInit {
  exercise: string = "Calculate 6 * 7";
  userAnswer = '';
  solution: string = "42";
  exerciseType: string = "Multiplication";
  showSolution: boolean = false;
  showHint: boolean = false;
  hint: string = "Remember to multiply, not add.";

  score: number = 40
  level: string = 'Basic'
  @Input() user: User | undefined = undefined;

  constructor() {
  }

  ngOnInit(): void {

  }


  displaySolution(): void {
    this.showSolution = true;
  }

  toggleHint(): void {
    this.showHint = !this.showHint;
  }

}
