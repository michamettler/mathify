import {Component, Input} from '@angular/core';
import { Exercise } from '../../../../../../model/exercise';

@Component({
  selector: 'app-math-multiple-step-operation',
  standalone: true,
  imports: [],
  templateUrl: './math-multiple-step-operation.component.html',
  styleUrl: './math-multiple-step-operation.component.scss'
})
export class MathMultipleStepOperationComponent {
  @Input() exercise!: Exercise | undefined;

}
