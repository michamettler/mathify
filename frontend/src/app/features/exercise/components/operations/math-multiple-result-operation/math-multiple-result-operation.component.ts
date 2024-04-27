import {Component, Input} from '@angular/core';
import {Exercise} from "../../../../../../model/exercise";

@Component({
  selector: 'app-math-multiple-result-operation',
  standalone: true,
  imports: [],
  templateUrl: './math-multiple-result-operation.component.html',
  styleUrl: './math-multiple-result-operation.component.scss'
})
export class MathMultipleResultOperationComponent {
  @Input() exercise!: Exercise | undefined;

}
