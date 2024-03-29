import { Component } from '@angular/core';
import {HeaderComponent} from "../../../../core/components/header/header.component";

@Component({
  selector: 'app-grade-selection',
  standalone: true,
  imports: [
    HeaderComponent
  ],
  templateUrl: './grade-selection.component.html',
  styleUrl: './grade-selection.component.scss'
})
export class GradeSelectionComponent {

}
