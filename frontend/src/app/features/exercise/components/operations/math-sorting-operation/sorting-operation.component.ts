import {Component, Input} from '@angular/core';
import {Exercise} from '../../../../../../model/exercise';

@Component({
  selector: 'app-math-sorting-operation',
  standalone: true,
  imports: [],
  templateUrl: './sorting-operation.component.html',
  styleUrl: './sorting-operation.component.scss'
})
export class SortingOperationComponent {
  @Input() exercise!: Exercise | undefined;

}
