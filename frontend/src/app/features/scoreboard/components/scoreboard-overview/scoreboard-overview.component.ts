import {Component} from '@angular/core';
import {HeaderComponent} from "../../../../core/components/header/header.component";

@Component({
  selector: 'app-scoreboard-overview',
  standalone: true,
  imports: [
    HeaderComponent
  ],
  templateUrl: './scoreboard-overview.component.html',
  styleUrl: './scoreboard-overview.component.scss'
})
export class ScoreboardOverviewComponent {

}
