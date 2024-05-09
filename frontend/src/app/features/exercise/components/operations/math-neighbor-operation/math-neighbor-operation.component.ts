import {Component, Input, OnChanges, OnInit} from '@angular/core';
import {Exercise} from "../../../../../../model/exercise";
import {MatButton} from "@angular/material/button";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {NgIf} from "@angular/common";
import {FormsModule} from "@angular/forms";

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
export class MathNeighborOperationComponent implements OnInit, OnChanges {
  @Input() exercise?: Exercise;
  lowerNeighbor: string = '';
  upperNeighbor: string =  '';

  ngOnInit(): void {
    this.loadExercise();
  }

  ngOnChanges(): void {
    this.loadExercise();
  }

  loadExercise(): void {
    this.lowerNeighbor = '';
    this.upperNeighbor = '';
  }

  loadResult(): void {
    if (this.exercise) {
      this.exercise.userResult = JSON.stringify([Number(this.lowerNeighbor), Number(this.upperNeighbor)]);
    }
  }
}
