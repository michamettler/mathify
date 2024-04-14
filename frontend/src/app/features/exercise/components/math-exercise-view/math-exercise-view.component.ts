import {Component} from '@angular/core';
import {MathExerciseBasicOperationComponent} from "../exercise-basic-operation/math-exercise-basic-operation.component";

@Component({
  selector: 'app-math-exercise-view',
  standalone: true,
  imports: [
    MathExerciseBasicOperationComponent
  ],
  templateUrl: './math-exercise-view.component.html',
  styleUrl: './math-exercise-view.component.scss'
})
export class MathExerciseViewComponent {

}
