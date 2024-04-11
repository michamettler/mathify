import {Routes} from '@angular/router';
import {
  UserLoginComponent
} from "./features/registration/components/user-login/user-login.component";
import {ModeSelectionComponent} from "./features/registration/components/mode-selection/mode-selection.component";
import {AuthGuard} from "./core/guards/auth.guard";
import {
  ScoreboardOverviewComponent
} from "./features/scoreboard/components/scoreboard-overview/scoreboard-overview.component";

export const routes: Routes = [
  {path: '', component: UserLoginComponent},
  {path: 'login', component: UserLoginComponent},
  {path: 'grade-selection', component: ModeSelectionComponent, canActivate: [AuthGuard]},
  {path: 'scoreboard', component: ScoreboardOverviewComponent, canActivate: [AuthGuard]}
]
