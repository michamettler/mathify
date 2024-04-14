import {Component} from '@angular/core';
import {ExerciseBasicOperationComponent} from "../exercise-basic-operation/exercise-basic-operation.component";

@Component({
  selector: 'app-math-exercise-view',
  standalone: true,
  imports: [
    ExerciseBasicOperationComponent
  ],
  templateUrl: './math-exercise-view.component.html',
  styleUrl: './math-exercise-view.component.scss'
})
export class MathExerciseViewComponent {

}
