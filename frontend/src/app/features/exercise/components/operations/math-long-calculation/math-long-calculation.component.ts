import {Component, Input} from '@angular/core';
import {Exercise} from "../../../../../../model/exercise";
import {UserInputs} from "../../../../../../model/userInputs";

@Component({
  selector: 'app-math-long-calculation',
  standalone: true,
  imports: [],
  templateUrl: './math-long-calculation.component.html',
  styleUrl: './math-long-calculation.component.scss'
})
export class MathLongCalculationComponent {

  @Input() exercise?: Exercise;
  @Input() userInputs?: UserInputs;
}
